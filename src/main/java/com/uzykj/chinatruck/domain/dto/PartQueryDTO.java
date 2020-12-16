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
    private String no;
    private String brand;
    private String category;
    private String keywords;
    private Page<PartInfo> page;
}
