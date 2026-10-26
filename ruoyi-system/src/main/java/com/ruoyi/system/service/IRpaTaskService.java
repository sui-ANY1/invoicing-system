package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.RpaTask;

/**
 * RPA 任务Service接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface IRpaTaskService 
{
    /**
     * 查询RPA 任务
     * 
     * @param taskId RPA 任务主键
     * @return RPA 任务
     */
    public RpaTask selectRpaTaskByTaskId(Long taskId);

    /**
     * 查询RPA 任务列表
     * 
     * @param rpaTask RPA 任务
     * @return RPA 任务集合
     */
    public List<RpaTask> selectRpaTaskList(RpaTask rpaTask);

    /**
     * 新增RPA 任务
     * 
     * @param rpaTask RPA 任务
     * @return 结果
     */
    public int insertRpaTask(RpaTask rpaTask);

    /**
     * 修改RPA 任务
     * 
     * @param rpaTask RPA 任务
     * @return 结果
     */
    public int updateRpaTask(RpaTask rpaTask);

    /**
     * 批量删除RPA 任务
     * 
     * @param taskIds 需要删除的RPA 任务主键集合
     * @return 结果
     */
    public int deleteRpaTaskByTaskIds(Long[] taskIds);

    /**
     * 删除RPA 任务信息
     * 
     * @param taskId RPA 任务主键
     * @return 结果
     */
    public int deleteRpaTaskByTaskId(Long taskId);
}
