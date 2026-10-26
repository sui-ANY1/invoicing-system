package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.RegApplyMapper;
import com.ruoyi.system.domain.RegApply;
import com.ruoyi.system.service.IRegApplyService;

/**
 * 注册收件Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class RegApplyServiceImpl implements IRegApplyService 
{
    @Autowired
    private RegApplyMapper regApplyMapper;

    /**
     * 查询注册收件
     * 
     * @param regApplyId 注册收件主键
     * @return 注册收件
     */
    @Override
    public RegApply selectRegApplyByRegApplyId(Long regApplyId)
    {
        return regApplyMapper.selectRegApplyByRegApplyId(regApplyId);
    }

    /**
     * 查询注册收件列表
     * 
     * @param regApply 注册收件
     * @return 注册收件
     */
    @Override
    public List<RegApply> selectRegApplyList(RegApply regApply)
    {
        return regApplyMapper.selectRegApplyList(regApply);
    }

    /**
     * 新增注册收件
     * 
     * @param regApply 注册收件
     * @return 结果
     */
    @Override
    public int insertRegApply(RegApply regApply)
    {
        regApply.setCreateTime(DateUtils.getNowDate());
        return regApplyMapper.insertRegApply(regApply);
    }

    /**
     * 修改注册收件
     * 
     * @param regApply 注册收件
     * @return 结果
     */
    @Override
    public int updateRegApply(RegApply regApply)
    {
        regApply.setUpdateTime(DateUtils.getNowDate());
        return regApplyMapper.updateRegApply(regApply);
    }

    /**
     * 批量删除注册收件
     * 
     * @param regApplyIds 需要删除的注册收件主键
     * @return 结果
     */
    @Override
    public int deleteRegApplyByRegApplyIds(Long[] regApplyIds)
    {
        return regApplyMapper.deleteRegApplyByRegApplyIds(regApplyIds);
    }

    /**
     * 删除注册收件信息
     * 
     * @param regApplyId 注册收件主键
     * @return 结果
     */
    @Override
    public int deleteRegApplyByRegApplyId(Long regApplyId)
    {
        return regApplyMapper.deleteRegApplyByRegApplyId(regApplyId);
    }
}
