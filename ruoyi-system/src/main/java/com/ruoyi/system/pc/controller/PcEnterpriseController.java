package com.ruoyi.system.pc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.Enterprise;
import com.ruoyi.system.pc.dto.PcEnterpriseAuditDTO;
import com.ruoyi.system.pc.dto.PcEnterpriseDetailVO;
import com.ruoyi.system.pc.dto.PcRequests;
import com.ruoyi.system.pc.service.PcEnterpriseService;
import com.ruoyi.system.pc.service.PcInvoiceQueryService;
import com.ruoyi.system.pc.support.PcPage;
import com.ruoyi.system.pc.support.PcPages;
import com.ruoyi.system.pc.support.PcResult;
import com.ruoyi.system.pc.vo.PcInvoiceOverviewVO;

/**
 * PC 企业（D02 列表与认证审核）
 *
 * 统一约定：查询用 GET + query / path 参数，审核用 POST 子资源 /audits，不再使用 POST + JSON body 传参。
 */
@Tag(name = "企业与开票", description = "D02 企业列表与认证审核（GET 查询 + POST 审核）")
@RestController
@RequestMapping("/pc/enterprises")
public class PcEnterpriseController extends BaseController {

    @Autowired
    private PcEnterpriseService pcEnterpriseService;

    @Autowired
    private PcInvoiceQueryService pcInvoiceQueryService;

    /**
     * D02 企业分页列表
     */
    @Operation(summary = "查询企业列表",
            description = "GET /pc/enterprises：D02 企业主体分页列表，query 参数支持主体名称 / 税号 / 主体类型 / 认证状态筛选，"
                    + "分页参数 pageNum / pageSize 走 query，按创建时间倒序")
    @PreAuthorize("@ss.hasPermi('pc:enterprise:list')")
    @GetMapping
    public PcResult<PcPage<Enterprise>> list(@ModelAttribute PcRequests.EnterpriseQuery query) {
        PcPages.start(query, "create_time desc");
        Enterprise condition = new Enterprise();
        condition.setEnterpriseName(query.getEnterpriseName());
        condition.setTaxNumber(query.getTaxNumber());
        condition.setEnterpriseType(query.getEnterpriseType());
        condition.setAuthStatus(query.getAuthStatus());
        List<Enterprise> list = pcEnterpriseService.list(condition);
        return PcResult.ok(PcPage.of(list));
    }

    /**
     * D02-V1 企业聚合详情（不返回税务密码，账号脱敏）
     */
    @Operation(summary = "查询企业详情",
            description = "GET /pc/enterprises/detail：query 参数 enterpriseId；D02-V1 企业聚合详情，含主体信息 + 联系人 + 税务凭据"
                    + "（账号脱敏、不返回密码） + 附件清单")
    @PreAuthorize("@ss.hasPermi('pc:enterprise:query')")
    @GetMapping("/detail")
    public PcResult<PcEnterpriseDetailVO> detail(@RequestParam("enterpriseId") Long enterpriseId) {
        PcEnterpriseDetailVO vo = pcEnterpriseService.detail(enterpriseId);
        return PcResult.ok(vo);
    }

    /**
     * D03 企业开票概览（蓝红分开统计 + 趋势 + 票种分布）
     */
    @Operation(summary = "查询企业开票概览",
            description = "GET /pc/enterprises/invoice-summary：D03 图表用，query 参数 enterpriseId + 可选 startDate/endDate/granularity；"
                    + "返回蓝字/红字发票张数、票面价税合计、按月（或按日）趋势、普票/专票分布；只统计真实发票，平台不重算税额")
    @PreAuthorize("@ss.hasPermi('pc:enterprise:query')")
    @GetMapping("/invoice-summary")
    public PcResult<PcInvoiceOverviewVO> invoiceSummary(@RequestParam("enterpriseId") Long enterpriseId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "granularity", required = false) String granularity) {
        PcRequests.EnterpriseOverviewQuery query = new PcRequests.EnterpriseOverviewQuery();
        query.setEnterpriseId(enterpriseId);
        query.setStartDate(startDate);
        query.setEndDate(endDate);
        query.setGranularity(granularity);
        return PcResult.ok(pcInvoiceQueryService.overview(query));
    }

    /**
     * D02-V1 认证审核（1通过 2要求补充 3不通过）
     */
    @Operation(summary = "企业认证审核",
            description = "POST /pc/enterprises/audits：D02-V1 主体认证审核，body 传 enterpriseId + "
                    + "auditResult 1通过 / 2要求补充 / 3不通过与审核意见，写审核留痕并触发站内消息")
    @PreAuthorize("@ss.hasPermi('pc:enterprise:audit')")
    @Log(title = "企业认证审核", businessType = BusinessType.UPDATE)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/audits")
    public PcResult<Void> audit(@RequestBody PcEnterpriseAuditDTO dto) {
        if (dto.getEnterpriseId() == null) {
            throw new ServiceException("参数缺失：enterpriseId");
        }
        pcEnterpriseService.audit(dto.getEnterpriseId(), dto, getUsername());
        return PcResult.okMsg("审核完成");
    }
}
