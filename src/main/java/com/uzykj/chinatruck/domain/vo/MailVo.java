package com.uzykj.chinatruck.domain.vo;

import com.uzykj.chinatruck.common.MailConstants;
import com.uzykj.chinatruck.domain.Contact;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;


/**
 * @author ghostxbh
 */
@Data
@Builder
public class MailVo {
    /**
     * 收件地址
     */
    private String toAddress;
    /**
     * 标题
     */
    private String subject;
    /**
     * 内容
     */
    private String content;
    /**
     * 附件
     */
    private String files;
    /**
     * 备注
     */
    private String remark;
    /**
     * 签名
     */
    private String sign;
    /**
     * 配置ID
     */
    private Integer propertiesId;
    /**
     * 用户ID
     */
    private Integer userId;

    public static MailVo assembly(Contact contact) {
        return MailVo.builder()
                .toAddress(MailConstants.TO)
                .subject("来自chinametruck.com的信息")
                .content(setContent(contact))
                .propertiesId(MailConstants.PROPERTIESID)
                .userId(MailConstants.USERID)
                .build();
    }

    private static String setContent(Contact contact) {
        StringBuilder builder = new StringBuilder();
        if (!StringUtils.isEmpty(contact.getCust_name())) {
            builder.append("联系人：")
                    .append(contact.getCust_name())
                    .append("\n");
        }
        if (!StringUtils.isEmpty(contact.getCust_phone())) {
            builder.append("联系电话：")
                    .append(contact.getCust_phone())
                    .append("\n");
        }
        if (!StringUtils.isEmpty(contact.getCust_email())) {
            builder.append("邮件地址：")
                    .append(contact.getCust_email())
                    .append("\n");
        }
        if (!StringUtils.isEmpty(contact.getBusiness())) {
            builder.append("咨询业务：")
                    .append(contact.getBusiness())
                    .append("\n");
        }
        if (!StringUtils.isEmpty(contact.getBrand())) {
            builder.append("品牌：")
                    .append(contact.getBrand())
                    .append("\n");
        }
        if (!StringUtils.isEmpty(contact.getPart_no())) {
            builder.append("零件型号：")
                    .append(contact.getPart_no())
                    .append("\n");
        }
        if (!StringUtils.isEmpty(contact.getPart_desc())) {
            builder.append("零件描述：")
                    .append(contact.getPart_desc())
                    .append("\n");
        }
        if (!StringUtils.isEmpty(contact.getOptional())) {
            builder.append("零件属性：")
                    .append(contact.getOptional())
                    .append("\n");
        }
        return new String(builder);
    }
}
