package com.uzykj.chinatruck.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xbh
 * @date 2020/12/7
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataQueueDTO {
    private String url;
    private Integer start_num;
    private Integer end_num;
}
