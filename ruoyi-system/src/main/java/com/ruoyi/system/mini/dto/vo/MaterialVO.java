package com.ruoyi.system.mini.dto.vo;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "材料（附件）")
public class MaterialVO {

    @Schema(description = "附件ID（注册收件材料用 /api/mini/reg/file/{id} 受控预览）", example = "12")
    private Long attachmentId;

    @Schema(description = "材料类型码", example = "LEGAL_PERSON_ID_CARD_FRONT")
    private String materialType;

    @Schema(description = "关联对象键（如股东/经办人姓名）", example = "张三")
    private String refKey;

    @Schema(description = "原始文件名", example = "front.png")
    private String fileName;

    @Schema(description = "文件访问路径", example = "/profile/upload/2026/10/14/front_xxx.png")
    private String fileUrl;

    @Schema(description = "文件类型", example = "png")
    private String fileType;

    @Schema(description = "文件大小（字节）", example = "20480")
    private Long fileSize;

    @Schema(description = "上传时间", example = "2026-10-16 10:00:00")
    private Date createTime;

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getRefKey() {
        return refKey;
    }

    public void setRefKey(String refKey) {
        this.refKey = refKey;
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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
