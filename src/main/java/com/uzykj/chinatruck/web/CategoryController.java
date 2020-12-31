package com.uzykj.chinatruck.web;

import com.uzykj.chinatruck.domain.Category;
import com.uzykj.chinatruck.domain.vo.JsonResult;
import com.uzykj.chinatruck.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ghostxbh
 */
@Slf4j
@RestController
@RequestMapping("category")
@Api("分类API")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("create")
    @ApiOperation("新增")
    public JsonResult<?> create(@RequestBody List<Category> categoryList) {
        categoryService.batchSave(categoryList);
        return new JsonResult().success();
    }

    @PostMapping("insert")
    @ApiOperation("新增")
    public JsonResult<?> insert(@RequestBody List<Category> categoryList) {
        categoryService.nodeSave(categoryList);
        return new JsonResult().success();
    }
}
