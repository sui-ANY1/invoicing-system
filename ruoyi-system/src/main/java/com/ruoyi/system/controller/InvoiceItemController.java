package com.ruoyi.system.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.InvoiceItem;
import com.ruoyi.system.service.IInvoiceItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 开票明细Controller
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/billing/item")
public class InvoiceItemController extends BaseController
{
    @Autowired
    private IInvoiceItemService invoiceItemService;

    /**
     * 查询开票明细列表
     */
    @PreAuthorize("@ss.hasPermi('billing:item:list')")
    @GetMapping("/list")
    public TableDataInfo list(InvoiceItem invoiceItem)
    {
        startPage();
        List<InvoiceItem> list = invoiceItemService.selectInvoiceItemList(invoiceItem);
        return getDataTable(list);
    }

    /**
     * 导出开票明细列表
     */
    @PreAuthorize("@ss.hasPermi('billing:item:export')")
    @Log(title = "开票明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InvoiceItem invoiceItem)
    {
        List<InvoiceItem> list = invoiceItemService.selectInvoiceItemList(invoiceItem);
        ExcelUtil<InvoiceItem> util = new ExcelUtil<InvoiceItem>(InvoiceItem.class);
        util.exportExcel(response, list, "开票明细数据");
    }

    /**
     * 获取开票明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('billing:item:query')")
    @GetMapping(value = "/{itemId}")
    public AjaxResult getInfo(@PathVariable("itemId") Long itemId)
    {
        return success(invoiceItemService.selectInvoiceItemByItemId(itemId));
    }

    /**
     * 新增开票明细
     */
    @PreAuthorize("@ss.hasPermi('billing:item:add')")
    @Log(title = "开票明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InvoiceItem invoiceItem)
    {
        return toAjax(invoiceItemService.insertInvoiceItem(invoiceItem));
    }

    /**
     * 修改开票明细
     */
    @PreAuthorize("@ss.hasPermi('billing:item:edit')")
    @Log(title = "开票明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InvoiceItem invoiceItem)
    {
        return toAjax(invoiceItemService.updateInvoiceItem(invoiceItem));
    }

    /**
     * 删除开票明细
     */
    @PreAuthorize("@ss.hasPermi('billing:item:remove')")
    @Log(title = "开票明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{itemIds}")
    public AjaxResult remove(@PathVariable Long[] itemIds)
    {
        return toAjax(invoiceItemService.deleteInvoiceItemByItemIds(itemIds));
    }
}
