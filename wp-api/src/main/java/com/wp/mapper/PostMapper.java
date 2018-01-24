package com.wp.mapper;


import com.wp.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {

    List<Tenant> findTenant();

    Post findPost(@Param("id") long id, @Param("tenantId") long tenantId);

    List<CategoryDTO> findCategoriesByPostId(@Param("id") long id, @Param("tenantId") long tenantId);

    List<Category> findAllCategories(@Param("tenantId") Long tenantId);

    List<Post> findPostsByCategory(@Param("categoryId") Long categoryId, @Param("tenantId") Long tenantId,
                                   @Param("offset") Integer offset, @Param("limit") Integer limit,
                                   @Param("order") String order);

    List<Comment> findCommentsByPostId(@Param("postId") Long postId, @Param("tenantId") Long tenantId);

    List<Post> homepageData(@Param("categoryIdList") List<Long> categoryIdList, @Param("tenantId") Long tenantId);
}
