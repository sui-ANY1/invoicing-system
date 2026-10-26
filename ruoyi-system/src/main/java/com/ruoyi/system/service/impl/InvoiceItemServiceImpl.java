package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.InvoiceItemMapper;
import com.ruoyi.system.domain.InvoiceItem;
import com.ruoyi.system.service.IInvoiceItemService;

/**
 * 开票明细Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class InvoiceItemServiceImpl implements IInvoiceItemService 
{
    @Autowired
    private InvoiceItemMapper invoiceItemMapper;

    /**
     * 查询开票明细
     * 
     * @param itemId 开票明细主键
     * @return 开票明细
     */
    @Override
    public InvoiceItem selectInvoiceItemByItemId(Long itemId)
    {
        return invoiceItemMapper.selectInvoiceItemByItemId(itemId);
    }

    /**
     * 查询开票明细列表
     * 
     * @param invoiceItem 开票明细
     * @return 开票明细
     */
    @Override
    public List<InvoiceItem> selectInvoiceItemList(InvoiceItem invoiceItem)
    {
        return invoiceItemMapper.selectInvoiceItemList(invoiceItem);
    }

    /**
     * 新增开票明细
     * 
     * @param invoiceItem 开票明细
     * @return 结果
     */
    @Override
    public int insertInvoiceItem(InvoiceItem invoiceItem)
    {
        invoiceItem.setCreateTime(DateUtils.getNowDate());
        return invoiceItemMapper.insertInvoiceItem(invoiceItem);
    }

    /**
     * 修改开票明细
     * 
     * @param invoiceItem 开票明细
     * @return 结果
     */
    @Override
    public int updateInvoiceItem(InvoiceItem invoiceItem)
    {
        invoiceItem.setUpdateTime(DateUtils.getNowDate());
        return invoiceItemMapper.updateInvoiceItem(invoiceItem);
    }

    /**
     * 批量删除开票明细
     * 
     * @param itemIds 需要删除的开票明细主键
     * @return 结果
     */
    @Override
    public int deleteInvoiceItemByItemIds(Long[] itemIds)
    {
        return invoiceItemMapper.deleteInvoiceItemByItemIds(itemIds);
    }

    /**
     * 删除开票明细信息
     * 
     * @param itemId 开票明细主键
     * @return 结果
     */
    @Override
    public int deleteInvoiceItemByItemId(Long itemId)
    {
        return invoiceItemMapper.deleteInvoiceItemByItemId(itemId);
    }
}
