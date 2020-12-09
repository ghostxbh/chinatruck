package com.uzykj.chinatruck.web;

import com.uzykj.chinatruck.domain.Brand;
import com.uzykj.chinatruck.domain.PartsInfo;
import com.uzykj.chinatruck.domain.vo.JsonResult;
import com.uzykj.chinatruck.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xbh
 * @date 2020/12/9
 * @description
 */
@Slf4j
@RestController
@RequestMapping("brand")
@Api("品牌API")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @PostMapping("create")
    @ApiOperation("新增")
    public JsonResult<?> create(@RequestBody List<Brand> brandList) {
        brandService.batchSave(brandList);
        return new JsonResult().success();
    }

    @GetMapping("/{id}")
    @ApiOperation("查看")
    public JsonResult<Brand> create(@PathVariable String id) {
        Brand brand = brandService.get(id);
        return new JsonResult<Brand>(brand);
    }
}
