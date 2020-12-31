package com.uzykj.chinatruck.service;

import com.uzykj.chinatruck.common.Constants;
import com.uzykj.chinatruck.domain.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ghostxbh
 */
@Slf4j
@Service
public class ComponentService {
    @Resource
    private MongoTemplate mongoTemplate;

    public List<Component> getAll() {
        return mongoTemplate.findAll(Component.class, Constants.COMPONENT);
    }

    public Component get(String id) {
        return mongoTemplate.findById(id, Component.class, Constants.COMPONENT);
    }
}
