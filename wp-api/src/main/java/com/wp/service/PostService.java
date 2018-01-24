package com.wp.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.RedisCacheUtil;
import com.common.RedisKeysPreFix;
import com.common.StringUtil;
import com.common.enums.Order;
import com.wp.dto.*;
import com.wp.mapper.PostMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class PostService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private RedisCacheUtil redisCacheUtil;

    public Post findById(Long id, Long tenantId) {
        if (null == id || id <= 0) {
            return null;
        }

        String key = RedisKeysPreFix.POST_DETAIL_PREFIX + tenantId + "_" + id;
        String value = redisCacheUtil.getCache(key);
        if (!StringUtil.isEmpty(value)) {
            return JSONObject.parseObject(value, Post.class);
        }

        Post post = postMapper.findPost(id, tenantId);
        if (null != post) {
            post.setImgData();
            post.setCategories(postMapper.findCategoriesByPostId(id, tenantId));
            value = JSONObject.toJSONString(post);
            redisCacheUtil.setCache(key, value, 30L, TimeUnit.MINUTES);
        }

        return post;
    }

    public List<Post> findPostsByCategory(Long categoryId, Long tenantId, Integer page, Integer per_page, String order) {
        if (StringUtil.isEmpty(order) || !Order.asc.name().equalsIgnoreCase(order.trim())) {
            order = Order.desc.name();
        } else {
            order = Order.asc.name();
        }
        int hash = (categoryId + "_" + tenantId + "_" + page + "_" + per_page + "_" + order).hashCode();
        String key = RedisKeysPreFix.POSTS_BY_CATEGORY_PREFIX + hash;
        String value = redisCacheUtil.getCache(key);
        if (!StringUtil.isEmpty(value)) {
            return JSONArray.parseArray(value, Post.class);
        }

        List<Post> postList = postMapper.findPostsByCategory(categoryId, tenantId, (page - 1) * per_page, per_page, order);
        if (null != postList && !postList.isEmpty()) {
            List<CategoryDTO> categoryDTOS = postMapper.findCategoriesByPostId(postList.get(0).getId(), tenantId);
            for (Post post : postList) {
                post.setImgData();
                List<CategoryDTO> categoryDTOList = new ArrayList<>();
                categoryDTOS.stream().forEach((CategoryDTO item) -> categoryDTOList.add(item.clone()));
                post.setCategories(categoryDTOList);
            }

            value = JSONObject.toJSONString(postList);
            redisCacheUtil.setCache(key, value, 30L, TimeUnit.MINUTES);
        }
        return postList;
    }

    public CommentDTO findCommentsByPostId(Long postId, Long parentId, Long tenantId, Integer page, Integer per_page, String order) {
        if (StringUtil.isEmpty(order) || !Order.asc.name().equalsIgnoreCase(order.trim())) {
            order = Order.desc.name();
        } else {
            order = Order.asc.name();
        }
        if (null == parentId || parentId < 1L) {
            parentId = 0L;
        }
        CommentDTO dto = new CommentDTO();
        dto.setParentCount(0);
        dto.setListData(new ArrayList<>());

        List<Comment> commentList;

        String key = RedisKeysPreFix.COMMENTS_BY_POST_PREFIX + tenantId + "_" + postId;
        String value = redisCacheUtil.getCache(key);
        if (!StringUtil.isEmpty(value)) {
            commentList = JSONArray.parseArray(value, Comment.class);
        } else {
            commentList = postMapper.findCommentsByPostId(postId, tenantId);
            if (null != commentList && !commentList.isEmpty()) {
                value = JSONObject.toJSONString(commentList);
                redisCacheUtil.setCache(key, value, 30L, TimeUnit.MINUTES);
            } else {
                return dto;
            }
        }

        if (0L == parentId) {
            if (setParentList(page, per_page, order, dto, commentList)) return dto;
        } else {
            if (setChildList(parentId, page, per_page, order, dto, commentList)) return dto;
        }

        return dto;
    }

    public List<HomePageDTO> homepage(Long tenantId, List<Category> categoryList) {
        String key = RedisKeysPreFix.HOMEPAGE_PREFIX + tenantId;
        String value = redisCacheUtil.getCache(key);
        if (!StringUtil.isEmpty(value)) {
            return JSONArray.parseArray(value, HomePageDTO.class);
        }

        List<HomePageDTO> result = new ArrayList<>();
        List<Long> categoryIdList = new ArrayList<>();
        Map<Long, HomePageDTO> map = new HashMap<>();
        categoryList.stream().forEach(item -> {
            categoryIdList.add(item.getId());
            HomePageDTO dto = new HomePageDTO();
            dto.setCategoryId(item.getId());
            dto.setCategoryName(item.getName());
            result.add(dto);
            map.put(dto.getCategoryId(), dto);
        });
        List<Post> postList = postMapper.homepageData(categoryIdList, tenantId);
        if (null != postList && !postList.isEmpty()) {
            for (Post post : postList) {
                post.setImgData();
                PostForHomePageDTO postDto = getPostDto(post);
                map.get(post.getCategoryId()).getListData().add(postDto);
            }
        }
        value = JSONObject.toJSONString(result);
        redisCacheUtil.setCache(key, value, 30L, TimeUnit.MINUTES);
        return result;
    }

    private boolean setChildList(Long parentId, Integer page, Integer per_page, String order, CommentDTO dto, List<Comment> commentList) {
        List<Comment> commentChildren = new ArrayList<>();
        Comment parent = null;
        for (Comment item : commentList) {
            if (parentId.equals(item.getId())) {
                parent = item;
            }
            if (parentId.equals(item.getParent())) {
                commentChildren.add(item);
            }
        }
        if (null == parent) {
            return true;
        }
        int allChildCount = commentChildren.size();
        parent.setChildCount(allChildCount);
        dto.setParentCount(1);
        List<Comment> resultList = new ArrayList<>();
        resultList.add(parent);
        dto.setListData(resultList);
        int offset = (page - 1) * per_page;
        if (offset >= allChildCount) {
            return true;
        }

        int index = offset;
        int limit = 0;
        Boolean isDesc = Order.desc.name().equals(order);
        while (index < allChildCount && limit < per_page) {
            parent.getChild().add(commentChildren.get(isDesc ? index : allChildCount - index - 1));
            index++;
            limit++;
        }//分页取子评论
        return false;
    }

    private boolean setParentList(Integer page, Integer per_page, String order, CommentDTO dto, List<Comment> commentList) {
        int offset = (page - 1) * per_page;
        List<Comment> commentAllParents = new ArrayList<>();
        commentList.stream().forEach(item -> {
            if (0L == item.getParent()) {
                commentAllParents.add(item);
            }
        });//填充父评论

        int allParentCount = commentAllParents.size();
        if (offset >= allParentCount) {
            return true;
        }

        int index = offset;
        int limit = 0;
        int i;
        Boolean isDesc = Order.desc.name().equals(order);
        Map<Long, Comment> map = new HashMap<>();
        List<Comment> resultList = new ArrayList<>();//java语言的hashMap不保序
        while (index < allParentCount && limit < per_page) {
            i = isDesc ? index : allParentCount - index - 1;
            Comment comment = commentAllParents.get(i);
            resultList.add(comment);
            map.put(comment.getId(), comment);
            index++;
            limit++;
        }//分页取父评论

        final int maxChildCount = 3;
        commentList.stream().forEach(item -> {
            if (map.containsKey(item.getParent())) {
                Comment parentComment = map.get(item.getParent());
                parentComment.setChildCount(parentComment.getChildCount() + 1);
                if (parentComment.getChild().size() < maxChildCount) {
                    parentComment.getChild().add(item);
                }
            }
        });//填充子评论

        dto.setParentCount(allParentCount);
        dto.setListData(resultList);
        return false;
    }

    private PostForHomePageDTO getPostDto(Post post) {
        PostForHomePageDTO dto = new PostForHomePageDTO();
        dto.setCreateTime(post.getWelink_creatTime());
        dto.setId(post.getId());
        dto.setImgData(post.getWelink_imgData());
        dto.setNameCn(post.getWelink_nameCn());
        dto.setTitle(post.getWelink_title());
        return dto;
    }
}
