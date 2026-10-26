package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.RegSole;

/**
 * 个体户注册资料Service接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface IRegSoleService 
{
    /**
     * 查询个体户注册资料
     * 
     * @param soleId 个体户注册资料主键
     * @return 个体户注册资料
     */
    public RegSole selectRegSoleBySoleId(Long soleId);

    /**
     * 查询个体户注册资料列表
     * 
     * @param regSole 个体户注册资料
     * @return 个体户注册资料集合
     */
    public List<RegSole> selectRegSoleList(RegSole regSole);

    /**
     * 新增个体户注册资料
     * 
     * @param regSole 个体户注册资料
     * @return 结果
     */
    public int insertRegSole(RegSole regSole);

    /**
     * 修改个体户注册资料
     * 
     * @param regSole 个体户注册资料
     * @return 结果
     */
    public int updateRegSole(RegSole regSole);

    /**
     * 批量删除个体户注册资料
     * 
     * @param soleIds 需要删除的个体户注册资料主键集合
     * @return 结果
     */
    public int deleteRegSoleBySoleIds(Long[] soleIds);

    /**
     * 删除个体户注册资料信息
     * 
     * @param soleId 个体户注册资料主键
     * @return 结果
     */
    public int deleteRegSoleBySoleId(Long soleId);
}
