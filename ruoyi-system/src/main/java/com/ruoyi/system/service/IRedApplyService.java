package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.RedApply;

/**
 * 红字申请Service接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface IRedApplyService 
{
    /**
     * 查询红字申请
     * 
     * @param redApplyId 红字申请主键
     * @return 红字申请
     */
    public RedApply selectRedApplyByRedApplyId(Long redApplyId);

    /**
     * 查询红字申请列表
     * 
     * @param redApply 红字申请
     * @return 红字申请集合
     */
    public List<RedApply> selectRedApplyList(RedApply redApply);

    /**
     * 新增红字申请
     * 
     * @param redApply 红字申请
     * @return 结果
     */
    public int insertRedApply(RedApply redApply);

    /**
     * 修改红字申请
     * 
     * @param redApply 红字申请
     * @return 结果
     */
    public int updateRedApply(RedApply redApply);

    /**
     * 批量删除红字申请
     * 
     * @param redApplyIds 需要删除的红字申请主键集合
     * @return 结果
     */
    public int deleteRedApplyByRedApplyIds(Long[] redApplyIds);

    /**
     * 删除红字申请信息
     * 
     * @param redApplyId 红字申请主键
     * @return 结果
     */
    public int deleteRedApplyByRedApplyId(Long redApplyId);
}
