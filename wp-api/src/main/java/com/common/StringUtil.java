package com.common;

/**
 * 字符串工具类
 */
public final class StringUtil {
    public static Boolean isEmpty(String value) {
        return null == value || "".equals(value);
    }
}
