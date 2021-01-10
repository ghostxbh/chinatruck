package com.uzykj.chinatruck.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xbh
 * @date 2021/1/10
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDataDTO {
    /**
     * 源数据库
     */
    private MongoInfoDTO source;
    /**
     * 目标数据库
     */
    private MongoInfoDTO target;
}
