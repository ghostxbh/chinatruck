package com.uzykj.chinatruck.service;

import com.uzykj.chinatruck.domain.Brand;
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
public class BrandService {
    @Resource
    private MongoTemplate mongoTemplate;

    public void save(Brand brand) {
        brand.setCreateTime(DateUtils.getCurrentTime());
        mongoTemplate.save(brand);
    }

    public void batchSave(List<Brand> brands) {
        log.info("add list total: {}", brands.size());
        brands.forEach(brand -> brand.setCreateTime(DateUtils.getCurrentTime()));
        mongoTemplate.insertAll(brands);
    }

    public Brand get(String id) {
        return mongoTemplate.findById(id, Brand.class);
    }

    public List<Brand> list() {
        return mongoTemplate.find(new Query(), Brand.class);
    }
}
