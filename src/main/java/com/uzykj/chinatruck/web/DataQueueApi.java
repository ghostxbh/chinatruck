package com.uzykj.chinatruck.web;

import com.google.common.base.Preconditions;
import com.uzykj.chinatruck.domain.dto.DataQueueDTO;
import com.uzykj.chinatruck.domain.vo.JsonResult;
import com.uzykj.chinatruck.service.QueueQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xbh
 * @date 2020/12/7
 * @description
 */
@RestController
@Api("数据队列")
public class DataQueueApi {
    @Autowired
    private QueueQueryService queueQueryService;

    @PostMapping("start")
    @ApiOperation("队列开始")
    public JsonResult<?> start(@RequestBody DataQueueDTO queueDTO) {
        Preconditions.checkArgument(queueDTO.getStart_num() < queueDTO.getEnd_num(), "endNum is error");
        queueQueryService.getQueue(queueDTO);
        return new JsonResult().success();
    }
}
