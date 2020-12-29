package com.uzykj.chinatruck.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ghostxbh
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "component")
public class Component {
    @Id
    private String _id;

    private String name;

    private String image;

    private String type;

    private String data_id;

    private Integer total;

    private String create_time;
}
