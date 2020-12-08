package com.uzykj.chinatruck.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.uzykj.chinatruck.domain.Parts;
import com.uzykj.chinatruck.domain.dto.DataQueueDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ghostxbh
 */
@Slf4j
@Service
public class QueueQueryService {
    @Resource
    private PartsService partsService;

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
}
