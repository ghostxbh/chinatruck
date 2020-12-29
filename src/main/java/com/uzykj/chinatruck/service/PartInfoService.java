package com.uzykj.chinatruck.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.uzykj.chinatruck.common.Constants;
import com.uzykj.chinatruck.domain.PartInfo;
import com.uzykj.chinatruck.domain.dto.PartQueryDTO;
import com.uzykj.chinatruck.domain.vo.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author ghostxbh
 */
@Slf4j
@Service
public class PartInfoService {
    @Resource
    private MongoTemplate mongoTemplate;

    public void save(PartInfo partInfo) {
        mongoTemplate.save(partInfo);
    }

    @Async
    public void batchSave(List<PartInfo> partInfoList) {
        log.info("add list total: {}", partInfoList.size());
        mongoTemplate.insertAll(partInfoList);
    }

    public PartInfo get(String id) {
        PartInfo partInfo = mongoTemplate.findById(id, PartInfo.class);
        assert partInfo != null;
        partInfo.setImage(getImage(partInfo.getImage()));
        return partInfo;
    }

    public PartInfo getByTitle(String title) {
        try {
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and("title").regex(title, "i");
            query.addCriteria(criteria);

            List<PartInfo> list = mongoTemplate.find(query, PartInfo.class, Constants.PART_INFO);
            if (!CollectionUtils.isEmpty(list)) {
                if (list.size() < 2) {
                    return list.get(0);
                } else {
                    log.warn("getByTitle size many, title: {}", title);
                    return list.get(0);
                }
            }
        } catch (Exception e) {
            log.error("getByTitle error", e);
            return null;
        }
        return null;
    }

    public Page<PartInfo> numberSearch(PartQueryDTO queryDTO) {
        Page<PartInfo> page = queryDTO.getPage();
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (!StringUtils.isEmpty(queryDTO.getNo())) {
            criteria.and("title").regex(queryDTO.getNo(), "i");
        }
        if (!StringUtils.isEmpty(queryDTO.getBrand())) {
            criteria.and("title").regex(queryDTO.getBrand(), "i");
        }
        if (!StringUtils.isEmpty(queryDTO.getCategory())) {
            criteria.and("title").regex(queryDTO.getCategory(), "i");
        }
        if (!StringUtils.isEmpty(queryDTO.getKeywords())) {
            criteria.and("title").regex(queryDTO.getKeywords(), "i");
        }
        query.addCriteria(criteria);

        long count = mongoTemplate.count(query, Constants.PART_INFO);

        query.limit(page.getPage_size());
        query.skip((page.getPage_no() - 1) * page.getPage_size());
        List<PartInfo> list = mongoTemplate.find(query, PartInfo.class, Constants.PART_INFO);

        page.formatTotal(count, list);
        return page;
    }

    public void listByModel(PartQueryDTO queryDTO, Model model) {
        Page<PartInfo> page = queryDTO.getPage();
        Query query = new Query();
        Criteria criteria = new Criteria();
        switch (queryDTO.getType()) {
            case 10:
                break;
            case 4:
                String condition = "^.*" + queryDTO.getKeywords() + ".*$";
                criteria.and("title").regex(condition, "i");
                model.addAttribute("keywords", queryDTO.getKeywords());
                break;
            case 1:
            case 3:
                String condition1 = "^.*";
                if (!StringUtils.isEmpty(queryDTO.getBrand())) {
                    condition1 += queryDTO.getBrand() + ".*";
                    model.addAttribute("brand", queryDTO.getBrand());
                }
                if (!StringUtils.isEmpty(queryDTO.getPlatfrom())) {
                    condition1 += queryDTO.getPlatfrom() + ".*";
                    model.addAttribute("platfrom", queryDTO.getPlatfrom());
                }
                if (!StringUtils.isEmpty(queryDTO.getCategory())) {
                    condition1 += queryDTO.getCategory() + ".*";
                    model.addAttribute("category", queryDTO.getCategory());
                }
                if (!StringUtils.isEmpty(queryDTO.getNo())) {
                    condition1 += queryDTO.getNo() + ".*";
                    model.addAttribute("no", queryDTO.getNo());
                }
                condition1 += "$";
                criteria.and("title").regex(condition1, "i");
                break;
            case 2:
                String condition2 = "^.*";
                if (!StringUtils.isEmpty(queryDTO.getKeywords())) {
                    condition2 += queryDTO.getKeywords() + ".*";
                    model.addAttribute("keywords", queryDTO.getKeywords());
                }
                if (!StringUtils.isEmpty(queryDTO.getNo())) {
                    condition2 += queryDTO.getNo() + ".*";
                    model.addAttribute("no", queryDTO.getNo());
                }
                condition2 += condition2 + "$";
                criteria.and("title").regex(condition2, "i");
                break;
        }
        query.addCriteria(criteria);

        long count = mongoTemplate.count(query, Constants.PART_INFO);
        log.info("query list {} ", count);

        query.limit(page.getPage_size());
        query.skip((page.getPage_no() - 1) * page.getPage_size());
        List<PartInfo> list = mongoTemplate.find(query, PartInfo.class, Constants.PART_INFO);

        list.forEach(partInfo -> partInfo.setImage(getImage(partInfo.getImage())));

        page.formatTotal(count, list);

        model.addAttribute("type", queryDTO.getType());
        model.addAttribute("result", page);
    }

    public List<PartInfo> getPartsList(String category, int size) {
        try {
            Query query = new Query();
            query.limit(size);
            Criteria criteria = Criteria.where("category").is(category);
            criteria.and("featured").is(1);

            query.addCriteria(criteria);
            List<PartInfo> partInfos = mongoTemplate.find(query, PartInfo.class, Constants.PART_INFO);

//            partInfos.forEach(partInfo -> partInfo.setImage(getImage(partInfo.getImage())));

            return partInfos;
        } catch (Exception e) {
            log.error("getPartsList error", e);
            return new ArrayList<PartInfo>(0);
        }
    }

    public String getImage(String url) {
        String imageUrl = null;
        String image = HttpUtil.get(url);
        if (image.contains("404 Not Found")) {
            String type = url.substring(url.length() - 3, url.length());
            String url1 = url.substring(0, url.length() - 3);
            if ("png".equals(type)) imageUrl = url1 + "jpg";
            if ("jpg".equals(type)) imageUrl = url1 + "png";
        } else imageUrl = url;
        return imageUrl;
    }
}
