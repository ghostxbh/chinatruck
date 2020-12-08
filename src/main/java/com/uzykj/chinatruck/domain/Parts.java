package com.uzykj.chinatruck.domain;

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
@Document(value = "parts")
public class Parts {
    @Id
    private ObjectId _id;
    // 实体id
    private String entity_id;
    // 实体类型id
    private String entity_type_id;
    // 属性id
    private String attribute_set_id;
    // 类型id
    private String type_id;
    // sku
    private String sku;
    // 索引位置
    private String cat_index_position;
    // 状态
    private String status;
    // 名称
    private String name;
    // 图片地址
    private String image;
    // 链接键
    private String url_key;
    // 生产国
    private String country_of_manufacture;
    // 元关键词
    private String meta_keyword;
    // 标题
    private String meta_title;
    // 描述
    private String meta_description;
    // 图片标签
    private String image_label;
    // 地址
    private String url_path;

    private String created_at;
    private String updated_at;


//    private String has_options;
//    private String required_options;
//    private String msrp_enabled;
//    private String msrp_display_actual_price_type;
//    private String small_image;
//    private String thumbnail;
//    private String custom_design;
//    private String page_layout;
//    private String options_container;
//    private String gift_message_available;
//    private String small_image_label;
//    private String thumbnail_label;
//    private String visibility;
//    private String reward_point_product;
//    private String mw_reward_point_sell_product;
//    private String tax_class_id;
//    private String is_recurring;
//    private String rw_google_base_skip_submi;
//    private String news_from_date;
//    private String news_to_date;
//    private String special_from_date;
//    private String special_to_date;
//    private String custom_design_from;
//    private String custom_design_to;
//    private String description;
//    private String short_description;
//    private String custom_layout_update;
//    private String weight;
//    private String price;
//    private String special_price;
//    private String msrp;
//    private String is_salable;
//    private Object stock_item;
}
