package com.ruoyi.system.mini.dto.vo;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 统一分页返回体：{@code total} 总条数 + {@code rows} 当前页数据
 *
 * 小程序所有列表接口都用这个结构，便于前端直接渲染分页控件。
 */
public class PageResult<T> {

    @Schema(description = "总条数（满足查询条件的全部记录数，不是当前页条数）", example = "37")
    private long total;

    @Schema(description = "当前页数据")
    private List<T> rows;

    public PageResult() {
        this.rows = new ArrayList<>();
    }

    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows == null ? new ArrayList<>() : rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows == null ? new ArrayList<>() : rows;
    }
}
