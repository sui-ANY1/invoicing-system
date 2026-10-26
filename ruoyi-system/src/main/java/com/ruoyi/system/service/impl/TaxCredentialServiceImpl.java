package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TaxCredentialMapper;
import com.ruoyi.system.domain.TaxCredential;
import com.ruoyi.system.service.ITaxCredentialService;

/**
 * 税务凭据Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class TaxCredentialServiceImpl implements ITaxCredentialService 
{
    @Autowired
    private TaxCredentialMapper taxCredentialMapper;

    /**
     * 查询税务凭据
     * 
     * @param credentialId 税务凭据主键
     * @return 税务凭据
     */
    @Override
    public TaxCredential selectTaxCredentialByCredentialId(Long credentialId)
    {
        return taxCredentialMapper.selectTaxCredentialByCredentialId(credentialId);
    }

    /**
     * 查询税务凭据列表
     * 
     * @param taxCredential 税务凭据
     * @return 税务凭据
     */
    @Override
    public List<TaxCredential> selectTaxCredentialList(TaxCredential taxCredential)
    {
        return taxCredentialMapper.selectTaxCredentialList(taxCredential);
    }

    /**
     * 新增税务凭据
     * 
     * @param taxCredential 税务凭据
     * @return 结果
     */
    @Override
    public int insertTaxCredential(TaxCredential taxCredential)
    {
        taxCredential.setCreateTime(DateUtils.getNowDate());
        return taxCredentialMapper.insertTaxCredential(taxCredential);
    }

    /**
     * 修改税务凭据
     * 
     * @param taxCredential 税务凭据
     * @return 结果
     */
    @Override
    public int updateTaxCredential(TaxCredential taxCredential)
    {
        taxCredential.setUpdateTime(DateUtils.getNowDate());
        return taxCredentialMapper.updateTaxCredential(taxCredential);
    }

    /**
     * 批量删除税务凭据
     * 
     * @param credentialIds 需要删除的税务凭据主键
     * @return 结果
     */
    @Override
    public int deleteTaxCredentialByCredentialIds(Long[] credentialIds)
    {
        return taxCredentialMapper.deleteTaxCredentialByCredentialIds(credentialIds);
    }

    /**
     * 删除税务凭据信息
     * 
     * @param credentialId 税务凭据主键
     * @return 结果
     */
    @Override
    public int deleteTaxCredentialByCredentialId(Long credentialId)
    {
        return taxCredentialMapper.deleteTaxCredentialByCredentialId(credentialId);
    }
}
