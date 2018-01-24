package com.common;

public final class RedisKeysPreFix {

    /**
     * 缓存键的前缀
     */
    private static final String REDIS_PREFIX = "3ms_wp_api:";

    /**
     * 文章详情缓存
     */
    public static final String POST_DETAIL_PREFIX = REDIS_PREFIX + "post_detail_";

    /**
     * 所有租户缓存
     */
    public static final String TENANT_ALL = REDIS_PREFIX + "tenant_all";

    /**
     * 所有分类缓存
     */
    public static final String CATEGORY_ALL = REDIS_PREFIX + "category_all_";

    /**
     * 按分类获取文章详情列表缓存
     */
    public static final String POSTS_BY_CATEGORY_PREFIX = REDIS_PREFIX + "posts_category_";

    /**
     * 按文章ID获取评论列表缓存
     */
    public static final String COMMENTS_BY_POST_PREFIX = REDIS_PREFIX + "comments_post_";

    /**
     * 客户端首页缓存
     */
    public static final String HOMEPAGE_PREFIX = REDIS_PREFIX + "homepage_";

}
