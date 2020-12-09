package com.uzykj.chinatruck.service;

import com.uzykj.chinatruck.domain.PartsInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ghostxbh
 */
@Slf4j
@Service
public class PartsImagesService {
    @Resource
    private MongoTemplate mongoTemplate;

    public void save(PartsInfo partsImages) {
        mongoTemplate.save(partsImages);
    }

    @Async
    public void batchSave(List<PartsInfo> imagesList) {
        log.info("add list total: {}", imagesList.size());
        mongoTemplate.insertAll(imagesList);
    }
}
