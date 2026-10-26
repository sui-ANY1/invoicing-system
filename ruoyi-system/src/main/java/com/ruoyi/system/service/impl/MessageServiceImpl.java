package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.MessageMapper;
import com.ruoyi.system.domain.Message;
import com.ruoyi.system.service.IMessageService;

/**
 * 站内消息Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class MessageServiceImpl implements IMessageService 
{
    @Autowired
    private MessageMapper messageMapper;

    /**
     * 查询站内消息
     * 
     * @param messageId 站内消息主键
     * @return 站内消息
     */
    @Override
    public Message selectMessageByMessageId(Long messageId)
    {
        return messageMapper.selectMessageByMessageId(messageId);
    }

    /**
     * 查询站内消息列表
     * 
     * @param message 站内消息
     * @return 站内消息
     */
    @Override
    public List<Message> selectMessageList(Message message)
    {
        return messageMapper.selectMessageList(message);
    }

    /**
     * 新增站内消息
     * 
     * @param message 站内消息
     * @return 结果
     */
    @Override
    public int insertMessage(Message message)
    {
        message.setCreateTime(DateUtils.getNowDate());
        return messageMapper.insertMessage(message);
    }

    /**
     * 修改站内消息
     * 
     * @param message 站内消息
     * @return 结果
     */
    @Override
    public int updateMessage(Message message)
    {
        return messageMapper.updateMessage(message);
    }

    /**
     * 批量删除站内消息
     * 
     * @param messageIds 需要删除的站内消息主键
     * @return 结果
     */
    @Override
    public int deleteMessageByMessageIds(Long[] messageIds)
    {
        return messageMapper.deleteMessageByMessageIds(messageIds);
    }

    /**
     * 删除站内消息信息
     * 
     * @param messageId 站内消息主键
     * @return 结果
     */
    @Override
    public int deleteMessageByMessageId(Long messageId)
    {
        return messageMapper.deleteMessageByMessageId(messageId);
    }
}
