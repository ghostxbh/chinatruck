package com.uzykj.chinatruck.web;

import com.uzykj.chinatruck.domain.PartInfo;
import com.uzykj.chinatruck.domain.vo.JsonResult;
import com.uzykj.chinatruck.service.PartInfoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ghostxbh
 */
@Slf4j
@RestController
@RequestMapping("part")
@Api("零件API")
public class PartInfoController {
    @Autowired
    private PartInfoService partInfoService;

    @GetMapping("{id}")
    public JsonResult<?> info(@PathVariable String id) {
        try {
            PartInfo partInfo = partInfoService.get(id);
            return new JsonResult<PartInfo>(partInfo);
        } catch (Exception e) {
            log.error("part info error");
            return new JsonResult().error(e);
        }
    }

    @GetMapping("search")
    public JsonResult<?> search() {
        return null;
    }
}
