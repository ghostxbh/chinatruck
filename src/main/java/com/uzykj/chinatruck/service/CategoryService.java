package com.uzykj.chinatruck.service;

import com.uzykj.chinatruck.domain.Category;
import com.uzykj.chinatruck.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

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

    public Category get(String id) {
        return mongoTemplate.findById(id, Category.class);
    }

    public List<Category> list() {
        return mongoTemplate.find(new Query(), Category.class);
    }
}
