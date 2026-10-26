package com.ruoyi.system.mini.support;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.ruoyi.system.mini.dto.vo.PageResult;

/**
 * 小程序列表分页工具
 *
 * 约定：pageNum 默认 1，pageSize 默认 10、上限 200；返回值统一带回 total，便于前端渲染"共 N 条"。
 */
public final class MiniPageUtils {

    /** 默认页码 */
    public static final int DEFAULT_PAGE_NUM = 1;

    /** 默认每页条数 */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /** 每页条数上限 */
    public static final int MAX_PAGE_SIZE = 200;

    private MiniPageUtils() {
    }

    /**
     * 先按条件过滤，再按页码切片
     */
    public static <T> PageResult<T> of(List<T> all, Integer pageNum, Integer pageSize, Predicate<T> filter) {
        List<T> src = all == null ? new ArrayList<>() : all;
        if (filter != null) {
            List<T> filtered = new ArrayList<>();
            for (T item : src) {
                if (filter.test(item)) {
                    filtered.add(item);
                }
            }
            src = filtered;
        }
        return slice(src, pageNum, pageSize);
    }

    /**
     * 直接按页码切片
     */
    public static <T> PageResult<T> of(List<T> all, Integer pageNum, Integer pageSize) {
        return of(all, pageNum, pageSize, null);
    }

    private static <T> PageResult<T> slice(List<T> src, Integer pageNum, Integer pageSize) {
        int num = (pageNum == null || pageNum < 1) ? DEFAULT_PAGE_NUM : pageNum;
        int size = (pageSize == null || pageSize < 1) ? DEFAULT_PAGE_SIZE : Math.min(pageSize, MAX_PAGE_SIZE);
        long total = src.size();
        long fromLong = (long) (num - 1) * size;
        if (fromLong >= total) {
            return new PageResult<>(total, new ArrayList<>());
        }
        int from = (int) fromLong;
        return new PageResult<>(total, new ArrayList<>(src.subList(from, Math.min(from + size, src.size()))));
    }

    /**
     * 模糊匹配（忽略大小写）：字段为空视为不匹配
     */
    public static boolean containsIgnoreCase(String value, String lowerKeyword) {
        return value != null && lowerKeyword != null && value.toLowerCase().contains(lowerKeyword);
    }
}
