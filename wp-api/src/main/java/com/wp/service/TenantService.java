package com.wp.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.RedisCacheUtil;
import com.common.RedisKeysPreFix;
import com.common.StringUtil;
import com.wp.dto.Tenant;
import com.wp.mapper.PostMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TenantService {
    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private RedisCacheUtil redisCacheUtil;


    @Value("${domain}")
    private String domain;

    /**
     * 获取租户id（路径），比如huawei
     *
     * @param request
     * @return
     */
    public String getTenantId(HttpServletRequest request) {
        return request.getHeader("tenantid");
    }

    /**
     * 获取租户账号ID，比如test1
     *
     * @param request
     * @return
     */
    public String getAccountId(HttpServletRequest request) {
        return request.getHeader("accountid");
    }

    /**
     * 查询所有租户
     */
    public List<Tenant> findTenant() {
        String key = RedisKeysPreFix.TENANT_ALL;
        String value = redisCacheUtil.getCache(key);
        if (!StringUtil.isEmpty(value)) {
            return JSONArray.parseArray(value, Tenant.class);
        }

        List<Tenant> tenantList = postMapper.findTenant();
        if (null != tenantList && !tenantList.isEmpty()) {
            value = JSONObject.toJSONString(tenantList);
            redisCacheUtil.setCache(key, value, 30L, TimeUnit.DAYS);
        }

        return tenantList;
    }

    /**
     * 查询单个租户
     */
    public Tenant findOne(HttpServletRequest request) {
        String path = "/cms/" + getTenantId(request) + "/";

        List<Tenant> tenantList = this.findTenant();
        for (Tenant tenant : tenantList) {
            if (tenant.getDomain().equals(this.domain) && tenant.getPath().equals(path)) {
                return tenant;//没有采用克隆，严格的写法要用克隆才能避免内存泄漏
            }
        }
        return null;
    }
}
