package com.uzykj.chinatruck.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ghostxbh
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("PartsImages")
public class PartsImages {
    @Id
    private ObjectId _id;

    private String title;

    private String url;

    private String image;

    private String brand;

    private String part;
}
