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
@Document(value = "contact")
public class Contact {
    @Id
    private String _id;

    private String cust_name;

    private String cust_email;

    private String cust_phone;

    private String business;

    /**
     * 邮件发送状态
     * 0、未发送 1、已发送 2、发送失败
     */
    private Integer send_status;

    private String send_time;

    private String create_time;
}
