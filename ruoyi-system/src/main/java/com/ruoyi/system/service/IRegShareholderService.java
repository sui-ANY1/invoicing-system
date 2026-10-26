package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.RegShareholder;

/**
 * 股东信息Service接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface IRegShareholderService 
{
    /**
     * 查询股东信息
     * 
     * @param shareholderId 股东信息主键
     * @return 股东信息
     */
    public RegShareholder selectRegShareholderByShareholderId(Long shareholderId);

    /**
     * 查询股东信息列表
     * 
     * @param regShareholder 股东信息
     * @return 股东信息集合
     */
    public List<RegShareholder> selectRegShareholderList(RegShareholder regShareholder);

    /**
     * 新增股东信息
     * 
     * @param regShareholder 股东信息
     * @return 结果
     */
    public int insertRegShareholder(RegShareholder regShareholder);

    /**
     * 修改股东信息
     * 
     * @param regShareholder 股东信息
     * @return 结果
     */
    public int updateRegShareholder(RegShareholder regShareholder);

    /**
     * 批量删除股东信息
     * 
     * @param shareholderIds 需要删除的股东信息主键集合
     * @return 结果
     */
    public int deleteRegShareholderByShareholderIds(Long[] shareholderIds);

    /**
     * 删除股东信息信息
     * 
     * @param shareholderId 股东信息主键
     * @return 结果
     */
    public int deleteRegShareholderByShareholderId(Long shareholderId);
}
