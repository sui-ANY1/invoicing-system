package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 统一附件对象 t_attachment
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class Attachment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 附件 id */
    private Long attachmentId;

    /** 业务类型（enterprise 主体、invoice_apply 开票、red_apply 红字、reg_apply 注册收件、reg_complete 注册完成凭证、manual_result 人工回填、temp 未关联临时上传等） */
    @Excel(name = "业务类型")
    private String bizType;

    /** 业务 id（未关联业务时为 0） */
    @Excel(name = "业务 id")
    private Long bizId;

    /** 材料类型（BUSINESS_LICENSE 营业执照、LEGAL_PERSON_ID_CARD_FRONT 法人身份证正面等） */
    @Excel(name = "材料类型")
    private String materialType;

    /** 关联对象键（同一业务下多份同类材料的人员标识） */
    @Excel(name = "关联对象键")
    private String refKey;

    /** 文件类型（pdf/ofd/xml/jpg/png 等） */
    @Excel(name = "文件类型")
    private String fileType;

    /** 文件名 */
    @Excel(name = "文件名")
    private String fileName;

    /** 文件访问 url */
    @Excel(name = "文件访问 url")
    private String fileUrl;

    /** 文件大小（字节） */
    @Excel(name = "文件大小", readConverterExp = "字=节")
    private Long fileSize;

    /** 上传人 */
    @Excel(name = "上传人")
    private String uploadBy;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    public void setAttachmentId(Long attachmentId) 
    {
        this.attachmentId = attachmentId;
    }

    public Long getAttachmentId() 
    {
        return attachmentId;
    }

    public void setBizType(String bizType) 
    {
        this.bizType = bizType;
    }

    public String getBizType() 
    {
        return bizType;
    }

    public void setBizId(Long bizId) 
    {
        this.bizId = bizId;
    }

    public Long getBizId() 
    {
        return bizId;
    }

    public void setMaterialType(String materialType)
    {
        this.materialType = materialType;
    }

    public String getMaterialType()
    {
        return materialType;
    }

    public void setRefKey(String refKey)
    {
        this.refKey = refKey;
    }

    public String getRefKey()
    {
        return refKey;
    }

    public void setFileType(String fileType) 
    {
        this.fileType = fileType;
    }

    public String getFileType() 
    {
        return fileType;
    }

    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }

    public void setFileUrl(String fileUrl) 
    {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() 
    {
        return fileUrl;
    }

    public void setFileSize(Long fileSize) 
    {
        this.fileSize = fileSize;
    }

    public Long getFileSize() 
    {
        return fileSize;
    }

    public void setUploadBy(String uploadBy) 
    {
        this.uploadBy = uploadBy;
    }

    public String getUploadBy() 
    {
        return uploadBy;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("attachmentId", getAttachmentId())
            .append("bizType", getBizType())
            .append("bizId", getBizId())
            .append("materialType", getMaterialType())
            .append("refKey", getRefKey())
            .append("fileType", getFileType())
            .append("fileName", getFileName())
            .append("fileUrl", getFileUrl())
            .append("fileSize", getFileSize())
            .append("uploadBy", getUploadBy())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .toString();
    }
}
