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
import com.ruoyi.system.domain.RpaTask;
import com.ruoyi.system.service.IRpaTaskService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * RPA 任务Controller
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/billing/task")
public class RpaTaskController extends BaseController
{
    @Autowired
    private IRpaTaskService rpaTaskService;

    /**
     * 查询RPA 任务列表
     */
    @PreAuthorize("@ss.hasPermi('billing:task:list')")
    @GetMapping("/list")
    public TableDataInfo list(RpaTask rpaTask)
    {
        startPage();
        List<RpaTask> list = rpaTaskService.selectRpaTaskList(rpaTask);
        return getDataTable(list);
    }

    /**
     * 导出RPA 任务列表
     */
    @PreAuthorize("@ss.hasPermi('billing:task:export')")
    @Log(title = "RPA 任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RpaTask rpaTask)
    {
        List<RpaTask> list = rpaTaskService.selectRpaTaskList(rpaTask);
        ExcelUtil<RpaTask> util = new ExcelUtil<RpaTask>(RpaTask.class);
        util.exportExcel(response, list, "RPA 任务数据");
    }

    /**
     * 获取RPA 任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('billing:task:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId)
    {
        return success(rpaTaskService.selectRpaTaskByTaskId(taskId));
    }

    /**
     * 新增RPA 任务
     */
    @PreAuthorize("@ss.hasPermi('billing:task:add')")
    @Log(title = "RPA 任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RpaTask rpaTask)
    {
        return toAjax(rpaTaskService.insertRpaTask(rpaTask));
    }

    /**
     * 修改RPA 任务
     */
    @PreAuthorize("@ss.hasPermi('billing:task:edit')")
    @Log(title = "RPA 任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RpaTask rpaTask)
    {
        return toAjax(rpaTaskService.updateRpaTask(rpaTask));
    }

    /**
     * 删除RPA 任务
     */
    @PreAuthorize("@ss.hasPermi('billing:task:remove')")
    @Log(title = "RPA 任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable Long[] taskIds)
    {
        return toAjax(rpaTaskService.deleteRpaTaskByTaskIds(taskIds));
    }
}
