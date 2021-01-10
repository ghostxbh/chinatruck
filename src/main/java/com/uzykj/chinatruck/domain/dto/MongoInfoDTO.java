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
public class MongoInfoDTO {
    /**
     * 地址
     */
    private String host;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证数据仓库
     */
    private String authDb;
    /**
     * 数据仓库
     */
    private String dataBase;
    /**
     * 数据表
     */
    private String collectionName;
    /**
     * 是否验证
     */
    private Boolean isAuth;
}
