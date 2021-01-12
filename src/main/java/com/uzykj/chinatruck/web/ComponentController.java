package com.uzykj.chinatruck.web;

import com.uzykj.chinatruck.domain.Component;
import com.uzykj.chinatruck.domain.vo.JsonResult;
import com.uzykj.chinatruck.service.ComponentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xbh
 * @date 2021/1/12
 * @description
 */
@Slf4j
@Api("分类部件")
@RestController
@RequestMapping("component")
public class ComponentController {
    @Autowired
    private ComponentService componentService;

    @ApiOperation("详情")
    @GetMapping("{id}")
    public JsonResult<Component> get(@PathVariable String id) {
        Component component = componentService.get(id);
        return new JsonResult(component);
    }

    @ApiOperation("全部")
    @GetMapping("all")
    public JsonResult<?> getAll() {
        List<Component> componentList = componentService.getAll();
        return new JsonResult(componentList);
    }
}
