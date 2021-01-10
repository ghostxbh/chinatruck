package com.uzykj.chinatruck.web;

import com.google.common.base.Preconditions;
import com.uzykj.chinatruck.domain.PartInfo;
import com.uzykj.chinatruck.domain.dto.DataQueueDTO;
import com.uzykj.chinatruck.domain.dto.TransferDataDTO;
import com.uzykj.chinatruck.domain.vo.JsonResult;
import com.uzykj.chinatruck.service.PartInfoService;
import com.uzykj.chinatruck.service.QueueQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author ghostxbh
 */
@RestController
@RequestMapping("queue")
@Api("数据队列")
public class DataQueueController {
    @Autowired
    private QueueQueryService queueQueryService;
    @Autowired
    private PartInfoService partsImagesService;

    @PostMapping("start")
    @ApiOperation("队列开始")
    public JsonResult<?> start(@RequestBody DataQueueDTO queueDTO) {
        Preconditions.checkArgument(queueDTO.getStart_num() < queueDTO.getEnd_num(), "endNum is error");
        queueQueryService.getQueue(queueDTO);
        return new JsonResult().success();
    }

    @PostMapping("parse")
    @ApiOperation("解析")
    public JsonResult<?> parse(@RequestBody List<PartInfo> imagesList) {
        partsImagesService.batchSave(imagesList);
        return new JsonResult().success();
    }

    @GetMapping("formatData")
    @ApiOperation("整理数据")
    public JsonResult<?> formatData() {
        queueQueryService.formatCateData();
        return new JsonResult().success();
    }

    @PostMapping("transferData")
    @ApiOperation("转移数据")
    public JsonResult<?> transferData(@RequestBody TransferDataDTO transferDataDTO) {
        Preconditions.checkNotNull(transferDataDTO.getSource(), "源数据不存在");
        Preconditions.checkNotNull(transferDataDTO.getTarget(), "目标数据不存在");
        queueQueryService.transferData(transferDataDTO);
        return new JsonResult().success();
    }
}
