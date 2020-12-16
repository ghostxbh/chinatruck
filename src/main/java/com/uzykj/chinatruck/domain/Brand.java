package com.uzykj.chinatruck.domain;

import com.uzykj.chinatruck.domain.vo.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author ghostxbh 品牌
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "brands")
public class Brand {
    @Id
    private String _id;

    private String name;

    private String cn_name;

    private String image;

    private Integer total;

    private String createTime;

    private String type;

    private List<Node> children;
}
