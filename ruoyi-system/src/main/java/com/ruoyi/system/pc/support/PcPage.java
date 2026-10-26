package com.ruoyi.system.pc.support;

import java.util.List;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * PC 后台分页数据
 *
 * 统一放在响应结构的 data 里：{ "code":200, "msg":"操作成功", "data":{ "total":128, "rows":[...] } }
 *
 * @author sui-ANY1
 */
@Schema(description = "分页数据")
public class PcPage<T> {

    @Schema(description = "总记录数（不受分页影响）", example = "128")
    private long total;

    @Schema(description = "当前页数据列表")
    private List<T> rows;

    public PcPage() {
    }

    public PcPage(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    /**
     * 由分页查询结果构建：total 取 PageHelper 的 total，非分页 List 时等于集合大小
     */
    public static <T> PcPage<T> of(List<T> list) {
        long total = list == null ? 0L : new PageInfo<>(list).getTotal();
        return new PcPage<>(total, list);
    }

    /**
     * 指定 total 构建（用于自定义统计口径）
     */
    public static <T> PcPage<T> of(List<T> list, long total) {
        return new PcPage<>(total, list);
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
        this.rows = rows;
    }
}
