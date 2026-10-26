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
import com.ruoyi.system.domain.AuditRecord;
import com.ruoyi.system.service.IAuditRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 认证审核记录Controller
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/audit/record")
public class AuditRecordController extends BaseController
{
    @Autowired
    private IAuditRecordService auditRecordService;

    /**
     * 查询认证审核记录列表
     */
    @PreAuthorize("@ss.hasPermi('audit:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(AuditRecord auditRecord)
    {
        startPage();
        List<AuditRecord> list = auditRecordService.selectAuditRecordList(auditRecord);
        return getDataTable(list);
    }

    /**
     * 导出认证审核记录列表
     */
    @PreAuthorize("@ss.hasPermi('audit:record:export')")
    @Log(title = "认证审核记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AuditRecord auditRecord)
    {
        List<AuditRecord> list = auditRecordService.selectAuditRecordList(auditRecord);
        ExcelUtil<AuditRecord> util = new ExcelUtil<AuditRecord>(AuditRecord.class);
        util.exportExcel(response, list, "认证审核记录数据");
    }

    /**
     * 获取认证审核记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('audit:record:query')")
    @GetMapping(value = "/{auditRecordId}")
    public AjaxResult getInfo(@PathVariable("auditRecordId") Long auditRecordId)
    {
        return success(auditRecordService.selectAuditRecordByAuditRecordId(auditRecordId));
    }

    /**
     * 新增认证审核记录
     */
    @PreAuthorize("@ss.hasPermi('audit:record:add')")
    @Log(title = "认证审核记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AuditRecord auditRecord)
    {
        return toAjax(auditRecordService.insertAuditRecord(auditRecord));
    }

    /**
     * 修改认证审核记录
     */
    @PreAuthorize("@ss.hasPermi('audit:record:edit')")
    @Log(title = "认证审核记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AuditRecord auditRecord)
    {
        return toAjax(auditRecordService.updateAuditRecord(auditRecord));
    }

    /**
     * 删除认证审核记录
     */
    @PreAuthorize("@ss.hasPermi('audit:record:remove')")
    @Log(title = "认证审核记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{auditRecordIds}")
    public AjaxResult remove(@PathVariable Long[] auditRecordIds)
    {
        return toAjax(auditRecordService.deleteAuditRecordByAuditRecordIds(auditRecordIds));
    }
}
