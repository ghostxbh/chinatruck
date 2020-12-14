package com.uzykj.chinatruck.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author xbh
 * @date 2020/12/14
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "notice.mail")
public class EmailProperties {
    private String from;
    private String to;
    private String subject;
}
