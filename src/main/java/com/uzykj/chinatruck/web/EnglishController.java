package com.uzykj.chinatruck.web;

import com.uzykj.chinatruck.common.CategoryConstants;
import com.uzykj.chinatruck.common.ResponseContants;
import com.uzykj.chinatruck.domain.Brand;
import com.uzykj.chinatruck.domain.Category;
import com.uzykj.chinatruck.domain.Contact;
import com.uzykj.chinatruck.domain.PartInfo;
import com.uzykj.chinatruck.domain.dto.PartQueryDTO;
import com.uzykj.chinatruck.domain.vo.Page;
import com.uzykj.chinatruck.service.BrandService;
import com.uzykj.chinatruck.service.CategoryService;
import com.uzykj.chinatruck.service.ContactService;
import com.uzykj.chinatruck.service.PartInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ghostxbh
 */
@Slf4j
@Controller
public class EnglishController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private PartInfoService partInfoService;

    @GetMapping("/")
    public String index(Model model) {
        List<Brand> brands = brandService.list();

        List<PartInfo> cabList = partInfoService.getPartsList(CategoryConstants.CAB, CategoryConstants.SIZE);
        List<PartInfo> chassisList = partInfoService.getPartsList(CategoryConstants.CHASSIS, CategoryConstants.SIZE);
        List<PartInfo> engineList = partInfoService.getPartsList(CategoryConstants.ENGINE, CategoryConstants.SIZE);
        List<PartInfo> electricalList = partInfoService.getPartsList(CategoryConstants.ELECTRICAL, CategoryConstants.SIZE);
        model.addAttribute("brands", brands);
        model.addAttribute("cabList", cabList);
        model.addAttribute("chassisList", chassisList);
        model.addAttribute("engineList", engineList);
        model.addAttribute("electricalList", electricalList);
        return "index";
    }

    @GetMapping("/number.html")
    public String number(@RequestParam(required = false) String brand,
                         @RequestParam(required = false) String platfrom,
                         @RequestParam(required = false) String category,
                         @RequestParam(required = false) String no,
                         @RequestParam(required = false) String keywords,
                         @RequestParam(defaultValue = "10") Integer type,
                         @RequestParam(defaultValue = "1") Integer page_no,
                         @RequestParam(defaultValue = "10") Integer page_size,
                         Model model, HttpServletRequest request) {
        Page<PartInfo> page = new Page<PartInfo>(page_no, page_size);
        PartQueryDTO queryDTO = PartQueryDTO.builder()
                .type(type)
                .brand(brand)
                .platfrom(platfrom)
                .category(category)
                .keywords(keywords)
                .no(no)
                .page(page)
                .build();

        partInfoService.listByModel(queryDTO, model);
        return "number";
    }

    @GetMapping("/categories.html")
    public String categories(Model model) {
        List<Category> categoryList = categoryService.list();
        model.addAttribute("categoryList", categoryList);
        return "categories";
    }

    @GetMapping("/part.html/{id}")
    public String part(Model model, @PathVariable String id) {
        PartInfo partInfo = partInfoService.get(id);
        model.addAttribute("partInfo", partInfo);
        return "part";
    }

    @GetMapping("/about.html")
    public String about() {
        return "about";
    }

    @GetMapping("/contact.html")
    public String contact() {
        return "contact";
    }

    @PostMapping("/quotation.html")
    public String quotation(Contact contact, Model model) {
        boolean success = true;
        if (StringUtils.isEmpty(contact.getBrand())) {
            success = false;
            model.addAttribute("message", ResponseContants.NOT_BRAND);
        }
        if (StringUtils.isEmpty(contact.getPart_desc())) {
            success = false;
            model.addAttribute("message", ResponseContants.NOT_PART_DESC);
        }
        if (StringUtils.isEmpty(contact.getCust_email())) {
            success = false;
            model.addAttribute("message", ResponseContants.NOT_CUST_EMAIL);
        }
        try {
            if (!success) {
                model.addAttribute("success", false);
                return "sendStatus";
            }
            contactService.save(contact);
            model.addAttribute("success", true);
            model.addAttribute("message", ResponseContants.SUBMIT_SUCCESS);
        } catch (Exception e) {
            log.error("submit quotation error", e);
            model.addAttribute("success", false);
            model.addAttribute("message", ResponseContants.QUOTATION_ERROR);
        }
        return "sendStatus";
    }

    @PostMapping("/submit.html")
    public String submit(Contact contact, Model model) {
        boolean success = true;
        if (StringUtils.isEmpty(contact.getCust_name())) {
            success = false;
            model.addAttribute("message", ResponseContants.NOT_CUST_NAME);
        }
        if (StringUtils.isEmpty(contact.getCust_email())) {
            success = false;
            model.addAttribute("message", ResponseContants.NOT_CUST_EMAIL);
        }
        try {
            if (!success) {
                model.addAttribute("success", false);
                return "sendStatus";
            }
            contactService.save(contact);
            model.addAttribute("success", true);
            model.addAttribute("message", ResponseContants.SUBMIT_SUCCESS);
        } catch (Exception e) {
            log.error("submit error", e);
            model.addAttribute("success", false);
            model.addAttribute("message", ResponseContants.ADVISORY_ERROR);
        }
        return "sendStatus";
    }
}
