package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.RegSoleMapper;
import com.ruoyi.system.domain.RegSole;
import com.ruoyi.system.service.IRegSoleService;

/**
 * 个体户注册资料Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class RegSoleServiceImpl implements IRegSoleService 
{
    @Autowired
    private RegSoleMapper regSoleMapper;

    /**
     * 查询个体户注册资料
     * 
     * @param soleId 个体户注册资料主键
     * @return 个体户注册资料
     */
    @Override
    public RegSole selectRegSoleBySoleId(Long soleId)
    {
        return regSoleMapper.selectRegSoleBySoleId(soleId);
    }

    /**
     * 查询个体户注册资料列表
     * 
     * @param regSole 个体户注册资料
     * @return 个体户注册资料
     */
    @Override
    public List<RegSole> selectRegSoleList(RegSole regSole)
    {
        return regSoleMapper.selectRegSoleList(regSole);
    }

    /**
     * 新增个体户注册资料
     * 
     * @param regSole 个体户注册资料
     * @return 结果
     */
    @Override
    public int insertRegSole(RegSole regSole)
    {
        regSole.setCreateTime(DateUtils.getNowDate());
        return regSoleMapper.insertRegSole(regSole);
    }

    /**
     * 修改个体户注册资料
     * 
     * @param regSole 个体户注册资料
     * @return 结果
     */
    @Override
    public int updateRegSole(RegSole regSole)
    {
        regSole.setUpdateTime(DateUtils.getNowDate());
        return regSoleMapper.updateRegSole(regSole);
    }

    /**
     * 批量删除个体户注册资料
     * 
     * @param soleIds 需要删除的个体户注册资料主键
     * @return 结果
     */
    @Override
    public int deleteRegSoleBySoleIds(Long[] soleIds)
    {
        return regSoleMapper.deleteRegSoleBySoleIds(soleIds);
    }

    /**
     * 删除个体户注册资料信息
     * 
     * @param soleId 个体户注册资料主键
     * @return 结果
     */
    @Override
    public int deleteRegSoleBySoleId(Long soleId)
    {
        return regSoleMapper.deleteRegSoleBySoleId(soleId);
    }
}
