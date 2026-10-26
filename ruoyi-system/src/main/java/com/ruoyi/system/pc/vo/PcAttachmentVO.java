package com.ruoyi.system.pc.vo;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 附件/材料视图（t_attachment）
 */
@Schema(description = "附件（材料）")
public class PcAttachmentVO {

    @Schema(description = "附件 id（受控预览/下载用）")
    private Long attachmentId;

    @Schema(description = "业务类型：invoice_apply / red_apply / reg_apply / enterprise / manual_result")
    private String bizType;

    @Schema(description = "业务 id（未绑定时为 0）")
    private Long bizId;

    @Schema(description = "材料码（见材料字典）")
    private String materialType;

    @Schema(description = "材料名称")
    private String materialName;

    @Schema(description = "扩展标识（股东名 / 联系人等）")
    private String refKey;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "原始文件名")
    private String fileName;

    @Schema(description = "文件相对路径")
    private String fileUrl;

    @Schema(description = "文件大小（字节）")
    private Long fileSize;

    @Schema(description = "上传人（会员 id 或 PC 用户名）")
    private String uploadBy;

    @Schema(description = "上传时间")
    private Date createTime;

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
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

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getRefKey() {
        return refKey;
    }

    public void setRefKey(String refKey) {
        this.refKey = refKey;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
