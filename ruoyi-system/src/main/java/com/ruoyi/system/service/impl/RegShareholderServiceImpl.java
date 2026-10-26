package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.RegShareholderMapper;
import com.ruoyi.system.domain.RegShareholder;
import com.ruoyi.system.service.IRegShareholderService;

/**
 * 股东信息Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class RegShareholderServiceImpl implements IRegShareholderService 
{
    @Autowired
    private RegShareholderMapper regShareholderMapper;

    /**
     * 查询股东信息
     * 
     * @param shareholderId 股东信息主键
     * @return 股东信息
     */
    @Override
    public RegShareholder selectRegShareholderByShareholderId(Long shareholderId)
    {
        return regShareholderMapper.selectRegShareholderByShareholderId(shareholderId);
    }

    /**
     * 查询股东信息列表
     * 
     * @param regShareholder 股东信息
     * @return 股东信息
     */
    @Override
    public List<RegShareholder> selectRegShareholderList(RegShareholder regShareholder)
    {
        return regShareholderMapper.selectRegShareholderList(regShareholder);
    }

    /**
     * 新增股东信息
     * 
     * @param regShareholder 股东信息
     * @return 结果
     */
    @Override
    public int insertRegShareholder(RegShareholder regShareholder)
    {
        regShareholder.setCreateTime(DateUtils.getNowDate());
        return regShareholderMapper.insertRegShareholder(regShareholder);
    }

    /**
     * 修改股东信息
     * 
     * @param regShareholder 股东信息
     * @return 结果
     */
    @Override
    public int updateRegShareholder(RegShareholder regShareholder)
    {
        regShareholder.setUpdateTime(DateUtils.getNowDate());
        return regShareholderMapper.updateRegShareholder(regShareholder);
    }

    /**
     * 批量删除股东信息
     * 
     * @param shareholderIds 需要删除的股东信息主键
     * @return 结果
     */
    @Override
    public int deleteRegShareholderByShareholderIds(Long[] shareholderIds)
    {
        return regShareholderMapper.deleteRegShareholderByShareholderIds(shareholderIds);
    }

    /**
     * 删除股东信息信息
     * 
     * @param shareholderId 股东信息主键
     * @return 结果
     */
    @Override
    public int deleteRegShareholderByShareholderId(Long shareholderId)
    {
        return regShareholderMapper.deleteRegShareholderByShareholderId(shareholderId);
    }
}
