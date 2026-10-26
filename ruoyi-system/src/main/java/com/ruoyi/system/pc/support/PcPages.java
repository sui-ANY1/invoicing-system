package com.ruoyi.system.pc.support;

import com.github.pagehelper.PageHelper;
import com.ruoyi.system.pc.dto.PcRequests;

/**
 * PC 分页辅助
 *
 * 统一口径：分页参数放在请求体（pageNum/pageSize），不用 query 传参，便于后续整体加密。
 */
public final class PcPages {

    private PcPages() {
    }

    /**
     * 按请求体分页参数开启分页（默认 10 条／页，最大 200 条）
     */
    public static void start(PcRequests.PageQuery query) {
        start(query, null);
    }

    /**
     * 带排序的分页
     *
     * 注意：orderBy 必须是服务端固定字面量（如 "create_time desc"），禁止拼接前端入参，避免 SQL 注入。
     */
    public static void start(PcRequests.PageQuery query, String orderBy) {
        int pageNum = 1;
        int pageSize = 10;
        if (query != null) {
            if (query.getPageNum() != null && query.getPageNum() > 0) {
                pageNum = query.getPageNum();
            }
            if (query.getPageSize() != null && query.getPageSize() > 0) {
                pageSize = query.getPageSize();
            }
        }
        if (pageSize > 200) {
            pageSize = 200;
        }
        if (orderBy == null || orderBy.trim().isEmpty()) {
            PageHelper.startPage(pageNum, pageSize);
        } else {
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }
}
