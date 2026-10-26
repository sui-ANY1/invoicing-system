package com.ruoyi.system.pc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.pc.dto.PcRequests;
import com.ruoyi.system.pc.mapper.PcWorkbenchMapper;
import com.ruoyi.system.pc.support.PcDict;
import com.ruoyi.system.pc.vo.PcTodoCountVO;
import com.ruoyi.system.pc.vo.PcTodoItemVO;

/**
 * PC 工作台（D01）：四类待办计数与待办分页列表
 *
 * 口径：待办来自开票申请、红字申请、RPA 任务、税务验证四个来源聚合，
 * 只统计未终结记录；「结果未知」只提示查询，不提供重开入口（AC22/AC27）。
 */
@Service
public class PcWorkbenchService {

    @Autowired
    private PcWorkbenchMapper workbenchMapper;

    /**
     * 工作台待办数量统计
     */
    public PcTodoCountVO counts() {
        return new PcTodoCountVO(
                workbenchMapper.countPendingAudit(),
                workbenchMapper.countNeedMaterial(),
                workbenchMapper.countWaitingUser(),
                workbenchMapper.countUnknownResult());
    }

    /**
     * 待办分页列表（分页由 Controller 控制）
     */
    public List<PcTodoItemVO> list(PcRequests.TodoQuery query) {
        List<PcTodoItemVO> list = workbenchMapper.selectTodoList(query);
        if (list != null) {
            for (PcTodoItemVO item : list) {
                item.setTypeText(PcDict.text(PcDict.TODO_TYPE, item.getType()));
                item.setStatusText(PcDict.todoStatusText(item.getStatusKind(), item.getStatus()));
            }
        }
        return list;
    }
}
