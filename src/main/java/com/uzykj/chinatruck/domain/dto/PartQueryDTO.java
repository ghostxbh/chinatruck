package com.uzykj.chinatruck.domain.dto;

import com.uzykj.chinatruck.domain.PartInfo;
import com.uzykj.chinatruck.domain.vo.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ghostxbh
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartQueryDTO {
    /**
     * 页面
     * -1、默认
     * 1、首页
     * 2、型号页面
     * 3、分类页面
     * 4、搜索框
     */
    private Integer type;
    private String brand;
    private String platfrom;
    private String category;
    private String no;
    private String keywords;
    private Page<PartInfo> page;
}
