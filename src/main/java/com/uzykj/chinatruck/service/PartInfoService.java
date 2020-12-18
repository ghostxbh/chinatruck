package com.uzykj.chinatruck.service;

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

import javax.annotation.Resource;
import java.util.List;

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
        return mongoTemplate.findById(id, PartInfo.class);
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

        page.formatTotal(count, list);

        model.addAttribute("type", queryDTO.getType());
        model.addAttribute("result", page);
    }
}