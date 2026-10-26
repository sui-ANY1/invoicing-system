package com.ruoyi.system.mini.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 小程序首页「需要我处理」待办行
 */
@Schema(description = "首页待办行")
public class MiniTodoVO {

    @Schema(description = "待办类型：taxVerify 税务验证待完成", example = "taxVerify")
    private String type;

    @Schema(description = "待办类型名称", example = "税务验证待完成")
    private String typeText;

    @Schema(description = "税务验证记录ID", example = "1")
    private Long verifyId;

    @Schema(description = "关联的开票申请ID", example = "1")
    private Long applyId;

    @Schema(description = "关联的开票申请编号", example = "APPLY20261019001")
    private String applyNo;

    @Schema(description = "企业主体ID", example = "1")
    private Long enterpriseId;

    @Schema(description = "企业名称", example = "某某市某某有限公司")
    private String enterpriseName;

    @Schema(description = "待办说明", example = "验证码待提交，请在有效期内完成税务验证")
    private String note;

    @Schema(description = "操作按钮文案", example = "去验证")
    private String actionText;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public Long getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(Long verifyId) {
        this.verifyId = verifyId;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getActionText() {
        return actionText;
    }

    public void setActionText(String actionText) {
        this.actionText = actionText;
    }
}
