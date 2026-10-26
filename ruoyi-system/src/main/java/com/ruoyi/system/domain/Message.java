package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 站内消息对象 t_message
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class Message extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 消息 id */
    private Long messageId;

    /** 接收用户 id */
    @Excel(name = "接收用户 id")
    private Long memberId;

    /** 消息类型（1系统通知 2开票进度 3红字进度 4注册进度） */
    @Excel(name = "消息类型", readConverterExp = "1=系统通知,2=开票进度,3=红字进度,4=注册进度")
    private String messageType;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 内容正文 */
    @Excel(name = "内容正文")
    private String content;

    /** 跳转业务类型（1开票 2红字 3注册 4认证 9通用） */
    @Excel(name = "跳转业务类型", readConverterExp = "1=开票,2=红字,3=注册,4=认证,9=通用")
    private String bizType;

    /** 跳转业务 id */
    @Excel(name = "跳转业务 id")
    private Long bizId;

    /** 是否已读（0未读 1已读） */
    @Excel(name = "是否已读", readConverterExp = "0=未读,1=已读")
    private String isRead;

    /** 已读时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "已读时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date readTime;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    public void setMessageId(Long messageId) 
    {
        this.messageId = messageId;
    }

    public Long getMessageId() 
    {
        return messageId;
    }

    public void setMemberId(Long memberId) 
    {
        this.memberId = memberId;
    }

    public Long getMemberId() 
    {
        return memberId;
    }

    public void setMessageType(String messageType) 
    {
        this.messageType = messageType;
    }

    public String getMessageType() 
    {
        return messageType;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
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

    public void setIsRead(String isRead) 
    {
        this.isRead = isRead;
    }

    public String getIsRead() 
    {
        return isRead;
    }

    public void setReadTime(Date readTime) 
    {
        this.readTime = readTime;
    }

    public Date getReadTime() 
    {
        return readTime;
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
            .append("messageId", getMessageId())
            .append("memberId", getMemberId())
            .append("messageType", getMessageType())
            .append("title", getTitle())
            .append("content", getContent())
            .append("bizType", getBizType())
            .append("bizId", getBizId())
            .append("isRead", getIsRead())
            .append("readTime", getReadTime())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .toString();
    }
}
