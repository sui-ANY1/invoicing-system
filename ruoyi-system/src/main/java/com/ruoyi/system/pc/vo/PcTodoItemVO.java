package com.ruoyi.system.pc.vo;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * D01 工作台待办行
 */
@Schema(description = "工作台待办行")
public class PcTodoItemVO {

    @Schema(description = "待办类型：audit 待审核 / material 需补资料 / user 待用户操作 / unknown 结果未知 / file 文件异常")
    private String type;

    @Schema(description = "待办类型名称")
    private String typeText;

    @Schema(description = "业务类型：1开票 2红字")
    private String bizType;

    @Schema(description = "业务 id（开票为 applyId，红字为 redApplyId，任务为 taskId 时 bizId 仍为业务 id）")
    private Long bizId;

    @Schema(description = "业务编号：申请编号 / 红字申请编号 / 任务号")
    private String bizNo;

    @Schema(description = "企业 id")
    private Long enterpriseId;

    @Schema(description = "企业名称")
    private String enterpriseName;

    @Schema(description = "业务状态原值")
    private String status;

    @Schema(description = "业务状态名称")
    private String statusText;

    @Schema(description = "购方名称")
    private String buyerName;

    @Schema(description = "提交时间")
    private Date createTime;

    @Schema(description = "下次查询时间（结果未知类待办使用）")
    private Date nextQueryTime;

    @Schema(description = "状态字典来源：apply 开票状态 / red 红字状态 / rpa RPA 任务状态 / verify 税务验证状态 / file 文件状态")
    private String statusKind;

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

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getNextQueryTime() {
        return nextQueryTime;
    }

    public void setNextQueryTime(Date nextQueryTime) {
        this.nextQueryTime = nextQueryTime;
    }

    public String getStatusKind() {
        return statusKind;
    }

    public void setStatusKind(String statusKind) {
        this.statusKind = statusKind;
    }
}
