package com.ruoyi.system.pc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.RegApply;
import com.ruoyi.system.pc.dto.PcRequests;
import com.ruoyi.system.pc.service.PcFileService;
import com.ruoyi.system.pc.service.PcRegService;
import com.ruoyi.system.pc.support.PcFileStreams;
import com.ruoyi.system.pc.support.PcPage;
import com.ruoyi.system.pc.support.PcPages;
import com.ruoyi.system.pc.support.PcResult;
import com.ruoyi.system.pc.vo.PcRegDetailVO;

/**
 * PC 注册信息只读（D11 列表 / D12 详情与受权附件预览）
 *
 * AC11/AC12：本模块只读，不提供任何写、审核、补件、导出、开票或系统管理能力。
 */
@Tag(name = "注册信息", description = "D11 注册收件列表、D12 注册详情与附件预览（只读，GET + query / path 参数）")
@RestController
@RequestMapping("/pc/registrations")
public class PcRegController extends BaseController {

    @Autowired
    private PcRegService pcRegService;

    @Autowired
    private PcFileService pcFileService;

    /**
     * D11 注册收件分页列表（按名称/编号、主体类型、提交时间筛选）
     */
    @Operation(summary = "查询注册列表",
            description = "GET /pc/registrations：D11 注册收件分页列表，query 参数支持收件编号（regApplyNo）/ 公司名称（拟定名称，companyName）/ 主体类型 / "
                    + "时间范围（startTime、endTime）筛选，分页参数 pageNum / pageSize 走 query")
    @PreAuthorize("@ss.hasPermi('pc:reg:list')")
    @GetMapping
    public PcResult<PcPage<RegApply>> list(@ModelAttribute PcRequests.RegQuery query) {
        PcPages.start(query, "create_time desc");
        RegApply condition = new RegApply();
        condition.setSubjectType(query.getSubjectType());
        condition.setRegApplyNo(query.getRegApplyNo());
        if (query.getCompanyName() != null && !query.getCompanyName().trim().isEmpty()) {
            condition.getParams().put("companyName", query.getCompanyName().trim());
        }
        if (query.getStartTime() != null && !query.getStartTime().trim().isEmpty()) {
            condition.getParams().put("beginTime", query.getStartTime().trim());
        }
        if (query.getEndTime() != null && !query.getEndTime().trim().isEmpty()) {
            condition.getParams().put("endTime", query.getEndTime().trim());
        }
        List<RegApply> list = pcRegService.list(condition);
        return PcResult.ok(PcPage.of(list));
    }

    /**
     * D12 注册详情（公司/个体完整只读 + 股东 + 已提交材料清单）
     */
    @Operation(summary = "查询注册详情",
            description = "GET /pc/registrations/detail：query 参数 submissionId；D12 注册详情，公司 / 个体工商户完整只读信息 + 股东 + "
                    + "已提交材料清单（readOnly 固定为 true）")
    @PreAuthorize("@ss.hasPermi('pc:reg:query')")
    @GetMapping("/detail")
    public PcResult<PcRegDetailVO> detail(@RequestParam("submissionId") Long submissionId) {
        return PcResult.ok(pcRegService.detail(submissionId));
    }

    /**
     * D12 受权附件预览（按 attachment_id 输出流，不暴露平台路径）
     */
    @Operation(summary = "预览注册附件",
            description = "GET /pc/registrations/attachments/file：query 参数 submissionId + attachmentId，D12 注册附件预览，"
                    + "输出文件流（inline 预览），不暴露平台路径")
    @ApiResponse(responseCode = "200", description = "文件流",
            content = @Content(mediaType = "application/octet-stream", schema = @Schema(type = "string", format = "binary")))
    @PreAuthorize("@ss.hasPermi('pc:reg:preview')")
    @GetMapping("/attachments/file")
    public void attachmentFile(@RequestParam("submissionId") Long submissionId,
            @RequestParam("attachmentId") Long attachmentId,
            HttpServletResponse response) {
        // submissionId 参与校验：附件必须属于该注册收件，防止改 attachmentId 越权预览
        PcFileStreams.write(pcFileService.attachmentFile(attachmentId, submissionId), response);
    }
}
