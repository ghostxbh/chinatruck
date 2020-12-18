package com.uzykj.chinatruck.utils;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author ghostxbh
 */
public class MongoUtils {
    public static Query getQueryFilter(Map<String, Object> filter) {
        Query query = new Query();
        if (CollectionUtils.isEmpty(filter)) {
            return query;
        }
        Criteria criteria = new Criteria();
        boolean w = false;
        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            if (!w) {
                criteria = (Criteria.where(entry.getKey()).is(entry.getValue()));
                w = true;
            } else {
                criteria = criteria.and(entry.getKey()).is(entry.getValue());
            }
        }
        query.addCriteria(criteria);
        return query;
    }
}
