package com.uzykj.chinatruck.service;

import com.uzykj.chinatruck.common.Constants;
import com.uzykj.chinatruck.domain.Brand;
import com.uzykj.chinatruck.domain.Component;
import com.uzykj.chinatruck.domain.vo.Node;
import com.uzykj.chinatruck.utils.DateUtils;
import com.uzykj.chinatruck.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ghostxbh
 */
@Slf4j
@Service
public class BrandService {
    @Resource
    private MongoTemplate mongoTemplate;

    public void save(Brand brand) {
        brand.setCreate_time(DateUtils.getCurrentTime());
        mongoTemplate.save(brand);
    }

    public void batchSave(List<Brand> brands) {
        log.info("add list total: {}", brands.size());
        brands.forEach(brand -> brand.setCreate_time(DateUtils.getCurrentTime()));
        mongoTemplate.insertAll(brands);
    }

    @Async
    public void nodeSave(List<Brand> brands) {
        log.info("add list total: {}", brands.size());
        brands.forEach(brand -> {
            getNode(brand.getChildren());
            brand.setCreate_time(DateUtils.getCurrentTime());
            mongoTemplate.save(brand);
        });
        log.info("add brands success");
    }

    private void getNode(List<Node> list){
        list.forEach(item -> {
            if (item.getChildren() != null) {
                getNode(item.getChildren());
            } else {
                Component component = Component.builder()
                        .name(item.getName())
                        .data_id(item.getData_id()[0])
                        .type(item.getType())
                        .create_time(DateUtils.getCurrentTime())
                        .update_stamp(DateUtils.getCurrentTimeStamp())
                        .build();
                Component save = mongoTemplate.save(component, Constants.COMPONENT);

                item.setId(save.get_id());
            }
        });
    }

    public Brand get(String id) {
        return mongoTemplate.findById(id, Brand.class);
    }

    public List<Brand> list() {
        return mongoTemplate.find(new Query(), Brand.class);
    }
}
