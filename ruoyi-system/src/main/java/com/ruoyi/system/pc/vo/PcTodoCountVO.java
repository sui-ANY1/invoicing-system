package com.ruoyi.system.pc.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * D01 工作台五类待办计数
 *
 * 口径与待办列表一致（同一套 SQL 条件），卡片数字与列表条数可直接对账。
 *
 * @author sui-ANY1
 */
@Schema(description = "工作台待办计数")
public class PcTodoCountVO {

    @Schema(description = "待审核：企业认证 / 开票申请等待后台审核", example = "1")
    private long audit;

    @Schema(description = "需补资料：审核结论为「2 要求补充」，等待补件", example = "0")
    private long material;

    @Schema(description = "待用户操作：等待会员在小程序完成确认 / 验证", example = "2")
    private long user;

    @Schema(description = "结果未知：RPA 任务结果未确认，只可查询不可重开", example = "1")
    private long unknown;

    public PcTodoCountVO() {
    }

    public PcTodoCountVO(long audit, long material, long user, long unknown) {
        this.audit = audit;
        this.material = material;
        this.user = user;
        this.unknown = unknown;
    }

    public long getAudit() {
        return audit;
    }

    public void setAudit(long audit) {
        this.audit = audit;
    }

    public long getMaterial() {
        return material;
    }

    public void setMaterial(long material) {
        this.material = material;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getUnknown() {
        return unknown;
    }

    public void setUnknown(long unknown) {
        this.unknown = unknown;
    }
}
