package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.RpaTaskMapper;
import com.ruoyi.system.domain.RpaTask;
import com.ruoyi.system.service.IRpaTaskService;

/**
 * RPA 任务Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class RpaTaskServiceImpl implements IRpaTaskService 
{
    @Autowired
    private RpaTaskMapper rpaTaskMapper;

    /**
     * 查询RPA 任务
     * 
     * @param taskId RPA 任务主键
     * @return RPA 任务
     */
    @Override
    public RpaTask selectRpaTaskByTaskId(Long taskId)
    {
        return rpaTaskMapper.selectRpaTaskByTaskId(taskId);
    }

    /**
     * 查询RPA 任务列表
     * 
     * @param rpaTask RPA 任务
     * @return RPA 任务
     */
    @Override
    public List<RpaTask> selectRpaTaskList(RpaTask rpaTask)
    {
        return rpaTaskMapper.selectRpaTaskList(rpaTask);
    }

    /**
     * 新增RPA 任务
     * 
     * @param rpaTask RPA 任务
     * @return 结果
     */
    @Override
    public int insertRpaTask(RpaTask rpaTask)
    {
        rpaTask.setCreateTime(DateUtils.getNowDate());
        return rpaTaskMapper.insertRpaTask(rpaTask);
    }

    /**
     * 修改RPA 任务
     * 
     * @param rpaTask RPA 任务
     * @return 结果
     */
    @Override
    public int updateRpaTask(RpaTask rpaTask)
    {
        rpaTask.setUpdateTime(DateUtils.getNowDate());
        return rpaTaskMapper.updateRpaTask(rpaTask);
    }

    /**
     * 批量删除RPA 任务
     * 
     * @param taskIds 需要删除的RPA 任务主键
     * @return 结果
     */
    @Override
    public int deleteRpaTaskByTaskIds(Long[] taskIds)
    {
        return rpaTaskMapper.deleteRpaTaskByTaskIds(taskIds);
    }

    /**
     * 删除RPA 任务信息
     * 
     * @param taskId RPA 任务主键
     * @return 结果
     */
    @Override
    public int deleteRpaTaskByTaskId(Long taskId)
    {
        return rpaTaskMapper.deleteRpaTaskByTaskId(taskId);
    }
}
