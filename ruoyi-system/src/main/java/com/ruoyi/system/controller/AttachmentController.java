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
import com.ruoyi.system.domain.Attachment;
import com.ruoyi.system.service.IAttachmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 统一附件Controller
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/common/attachment")
public class AttachmentController extends BaseController
{
    @Autowired
    private IAttachmentService attachmentService;

    /**
     * 查询统一附件列表
     */
    @PreAuthorize("@ss.hasPermi('common:attachment:list')")
    @GetMapping("/list")
    public TableDataInfo list(Attachment attachment)
    {
        startPage();
        List<Attachment> list = attachmentService.selectAttachmentList(attachment);
        return getDataTable(list);
    }

    /**
     * 导出统一附件列表
     */
    @PreAuthorize("@ss.hasPermi('common:attachment:export')")
    @Log(title = "统一附件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Attachment attachment)
    {
        List<Attachment> list = attachmentService.selectAttachmentList(attachment);
        ExcelUtil<Attachment> util = new ExcelUtil<Attachment>(Attachment.class);
        util.exportExcel(response, list, "统一附件数据");
    }

    /**
     * 获取统一附件详细信息
     */
    @PreAuthorize("@ss.hasPermi('common:attachment:query')")
    @GetMapping(value = "/{attachmentId}")
    public AjaxResult getInfo(@PathVariable("attachmentId") Long attachmentId)
    {
        return success(attachmentService.selectAttachmentByAttachmentId(attachmentId));
    }

    /**
     * 新增统一附件
     */
    @PreAuthorize("@ss.hasPermi('common:attachment:add')")
    @Log(title = "统一附件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Attachment attachment)
    {
        return toAjax(attachmentService.insertAttachment(attachment));
    }

    /**
     * 修改统一附件
     */
    @PreAuthorize("@ss.hasPermi('common:attachment:edit')")
    @Log(title = "统一附件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Attachment attachment)
    {
        return toAjax(attachmentService.updateAttachment(attachment));
    }

    /**
     * 删除统一附件
     */
    @PreAuthorize("@ss.hasPermi('common:attachment:remove')")
    @Log(title = "统一附件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{attachmentIds}")
    public AjaxResult remove(@PathVariable Long[] attachmentIds)
    {
        return toAjax(attachmentService.deleteAttachmentByAttachmentIds(attachmentIds));
    }
}
