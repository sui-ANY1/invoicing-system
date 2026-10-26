package com.ruoyi.system.mini.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.constant.HttpStatus;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 小程序统一响应结构
 *
 * 字段与 RuoYi 的 AjaxResult 完全一致（code/msg/data），运行时返回的 JSON 不变；
 * 使用泛型 T 声明 data 的具体类型，便于 SpringDoc 生成精确的响应字段文档。
 *
 * @author sui-ANY1
 */
@Schema(description = "小程序统一响应结构")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R<T> {

    @Schema(description = "状态码，200表示成功", example = "200")
    private int code;

    @Schema(description = "提示信息", example = "操作成功")
    private String msg;

    @Schema(description = "业务数据")
    private T data;

    public R() {
    }

    public R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功（无数据）
     */
    public static <T> R<T> ok() {
        return new R<>(HttpStatus.SUCCESS, "操作成功", null);
    }

    /**
     * 成功并返回数据
     */
    public static <T> R<T> ok(T data) {
        return new R<>(HttpStatus.SUCCESS, "操作成功", data);
    }

    /**
     * 失败（默认 500）
     */
    public static <T> R<T> fail(String msg) {
        return new R<>(HttpStatus.ERROR, msg, null);
    }

    /**
     * 失败并指定状态码
     */
    public static <T> R<T> fail(int code, String msg) {
        return new R<>(code, msg, null);
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
