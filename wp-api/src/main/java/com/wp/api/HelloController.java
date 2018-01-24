package com.wp.api;

import com.common.RedisCacheUtil;
import com.common.dto.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class HelloController {
    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResultModel hello(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultModel result = new ResultModel();
        result.setInfo("OK");
        return result;
    }

    @RequestMapping(value = "/cache", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    public ResultModel deleteCache(@RequestParam String key, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultModel result = new ResultModel();
        result.setInfo("OK");
        redisCacheUtil.delCache(key);
        return result;
    }

}
