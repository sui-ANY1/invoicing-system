package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TaxCredential;

/**
 * 税务凭据Service接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface ITaxCredentialService 
{
    /**
     * 查询税务凭据
     * 
     * @param credentialId 税务凭据主键
     * @return 税务凭据
     */
    public TaxCredential selectTaxCredentialByCredentialId(Long credentialId);

    /**
     * 查询税务凭据列表
     * 
     * @param taxCredential 税务凭据
     * @return 税务凭据集合
     */
    public List<TaxCredential> selectTaxCredentialList(TaxCredential taxCredential);

    /**
     * 新增税务凭据
     * 
     * @param taxCredential 税务凭据
     * @return 结果
     */
    public int insertTaxCredential(TaxCredential taxCredential);

    /**
     * 修改税务凭据
     * 
     * @param taxCredential 税务凭据
     * @return 结果
     */
    public int updateTaxCredential(TaxCredential taxCredential);

    /**
     * 批量删除税务凭据
     * 
     * @param credentialIds 需要删除的税务凭据主键集合
     * @return 结果
     */
    public int deleteTaxCredentialByCredentialIds(Long[] credentialIds);

    /**
     * 删除税务凭据信息
     * 
     * @param credentialId 税务凭据主键
     * @return 结果
     */
    public int deleteTaxCredentialByCredentialId(Long credentialId);
}
