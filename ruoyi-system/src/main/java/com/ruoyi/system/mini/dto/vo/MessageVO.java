package com.ruoyi.system.mini.dto.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "消息列表项")
public class MessageVO {

    @Schema(description = "消息ID", example = "1")
    private Long id;

    @Schema(description = "消息类型：1系统通知 2开票进度 3红字进度 4注册进度", example = "1")
    private String messageType;

    @Schema(description = "标题", example = "主体认证已通过")
    private String title;

    @Schema(description = "内容正文", example = "您的企业主体认证已审核通过")
    private String content;

    @Schema(description = "跳转业务类型：1开票 2红字 3注册 4认证 9通用", example = "4")
    private String bizType;

    @Schema(description = "跳转业务ID", example = "1")
    private Long bizId;

    @Schema(description = "是否已读：0未读 1已读", example = "0")
    private String isRead;

    @Schema(description = "已读时间", example = "2026-10-15 10:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date readTime;

    @Schema(description = "发送时间", example = "2026-10-15 09:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
