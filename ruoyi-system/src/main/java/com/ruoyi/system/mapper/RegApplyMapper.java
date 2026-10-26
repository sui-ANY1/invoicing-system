package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.RegApply;

/**
 * 注册收件Mapper接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface RegApplyMapper 
{
    /**
     * 查询注册收件
     * 
     * @param regApplyId 注册收件主键
     * @return 注册收件
     */
    public RegApply selectRegApplyByRegApplyId(Long regApplyId);

    /**
     * 查询注册收件列表
     * 
     * @param regApply 注册收件
     * @return 注册收件集合
     */
    public List<RegApply> selectRegApplyList(RegApply regApply);

    /**
     * 新增注册收件
     * 
     * @param regApply 注册收件
     * @return 结果
     */
    public int insertRegApply(RegApply regApply);

    /**
     * 修改注册收件
     * 
     * @param regApply 注册收件
     * @return 结果
     */
    public int updateRegApply(RegApply regApply);

    /**
     * 删除注册收件
     * 
     * @param regApplyId 注册收件主键
     * @return 结果
     */
    public int deleteRegApplyByRegApplyId(Long regApplyId);

    /**
     * 批量删除注册收件
     * 
     * @param regApplyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRegApplyByRegApplyIds(Long[] regApplyIds);
}
