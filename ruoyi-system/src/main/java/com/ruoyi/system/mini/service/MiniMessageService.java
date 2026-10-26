package com.ruoyi.system.mini.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.Message;
import com.ruoyi.system.mini.dto.vo.MessageVO;
import com.ruoyi.system.service.IMessageService;

/**
 * 小程序站内消息
 */
@Service
public class MiniMessageService {

    @Autowired
    private IMessageService messageService;

    /**
     * 当前会员的消息列表（实体 -> VO）
     */
    public List<MessageVO> list(Long memberId) {
        Message q = new Message();
        q.setMemberId(memberId);
        q.setDelFlag("0");
        List<Message> list = messageService.selectMessageList(q);

        List<MessageVO> result = new ArrayList<>();
        if (list != null) {
            for (Message m : list) {
                MessageVO vo = new MessageVO();
                vo.setId(m.getMessageId());
                vo.setMessageType(m.getMessageType());
                vo.setTitle(m.getTitle());
                vo.setContent(m.getContent());
                vo.setBizType(m.getBizType());
                vo.setBizId(m.getBizId());
                vo.setIsRead(m.getIsRead());
                vo.setReadTime(m.getReadTime());
                vo.setCreateTime(m.getCreateTime());
                result.add(vo);
            }
        }
        return result;
    }

    /**
     * 标记已读：ids 为空则当前会员全部未读已读，否则只标记指定消息。返回标记条数。
     */
    public int read(Long memberId, List<Long> ids) {
        Message q = new Message();
        q.setMemberId(memberId);
        q.setDelFlag("0");
        List<Message> mine = messageService.selectMessageList(q);
        if (mine == null) {
            return 0;
        }

        int count = 0;
        Date now = new Date();
        for (Message m : mine) {
            if ("1".equals(m.getIsRead())) {
                continue;
            }
            if (ids != null && !ids.isEmpty() && !ids.contains(m.getMessageId())) {
                continue;
            }
            m.setIsRead("1");
            m.setReadTime(now);
            messageService.updateMessage(m);
            count++;
        }
        return count;
    }
}
