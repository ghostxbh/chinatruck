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
@Document("parts_info")
public class PartInfo {
    @Id
    private String _id;

    private String title;

    private String url;

    private String image;

    private String brand;

    private String part;

    private Integer featured;

    private String category;

    private String brand_id;

    private String category_id;

    private String component_id;
}
