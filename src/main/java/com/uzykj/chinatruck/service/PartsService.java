package com.uzykj.chinatruck.service;

import com.uzykj.chinatruck.domain.Parts;
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
public class PartsService {
    @Resource
    private MongoTemplate mongoTemplate;

    public Parts get(String id){
        return mongoTemplate.findById(id, Parts.class);
    }

    public void save(Parts parts){
        mongoTemplate.save(parts);
    }

    public void batchSave(List<Parts> partsList){
        mongoTemplate.insertAll(partsList);
    }
}
