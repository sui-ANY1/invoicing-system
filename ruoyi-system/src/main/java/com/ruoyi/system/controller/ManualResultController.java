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
import com.ruoyi.system.domain.ManualResult;
import com.ruoyi.system.service.IManualResultService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 人工办理结果Controller
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/audit/result")
public class ManualResultController extends BaseController
{
    @Autowired
    private IManualResultService manualResultService;

    /**
     * 查询人工办理结果列表
     */
    @PreAuthorize("@ss.hasPermi('audit:result:list')")
    @GetMapping("/list")
    public TableDataInfo list(ManualResult manualResult)
    {
        startPage();
        List<ManualResult> list = manualResultService.selectManualResultList(manualResult);
        return getDataTable(list);
    }

    /**
     * 导出人工办理结果列表
     */
    @PreAuthorize("@ss.hasPermi('audit:result:export')")
    @Log(title = "人工办理结果", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ManualResult manualResult)
    {
        List<ManualResult> list = manualResultService.selectManualResultList(manualResult);
        ExcelUtil<ManualResult> util = new ExcelUtil<ManualResult>(ManualResult.class);
        util.exportExcel(response, list, "人工办理结果数据");
    }

    /**
     * 获取人工办理结果详细信息
     */
    @PreAuthorize("@ss.hasPermi('audit:result:query')")
    @GetMapping(value = "/{resultId}")
    public AjaxResult getInfo(@PathVariable("resultId") Long resultId)
    {
        return success(manualResultService.selectManualResultByResultId(resultId));
    }

    /**
     * 新增人工办理结果
     */
    @PreAuthorize("@ss.hasPermi('audit:result:add')")
    @Log(title = "人工办理结果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ManualResult manualResult)
    {
        return toAjax(manualResultService.insertManualResult(manualResult));
    }

    /**
     * 修改人工办理结果
     */
    @PreAuthorize("@ss.hasPermi('audit:result:edit')")
    @Log(title = "人工办理结果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ManualResult manualResult)
    {
        return toAjax(manualResultService.updateManualResult(manualResult));
    }

    /**
     * 删除人工办理结果
     */
    @PreAuthorize("@ss.hasPermi('audit:result:remove')")
    @Log(title = "人工办理结果", businessType = BusinessType.DELETE)
	@DeleteMapping("/{resultIds}")
    public AjaxResult remove(@PathVariable Long[] resultIds)
    {
        return toAjax(manualResultService.deleteManualResultByResultIds(resultIds));
    }
}
