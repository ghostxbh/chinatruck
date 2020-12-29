package com.uzykj.chinatruck.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uzykj.chinatruck.domain.Brand;
import com.uzykj.chinatruck.domain.Component;
import com.uzykj.chinatruck.domain.PartInfo;
import com.uzykj.chinatruck.domain.Parts;
import com.uzykj.chinatruck.domain.dto.DataQueueDTO;
import com.uzykj.chinatruck.domain.vo.Node;
import lombok.extern.slf4j.Slf4j;
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
    private static String CATEIMAGEURL = "https://www.chinatruckparts.com/productcontact/index/ceidfujia?zyid=";
    private static String CATEURL = "https://www.chinatruckparts.com/productcontact/index/indexfujia?zyid=";
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
    }

    public static void main(String[] args) {
        String s = HttpUtil.get("https://www.chinatruckparts.com/productcontact/index/ceidfujia?zyid=1500");
        System.out.println(s);
    }
}
