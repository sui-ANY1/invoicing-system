package com.ruoyi.system.mini.dto.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "税务验证结果")
public class TaxVerifyVO {

    @Schema(description = "税务验证记录ID", example = "1")
    private Long id;

    @Schema(description = "业务类型：1蓝字开票 2红字", example = "1")
    private String bizType;

    @Schema(description = "关联业务ID（开票申请/红字申请ID）", example = "1")
    private Long bizId;

    @Schema(description = "验证方式：1短信", example = "1")
    private String verifyType;

    @Schema(description = "接收账号（手机号，脱敏显示）", example = "138****0000")
    private String targetAccount;

    @Schema(description = "验证状态：0已发起待验证 1已提交待填 2验证通过 3验证失败/过期", example = "2")
    private String verifyStatus;

    @Schema(description = "验证过期时间", example = "2026-10-15 12:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(String verifyType) {
        this.verifyType = verifyType;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
