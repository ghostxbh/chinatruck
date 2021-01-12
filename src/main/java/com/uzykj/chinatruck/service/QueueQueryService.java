package com.uzykj.chinatruck.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.uzykj.chinatruck.common.Constants;
import com.uzykj.chinatruck.domain.Brand;
import com.uzykj.chinatruck.domain.Component;
import com.uzykj.chinatruck.domain.PartInfo;
import com.uzykj.chinatruck.domain.Parts;
import com.uzykj.chinatruck.domain.dto.DataQueueDTO;
import com.uzykj.chinatruck.domain.dto.MongoInfoDTO;
import com.uzykj.chinatruck.domain.dto.TransferDataDTO;
import com.uzykj.chinatruck.domain.vo.Node;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ghostxbh
 */
@Slf4j
@Service
public class QueueQueryService {
    public static String CATEIMAGEURL = "https://www.chinatruckparts.com/productcontact/index/ceidfujia?zyid=";
    public static String CATEURL = "https://www.chinatruckparts.com/productcontact/index/indexfujia?zyid=";

    public static String SOURCE_URL = "https://www.chinatruckparts.com/";
    public static String NEW_IMAGE_URL = "https://chinametruckparts.com/parts_images/";

    @Resource
    private PartsService partsService;
    @Resource
    private ComponentService componentService;
    @Resource
    private PartInfoService partInfoService;
    @Resource
    private MongoTemplate mongoTemplate;

    @Async
    public void getQueue(DataQueueDTO queueDTO) {
        String domain = queueDTO.getUrl();
        int start_num = queueDTO.getStart_num();
        int end_num = queueDTO.getEnd_num();

        while (start_num < end_num) {
            String url = domain + start_num;
            log.info("queue request url: {}", url);
            try {
                HttpRequest request = HttpUtil.createGet(url);
                request.setConnectionTimeout(10 * 1000);
                request.setReadTimeout(20 * 1000);
                HttpResponse execute = request.execute();
                String body = execute.body();
                log.info("queue response body: {}", body);
                List<Parts> partsList = JSONArray.parseArray(body, Parts.class);

                partsService.batchSave(partsList);
            } catch (Exception e) {
                log.error("queue fail! num: {}", start_num, e);
            }
            start_num++;
        }
        log.info("queue is done!");
    }

    @Async
    public void formatCateData() {
        List<Component> componentList = componentService.getAll();
        log.info("format component size: {}", componentList.size());
        componentList.forEach(component -> {
            String image = HttpUtil.get(CATEIMAGEURL + component.getData_id());
            String cateList = HttpUtil.get(CATEURL + component.getData_id());
            List<Parts> parts = JSONArray.parseArray(cateList, Parts.class);
            Integer total = parts != null ? parts.size() : 0;

            assert parts != null;
            log.info("update PartInfo size: {}", parts.size());
            parts.forEach(part -> {
                PartInfo byTitle = partInfoService.getByTitle(part.getName());

                if (byTitle != null) {
                    byTitle.setComponent_id(component.get_id());

                    Query query = Query.query(Criteria.where("_id").is(byTitle.get_id()));
                    Update update = Update.update("component_id", component.get_id());

                    mongoTemplate.findAndModify(query, update, PartInfo.class);
                }
            });

            Query query = Query.query(Criteria.where("_id").is(component.get_id()));
            Update update = Update.update("image", image);
            update.set("total", total);

            mongoTemplate.findAndModify(query, update, Component.class);
        });
        log.info("format data success!");
    }

    @Async
    public void transferData(TransferDataDTO transferDataDTO) {
        log.info("transfer data started...");

        MongoInfoDTO source = transferDataDTO.getSource();
        MongoInfoDTO target = transferDataDTO.getTarget();

        MongoClient sourceClient;
        MongoClient targetClient;

        MongoClientOptions mongoClientOptions = new MongoClientOptions.Builder()
                .connectionsPerHost(1000)
                .minConnectionsPerHost(5)
                .connectTimeout(60 * 1000)
                .maxWaitTime(2 * 60 * 1000)
                .build();

        try {
            if (source.getIsAuth()) {
                ServerAddress sourceAddress = new ServerAddress(source.getHost(), source.getPort());

                MongoCredential sourceCredential = MongoCredential
                        .createCredential(source.getUsername(), source.getAuthDb(), source.getPassword().toCharArray());

                sourceClient = new MongoClient(sourceAddress, sourceCredential, mongoClientOptions);
            } else {
                sourceClient = new MongoClient(source.getHost(), source.getPort());
            }
        } catch (Exception e) {
            log.error("get source error", e);
            throw new RuntimeException("获取源数据库错误");
        }


        try {
            if (target.getIsAuth()) {
                ServerAddress targetAddress = new ServerAddress(target.getHost(), target.getPort());

                MongoCredential targetCredential = MongoCredential
                        .createCredential(target.getUsername(), target.getAuthDb(), target.getPassword().toCharArray());

                targetClient = new MongoClient(targetAddress, targetCredential, mongoClientOptions);
            } else {
                targetClient = new MongoClient(target.getHost(), target.getPort());
            }
        } catch (Exception e) {
            log.error("get target error", e);
            throw new RuntimeException("获取目标数据库错误");
        }

        try {
            MongoDatabase sourceDb = sourceClient.getDatabase(source.getDataBase());

            MongoDatabase targetDb = targetClient.getDatabase(target.getDataBase());

            //要转移数据的表名
            MongoCollection<Document> sourceDbCollection = sourceDb.getCollection(source.getCollectionName());

            MongoCollection<Document> targetDbCollection = targetDb.getCollection(target.getCollectionName());

            //iterator——迭代
            FindIterable<Document> findIterable = sourceDbCollection.find();

            MongoCursor<Document> iterator = findIterable.iterator();

            int size = 0;
            //游标
            //遍历每一条数据
            while (iterator.hasNext()) {
                Document document = iterator.next();
                targetDbCollection.insertOne(document);
                size++;
            }
            log.info("transfer data success! size: {}", size);
        } catch (Exception e) {
            log.error("transfer data error", e);
            throw new RuntimeException("转移数据异常");
        }
    }
}
