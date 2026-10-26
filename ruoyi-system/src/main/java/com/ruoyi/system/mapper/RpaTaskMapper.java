package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.RpaTask;

/**
 * RPA 任务Mapper接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface RpaTaskMapper 
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
     * 删除RPA 任务
     * 
     * @param taskId RPA 任务主键
     * @return 结果
     */
    public int deleteRpaTaskByTaskId(Long taskId);

    /**
     * 批量删除RPA 任务
     * 
     * @param taskIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRpaTaskByTaskIds(Long[] taskIds);
}
