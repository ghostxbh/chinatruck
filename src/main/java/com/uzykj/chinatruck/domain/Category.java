package com.uzykj.chinatruck.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ghostxbh 分类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "category")
public class Category {
    @Id
    private String _id;

    private String name;

    private String cn_name;

    private Integer total;

    private String createTime;
}
