package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.RedApplyMapper;
import com.ruoyi.system.domain.RedApply;
import com.ruoyi.system.service.IRedApplyService;

/**
 * 红字申请Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class RedApplyServiceImpl implements IRedApplyService 
{
    @Autowired
    private RedApplyMapper redApplyMapper;

    /**
     * 查询红字申请
     * 
     * @param redApplyId 红字申请主键
     * @return 红字申请
     */
    @Override
    public RedApply selectRedApplyByRedApplyId(Long redApplyId)
    {
        return redApplyMapper.selectRedApplyByRedApplyId(redApplyId);
    }

    /**
     * 查询红字申请列表
     * 
     * @param redApply 红字申请
     * @return 红字申请
     */
    @Override
    public List<RedApply> selectRedApplyList(RedApply redApply)
    {
        return redApplyMapper.selectRedApplyList(redApply);
    }

    /**
     * 新增红字申请
     * 
     * @param redApply 红字申请
     * @return 结果
     */
    @Override
    public int insertRedApply(RedApply redApply)
    {
        redApply.setCreateTime(DateUtils.getNowDate());
        return redApplyMapper.insertRedApply(redApply);
    }

    /**
     * 修改红字申请
     * 
     * @param redApply 红字申请
     * @return 结果
     */
    @Override
    public int updateRedApply(RedApply redApply)
    {
        redApply.setUpdateTime(DateUtils.getNowDate());
        return redApplyMapper.updateRedApply(redApply);
    }

    /**
     * 批量删除红字申请
     * 
     * @param redApplyIds 需要删除的红字申请主键
     * @return 结果
     */
    @Override
    public int deleteRedApplyByRedApplyIds(Long[] redApplyIds)
    {
        return redApplyMapper.deleteRedApplyByRedApplyIds(redApplyIds);
    }

    /**
     * 删除红字申请信息
     * 
     * @param redApplyId 红字申请主键
     * @return 结果
     */
    @Override
    public int deleteRedApplyByRedApplyId(Long redApplyId)
    {
        return redApplyMapper.deleteRedApplyByRedApplyId(redApplyId);
    }
}
