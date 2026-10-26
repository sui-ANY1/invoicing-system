package com.ruoyi.system.pc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.pc.dto.PcRequests;
import com.ruoyi.system.pc.service.PcWorkbenchService;
import com.ruoyi.system.pc.support.PcPage;
import com.ruoyi.system.pc.support.PcPages;
import com.ruoyi.system.pc.support.PcResult;
import com.ruoyi.system.pc.vo.PcTodoCountVO;
import com.ruoyi.system.pc.vo.PcTodoItemVO;

/**
 * PC 开票工作台（D01）：五类待办计数 + 待办分页列表
 */
@Tag(name = "开票工作台", description = "D01 待办统计与待办列表（GET + query 参数）")
@RestController
@RequestMapping("/pc/workbench")
public class PcWorkbenchController extends BaseController {

    @Autowired
    private PcWorkbenchService pcWorkbenchService;

    /**
     * 五类待办计数：待审核 / 需补资料 / 待用户操作 / 结果未知 / 文件异常
     */
    @Operation(summary = "待办统计",
            description = "GET /pc/workbench/todos/count：五类待办数量（待审核 / 需补资料 / 待用户操作 / 结果未知 / 文件异常），"
                    + "无入参，口径与待办列表一致")
    @PreAuthorize("@ss.hasPermi('pc:workbench:list')")
    @GetMapping("/todos/count")
    public PcResult<PcTodoCountVO> todoCount() {
        return PcResult.ok(pcWorkbenchService.counts());
    }

    /**
     * 待办分页列表（可按类型 / 状态 / 关键字 / 时间筛选）
     */
    @Operation(summary = "待办分页列表",
            description = "GET /pc/workbench/todos：待办分页列表，query 参数支持待办类型（type）与状态、关键字、时间范围筛选，"
                    + "分页参数 pageNum / pageSize 一并走 query")
    @PreAuthorize("@ss.hasPermi('pc:workbench:list')")
    @GetMapping("/todos")
    public PcResult<PcPage<PcTodoItemVO>> todoList(@ModelAttribute PcRequests.TodoQuery query) {
        PcPages.start(query);
        List<PcTodoItemVO> list = pcWorkbenchService.list(query);
        return PcResult.ok(PcPage.of(list));
    }
}
