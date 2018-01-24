package com.wp.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.RedisCacheUtil;
import com.common.RedisKeysPreFix;
import com.common.StringUtil;
import com.wp.dto.Category;
import com.wp.dto.Post;
import com.wp.mapper.PostMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CategoryService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private RedisCacheUtil redisCacheUtil;

    /**
     * 查询所有分类
     *
     * @return
     */
    public List<Category> findAllCategories(Long tenantId) {
        String key = RedisKeysPreFix.CATEGORY_ALL + tenantId;
        String value = redisCacheUtil.getCache(key);
        if (!StringUtil.isEmpty(value)) {
            return JSONArray.parseArray(value, Category.class);
        }

        List<Category> categoryList = postMapper.findAllCategories(tenantId);
        if (null != categoryList && !categoryList.isEmpty()) {
            value = JSONObject.toJSONString(categoryList);
            redisCacheUtil.setCache(key, value, 30L, TimeUnit.DAYS);
        }

        return categoryList;
    }

}
