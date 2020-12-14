package com.uzykj.chinatruck.service;

import com.uzykj.chinatruck.domain.Contact;
import com.uzykj.chinatruck.domain.vo.EmailProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author xbh
 * @date 2020/12/14
 * @description
 */
@Slf4j
@Service
public class MailService {
    @Resource
    private ContactService contactService;
    @Resource
    private TemplateEngine templateEngine;
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private EmailProperties emailProperties;

    public void sendMail(Contact contact) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(emailProperties.getFrom());
        helper.setTo(emailProperties.getTo());
        helper.setSubject(emailProperties.getSubject());
        //创建邮件正文
        Context context = new Context();
        context.setVariable("contact", contact);

        String emailContent = templateEngine.process("emailTemplate", context);
        helper.setText(emailContent, true);
        try {
            // 发送
            javaMailSender.send(message);
            // 修改发送状态
            contactService.update(contact.get_id(), 1);
        } catch (Exception e) {
            log.error("send mail error", e);
            contactService.update(contact.get_id(), 2);
        }
    }
}
