package com.uzykj.chinatruck.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.uzykj.chinatruck.common.Constants;
import com.uzykj.chinatruck.domain.Component;
import com.uzykj.chinatruck.domain.PartInfo;
import com.uzykj.chinatruck.domain.Parts;
import com.uzykj.chinatruck.domain.dto.PartQueryDTO;
import com.uzykj.chinatruck.domain.vo.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ghostxbh
 */
@Slf4j
@Service
public class PartInfoService {
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private ComponentService componentService;

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

    public List<PartInfo> getByComponent(String component) {
        try {
            Query query = new Query();
            Criteria criteria = Criteria.where("component_id").is(component);
            query.addCriteria(criteria);
            List<PartInfo> partInfoList = mongoTemplate.find(query, PartInfo.class, Constants.PART_INFO);

            if (CollectionUtils.isEmpty(partInfoList)) {
                log.info("this component not found parts, go to miss data!");
                this.getMissData(component);
                return new ArrayList<PartInfo>(0);
            } else {
                partInfoList.forEach(partInfo -> partInfo.setImage(getImage(partInfo.getImage())));
                return partInfoList;
            }
        } catch (Exception e) {
            log.error("getByComponent error", e);
            return new ArrayList<PartInfo>(0);
        }
    }

    @Async
    public void getMissData(String componentId) {
        Component component = componentService.get(componentId);

        String cateList = HttpUtil.get(QueueQueryService.CATEURL + component.getData_id());
        List<Parts> parts = JSONArray.parseArray(cateList, Parts.class);

        log.info("componentId: {} get miss data size: {}", componentId, parts != null ? parts.size() : 0);

        Optional.ofNullable(parts)
                .orElse(new ArrayList<Parts>(0))
                .forEach(part -> {
                    PartInfo byTitle = this.getByTitle(part.getName());
                    if (byTitle != null) {
                        Query query = Query.query(Criteria.where("_id").is(byTitle.get_id()));
                        Update update = Update.update("component_id", componentId);
                        mongoTemplate.upsert(query, update, Constants.PART_INFO);
                        log.info("add part'name:{} component: {}", byTitle.getTitle(), componentId);
                    } else {
                        try {
                            String image = part.getImage();
                            if (!StringUtils.isEmpty(image) && image.contains("/")) {
                                String[] split = image.split("/");
                                image = split[split.length - 1];
                            }

                            PartInfo partInfo = PartInfo.builder()
                                    .title(part.getName())
                                    .brand(part.getShort_description())
                                    .part(part.getSku())
                                    .url(QueueQueryService.SOURCE_URL + part.getUrl_path())
                                    .image(QueueQueryService.NEW_IMAGE_URL + image)
                                    .component_id(componentId)
                                    .build();

                            log.info("componentId: {} add new part: {}", componentId, partInfo);

                            mongoTemplate.save(partInfo);
                        } catch (Exception e) {
                            log.error("add new part error, componentId: {} ,title: {}", componentId, part.getName());
                        }
                    }
                });
        log.info("componentId: {} add miss data success!", componentId);
    }
}
