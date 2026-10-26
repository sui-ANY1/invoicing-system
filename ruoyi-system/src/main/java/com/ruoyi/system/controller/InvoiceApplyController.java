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
import com.ruoyi.system.domain.InvoiceApply;
import com.ruoyi.system.service.IInvoiceApplyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 开票申请Controller
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/billing/apply")
public class InvoiceApplyController extends BaseController
{
    @Autowired
    private IInvoiceApplyService invoiceApplyService;

    /**
     * 查询开票申请列表
     */
    @PreAuthorize("@ss.hasPermi('billing:apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(InvoiceApply invoiceApply)
    {
        startPage();
        List<InvoiceApply> list = invoiceApplyService.selectInvoiceApplyList(invoiceApply);
        return getDataTable(list);
    }

    /**
     * 导出开票申请列表
     */
    @PreAuthorize("@ss.hasPermi('billing:apply:export')")
    @Log(title = "开票申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InvoiceApply invoiceApply)
    {
        List<InvoiceApply> list = invoiceApplyService.selectInvoiceApplyList(invoiceApply);
        ExcelUtil<InvoiceApply> util = new ExcelUtil<InvoiceApply>(InvoiceApply.class);
        util.exportExcel(response, list, "开票申请数据");
    }

    /**
     * 获取开票申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('billing:apply:query')")
    @GetMapping(value = "/{applyId}")
    public AjaxResult getInfo(@PathVariable("applyId") Long applyId)
    {
        return success(invoiceApplyService.selectInvoiceApplyByApplyId(applyId));
    }

    /**
     * 新增开票申请
     */
    @PreAuthorize("@ss.hasPermi('billing:apply:add')")
    @Log(title = "开票申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InvoiceApply invoiceApply)
    {
        return toAjax(invoiceApplyService.insertInvoiceApply(invoiceApply));
    }

    /**
     * 修改开票申请
     */
    @PreAuthorize("@ss.hasPermi('billing:apply:edit')")
    @Log(title = "开票申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InvoiceApply invoiceApply)
    {
        return toAjax(invoiceApplyService.updateInvoiceApply(invoiceApply));
    }

    /**
     * 删除开票申请
     */
    @PreAuthorize("@ss.hasPermi('billing:apply:remove')")
    @Log(title = "开票申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{applyIds}")
    public AjaxResult remove(@PathVariable Long[] applyIds)
    {
        return toAjax(invoiceApplyService.deleteInvoiceApplyByApplyIds(applyIds));
    }
}
