package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.InvoiceApply;

/**
 * 开票申请Mapper接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface InvoiceApplyMapper 
{
    /**
     * 查询开票申请
     * 
     * @param applyId 开票申请主键
     * @return 开票申请
     */
    public InvoiceApply selectInvoiceApplyByApplyId(Long applyId);

    /**
     * 查询开票申请列表
     * 
     * @param invoiceApply 开票申请
     * @return 开票申请集合
     */
    public List<InvoiceApply> selectInvoiceApplyList(InvoiceApply invoiceApply);

    /**
     * 新增开票申请
     * 
     * @param invoiceApply 开票申请
     * @return 结果
     */
    public int insertInvoiceApply(InvoiceApply invoiceApply);

    /**
     * 修改开票申请
     * 
     * @param invoiceApply 开票申请
     * @return 结果
     */
    public int updateInvoiceApply(InvoiceApply invoiceApply);

    /**
     * 删除开票申请
     * 
     * @param applyId 开票申请主键
     * @return 结果
     */
    public int deleteInvoiceApplyByApplyId(Long applyId);

    /**
     * 批量删除开票申请
     * 
     * @param applyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInvoiceApplyByApplyIds(Long[] applyIds);
}
