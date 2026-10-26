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
import com.ruoyi.system.domain.RegApply;
import com.ruoyi.system.service.IRegApplyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 注册收件Controller
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/reg/apply")
public class RegApplyController extends BaseController
{
    @Autowired
    private IRegApplyService regApplyService;

    /**
     * 查询注册收件列表
     */
    @PreAuthorize("@ss.hasPermi('reg:apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(RegApply regApply)
    {
        startPage();
        List<RegApply> list = regApplyService.selectRegApplyList(regApply);
        return getDataTable(list);
    }

    /**
     * 导出注册收件列表
     */
    @PreAuthorize("@ss.hasPermi('reg:apply:export')")
    @Log(title = "注册收件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RegApply regApply)
    {
        List<RegApply> list = regApplyService.selectRegApplyList(regApply);
        ExcelUtil<RegApply> util = new ExcelUtil<RegApply>(RegApply.class);
        util.exportExcel(response, list, "注册收件数据");
    }

    /**
     * 获取注册收件详细信息
     */
    @PreAuthorize("@ss.hasPermi('reg:apply:query')")
    @GetMapping(value = "/{regApplyId}")
    public AjaxResult getInfo(@PathVariable("regApplyId") Long regApplyId)
    {
        return success(regApplyService.selectRegApplyByRegApplyId(regApplyId));
    }

    /**
     * 新增注册收件
     */
    @PreAuthorize("@ss.hasPermi('reg:apply:add')")
    @Log(title = "注册收件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RegApply regApply)
    {
        return toAjax(regApplyService.insertRegApply(regApply));
    }

    /**
     * 修改注册收件
     */
    @PreAuthorize("@ss.hasPermi('reg:apply:edit')")
    @Log(title = "注册收件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RegApply regApply)
    {
        return toAjax(regApplyService.updateRegApply(regApply));
    }

    /**
     * 删除注册收件
     */
    @PreAuthorize("@ss.hasPermi('reg:apply:remove')")
    @Log(title = "注册收件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{regApplyIds}")
    public AjaxResult remove(@PathVariable Long[] regApplyIds)
    {
        return toAjax(regApplyService.deleteRegApplyByRegApplyIds(regApplyIds));
    }
}
