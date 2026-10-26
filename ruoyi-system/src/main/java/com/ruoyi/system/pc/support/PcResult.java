package com.ruoyi.system.pc.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.constant.HttpStatus;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * PC 后台统一响应结构
 *
 * 字段与 RuoYi 的 AjaxResult、小程序端的 R&lt;T&gt; 完全一致（code/msg/data），
 * 用泛型 T 声明 data 的具体类型，便于 SpringDoc/Apifox 生成逐字段中文文档。
 *
 * 分页接口的 data 为 {@link PcPage}（data.total / data.rows），不再使用 RuoYi 的 TableDataInfo。
 *
 * @author sui-ANY1
 */
@Schema(description = "PC 后台统一响应结构")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PcResult<T> {

    @Schema(description = "状态码，200表示成功", example = "200")
    private int code;

    @Schema(description = "提示信息", example = "操作成功")
    private String msg;

    @Schema(description = "业务数据")
    private T data;

    public PcResult() {
    }

    public PcResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功（无数据）
     */
    public static <T> PcResult<T> ok() {
        return new PcResult<>(HttpStatus.SUCCESS, "操作成功", null);
    }

    /**
     * 成功并返回数据
     */
    public static <T> PcResult<T> ok(T data) {
        return new PcResult<>(HttpStatus.SUCCESS, "操作成功", data);
    }

    /**
     * 成功并使用自定义提示信息（无数据）
     */
    public static <T> PcResult<T> okMsg(String msg) {
        return new PcResult<>(HttpStatus.SUCCESS, msg, null);
    }

    /**
     * 失败（默认 500）
     */
    public static <T> PcResult<T> fail(String msg) {
        return new PcResult<>(HttpStatus.ERROR, msg, null);
    }

    /**
     * 失败并指定状态码
     */
    public static <T> PcResult<T> fail(int code, String msg) {
        return new PcResult<>(code, msg, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
