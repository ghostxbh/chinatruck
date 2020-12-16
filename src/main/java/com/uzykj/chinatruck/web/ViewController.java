package com.uzykj.chinatruck.web;

import com.uzykj.chinatruck.domain.Brand;
import com.uzykj.chinatruck.domain.PartInfo;
import com.uzykj.chinatruck.domain.dto.PartQueryDTO;
import com.uzykj.chinatruck.domain.vo.Page;
import com.uzykj.chinatruck.service.BrandService;
import com.uzykj.chinatruck.service.CategoryService;
import com.uzykj.chinatruck.service.PartInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author ghostxbh
 */
@Controller
public class ViewController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PartInfoService partInfoService;

    @GetMapping("/")
    public String index(Model model) {
        List<Brand> brands = brandService.list();
        model.addAttribute("brands", brands);
        return "index";
    }

    @GetMapping("/number.html")
    public String number(@RequestParam(required = false) String q,
                         @RequestParam(required = false) String no,
                         @RequestParam(required = false) String brand,
                         @RequestParam(required = false) String category,
                         @RequestParam(required = false) String keywords,
                         @RequestParam(defaultValue = "1") Integer page_no,
                         @RequestParam(defaultValue = "10") Integer page_size,
                         Model model) {
        Page<PartInfo> page = new Page<PartInfo>(page_no, page_size);
        PartQueryDTO queryDTO = PartQueryDTO.builder()
                .q(q)
                .brand(brand)
                .category(category)
                .keywords(keywords)
                .no(no)
                .page(page)
                .build();

        Page<PartInfo> partInfoPage = partInfoService.numberSearch(queryDTO);

        model.addAttribute("result", partInfoPage);
        model.addAttribute("q", q);
        model.addAttribute("no", no);
        model.addAttribute("brand", brand);
        model.addAttribute("category", category);
        model.addAttribute("keywords", keywords);
        return "number";
    }

    @GetMapping("/categories.html")
    public String categories(Model model) {
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
}
