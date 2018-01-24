package com.wp.api;

import com.common.dto.Page;
import com.common.dto.ResultModel;
import com.common.enums.ErrorCode;
import com.common.enums.HttpCode;
import com.wp.dto.*;
import com.wp.service.CategoryService;
import com.wp.service.PostService;
import com.wp.service.TenantService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class PostController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private PostService postService;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询文章详情
     */
    @RequestMapping(value = "/posts/{postId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResultModel findPostById(@PathVariable("postId") Long postId, HttpServletRequest request, HttpServletResponse response) {
        ResultModel result = new ResultModel();
        Tenant tenant = getTenant(request, result);
        if (result.getStatus() != ErrorCode.success.value()) {
            return result;
        }
        Post post = postService.findById(postId, tenant.getTenantId());
        result.setData(post);
        return result;
    }

    /**
     * 查询所有分类
     */
    @RequestMapping(value = "/categories", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResultModel findAllCategories(HttpServletRequest request, HttpServletResponse response) {
        ResultModel result = new ResultModel();
        Tenant tenant = getTenant(request, result);
        if (result.getStatus() != ErrorCode.success.value()) {
            return result;
        }
        List<Category> categoryList = categoryService.findAllCategories(tenant.getTenantId());
        result.setData(categoryList);
        return result;
    }

    /**
     * 按分类查询文章详情列表
     */
    @RequestMapping(value = "/posts", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResultModel findPostsByCategory(Long categories, Integer page,
                                           Integer per_page, String order,
                                           HttpServletRequest request, HttpServletResponse response) {
        ResultModel result = new ResultModel();
        if (null == categories || categories < 1L) {
            result.setCode(HttpCode.errparam);
            result.setStatus(ErrorCode.validateFail);
            result.setInfo("categoryId can not be null or less than 1");
            return result;
        }
        Tenant tenant = getTenant(request, result);
        if (result.getStatus() != ErrorCode.success.value()) {
            return result;
        }
        if (null == page || page < 1) {
            page = 1;
        }
        if (null == per_page || per_page < 1) {
            per_page = 10;
        }
        List<Post> postList = postService.findPostsByCategory(categories, tenant.getTenantId(), page, per_page, order);
        result.setData(postList);
        Page page1 = new Page();
        page1.setLimit(per_page);
        page1.setOffset((page - 1) * per_page);
        result.setPage(page1);
        return result;
    }

    /**
     * 按文章ID查询评论列表
     */
    @RequestMapping(value = "/comments", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResultModel findCommentsByPostId(Long post, Long parent, Integer page,
                                            Integer per_page, String order,
                                            HttpServletRequest request, HttpServletResponse response) {
        ResultModel result = new ResultModel();
        if (null == post || post < 1L) {
            result.setCode(HttpCode.errparam);
            result.setStatus(ErrorCode.validateFail);
            result.setInfo("postId can not be null or less than 1");
            return result;
        }
        Tenant tenant = getTenant(request, result);
        if (result.getStatus() != ErrorCode.success.value()) {
            return result;
        }
        if (null == page || page < 1) {
            page = 1;
        }
        if (null == per_page || per_page < 1) {
            per_page = 20;
        }
        CommentDTO commentDTO = postService.findCommentsByPostId(post, parent, tenant.getTenantId(), page, per_page, order);
        result.setData(commentDTO);
        Page page1 = new Page();
        page1.setLimit(per_page);
        page1.setOffset((page - 1) * per_page);
        result.setPage(page1);
        return result;
    }

    /**
     * 客户端首屏数据
     */
    @RequestMapping(value = "/homepage", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResultModel homepage(HttpServletRequest request, HttpServletResponse response) {
        ResultModel result = new ResultModel();
        Tenant tenant = getTenant(request, result);
        if (result.getStatus() != ErrorCode.success.value()) {
            return result;
        }
        List<Category> categoryList = categoryService.findAllCategories(tenant.getTenantId());
        List<HomePageDTO> homePageDTOList = postService.homepage(tenant.getTenantId(), categoryList);
        result.setData(homePageDTOList);
        return result;
    }

    private Tenant getTenant(HttpServletRequest request, ResultModel result) {
        Tenant tenant = tenantService.findOne(request);
        if (null == tenant) {
            result.setCode(HttpCode.unauthorized);
            result.setStatus(ErrorCode.validateFail);
            result.setInfo("no tenant");
        }
        return tenant;
    }

}