package com.uzykj.chinatruck.web;

import com.google.common.base.Preconditions;
import com.uzykj.chinatruck.common.ResponseContants;
import com.uzykj.chinatruck.domain.Contact;
import com.uzykj.chinatruck.domain.vo.JsonResult;
import com.uzykj.chinatruck.service.ContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ghostxbh
 */
@Slf4j
@RestController
@RequestMapping("contact")
@Api("联系API")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("submit")
    @ApiOperation("提交")
    public JsonResult<?> submit(@RequestBody Contact contact) {
        try {
            Preconditions.checkArgument(contact.getCust_name() != null, ResponseContants.cust_name);
            Preconditions.checkArgument(contact.getCust_email() != null, ResponseContants.cust_email);
            contactService.save(contact);
            return new JsonResult().success();
        } catch (Exception e) {
            log.error("contact submit error", e);
            return new JsonResult().error(e);
        }
    }

    @PostMapping("quotation")
    @ApiOperation("报价单")
    public JsonResult<?> quotation(@RequestBody Contact contact) {
        try {
            Preconditions.checkArgument(contact.getBrand() != null, ResponseContants.brand);
            Preconditions.checkArgument(contact.getPart_desc() != null, ResponseContants.part_desc);
            Preconditions.checkArgument(contact.getCust_email() != null, ResponseContants.cust_email);
            contactService.save(contact);
            return new JsonResult().success();
        } catch (Exception e) {
            log.error("contact quotation error", e);
            return new JsonResult().error(e);
        }
    }
}
