package com.uzykj.chinatruck.service;

import com.uzykj.chinatruck.common.Constants;
import com.uzykj.chinatruck.domain.Contact;
import com.uzykj.chinatruck.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;

/**
 * @author ghostxbh
 */
@Slf4j
@Service
public class ContactService {

    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private MailService mailService;

    public void save(Contact contact) throws MessagingException {
        contact.setSend_status(0);
        contact.setCreate_time(DateUtils.getCurrentTime());

        Contact save = mongoTemplate.save(contact);
        mailService.sendMail(save);
    }

    public void update(String contact_id, Integer status) {
        Query query = Query.query(Criteria.where("_id").is(contact_id));
        Update update = Update.update("send_status", status);
        update.set("send_time", DateUtils.getCurrentTime());
        mongoTemplate.upsert(query, update, Constants.contact);
    }
}
