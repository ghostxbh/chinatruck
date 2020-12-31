package com.uzykj.chinatruck.service;

import com.uzykj.chinatruck.common.CateConstants;
import com.uzykj.chinatruck.common.Constants;
import com.uzykj.chinatruck.domain.Brand;
import com.uzykj.chinatruck.domain.Category;
import com.uzykj.chinatruck.domain.Component;
import com.uzykj.chinatruck.domain.vo.Node;
import com.uzykj.chinatruck.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ghostxbh
 */
@Slf4j
@Service
public class CategoryService {

    @Resource
    private MongoTemplate mongoTemplate;

    public void save(Category category) {
        category.setCreate_time(DateUtils.getCurrentTime());
        mongoTemplate.save(category);
    }

    public void batchSave(List<Category> categories) {
        log.info("add list total: {}", categories.size());
        categories.forEach(category -> category.setCreate_time(DateUtils.getCurrentTime()));
        mongoTemplate.insertAll(categories);
    }

    @Async
    public void nodeSave(List<Category> categories) {
        log.info("add list total: {}", categories.size());
        categories.forEach(category -> {
            getNode(category.getChildren());
            category.setCreate_time(DateUtils.getCurrentTime());
            mongoTemplate.save(category);
        });
        log.info("add category success");
    }

    private void getNode(List<Node> list) {
        list.forEach(item -> {
            if (item.getChildren() != null) {
                getNode(item.getChildren());
            } else {
                if (CateConstants.isContain(item.getName())) {
                    Query query = new Query();
                    query.with(Sort.by(Sort.Direction.DESC, "update_stamp"));
                    String condition = "^" + item.getName() + "_.*$";
                    Criteria criteria = Criteria.where("name").regex(condition);
                    query.addCriteria(criteria);
                    List<Component> components = mongoTemplate.find(query, Component.class, Constants.COMPONENT);
                    log.info("get like component size: {}", components.size());
                    if (!CollectionUtils.isEmpty(components)) {
                        Component component = components.get(0);
                        String[] split = component.getName().split("_");
                        item.setName(item.getName() + "_" + (Integer.parseInt(split[split.length - 1]) + 1));
                    } else {
                        item.setName(item.getName() + "_1");
                    }
                }
                saveComponent(item);
            }
        });
    }

    private void saveComponent(Node item) {
        try {
            Query query = new Query();
            Criteria criteria = Criteria.where("name").is(item.getName());
            query.addCriteria(criteria);
            List<Component> components = mongoTemplate.find(query, Component.class, Constants.COMPONENT);

            if (CollectionUtils.isEmpty(components)) {
                Component component = Component.builder()
                        .name(item.getName())
                        .data_id(item.getData_id()[0])
                        .type(item.getType())
                        .create_time(DateUtils.getCurrentTime())
                        .update_stamp(DateUtils.getCurrentTimeStamp())
                        .build();

                Component save = mongoTemplate.save(component, Constants.COMPONENT);

                item.setId(save.get_id());
            } else {
                log.info("this component is exist, name: {}", item.getName());
                Component component = components.get(0);
                log.info("set category data_id's _id, component: {}", component);
                item.setId(component.get_id());
            }

        } catch (Exception e) {
            log.error("save component error: ", e);
            log.info("save component name: {}", item.getName());
        }
    }

    public Category get(String id) {
        return mongoTemplate.findById(id, Category.class);
    }

    public List<Category> list() {
        return mongoTemplate.find(new Query(), Category.class);
    }
}
