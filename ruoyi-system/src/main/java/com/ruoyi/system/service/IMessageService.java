package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Message;

/**
 * 站内消息Service接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface IMessageService 
{
    /**
     * 查询站内消息
     * 
     * @param messageId 站内消息主键
     * @return 站内消息
     */
    public Message selectMessageByMessageId(Long messageId);

    /**
     * 查询站内消息列表
     * 
     * @param message 站内消息
     * @return 站内消息集合
     */
    public List<Message> selectMessageList(Message message);

    /**
     * 新增站内消息
     * 
     * @param message 站内消息
     * @return 结果
     */
    public int insertMessage(Message message);

    /**
     * 修改站内消息
     * 
     * @param message 站内消息
     * @return 结果
     */
    public int updateMessage(Message message);

    /**
     * 批量删除站内消息
     * 
     * @param messageIds 需要删除的站内消息主键集合
     * @return 结果
     */
    public int deleteMessageByMessageIds(Long[] messageIds);

    /**
     * 删除站内消息信息
     * 
     * @param messageId 站内消息主键
     * @return 结果
     */
    public int deleteMessageByMessageId(Long messageId);
}
