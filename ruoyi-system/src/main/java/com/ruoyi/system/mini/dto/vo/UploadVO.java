package com.ruoyi.system.mini.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "上传结果")
public class UploadVO {

    @Schema(description = "附件ID（提交业务时随材料清单一并回传，用于受控预览）", example = "12")
    private Long attachmentId;

    @Schema(description = "文件访问相对路径（前端用 baseURL 拼接）", example = "/profile/upload/2026/10/14/x.png")
    private String url;

    @Schema(description = "文件名（相对路径）", example = "/profile/upload/2026/10/14/x.png")
    private String fileName;

    @Schema(description = "原始文件名", example = "test.png")
    private String originalFilename;

    @Schema(description = "文件类型（pdf/jpg/png 等）", example = "png")
    private String fileType;

    @Schema(description = "文件大小（字节）", example = "20480")
    private Long fileSize;

    @Schema(description = "材料类型（上传时传入则原样返回）", example = "LEGAL_PERSON_ID_CARD_FRONT")
    private String materialType;

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
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

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }
}
