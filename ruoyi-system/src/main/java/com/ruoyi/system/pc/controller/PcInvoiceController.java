package com.ruoyi.system.pc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.pc.dto.PcRequests;
import com.ruoyi.system.pc.service.PcInvoiceQueryService;
import com.ruoyi.system.pc.support.PcPage;
import com.ruoyi.system.pc.support.PcPages;
import com.ruoyi.system.pc.support.PcResult;
import com.ruoyi.system.pc.vo.PcInvoiceTicketVO;

/**
 * PC 企业开票详情（D03）下的实际发票列表
 *
 * 口径：一张真实票一行，只列同一企业的真实发票；金额与税额以票面回传值为准（AC20/AC29），平台不重算；
 * 文件下载走受权下载接口，不在本控制器暴露平台路径。
 */
@Tag(name = "企业与开票", description = "D03 企业开票概览与实际发票列表（GET 查询）")
@RestController
@RequestMapping("/pc/enterprises")
public class PcInvoiceController extends BaseController {

    @Autowired
    private PcInvoiceQueryService pcInvoiceQueryService;

    /**
     * D03 实际发票列表：同企业一票一行，可按蓝红、文件状态、实际开票日期范围筛选
     */
    @Operation(summary = "查询企业发票列表",
            description = "GET /pc/enterprises/invoices：D03 企业开票概览下方的实际发票列表，一张真实票一行；"
                    + "query 必传 enterpriseId，另支持 invoiceKind（1蓝字 2红字）、fileStatus（0待文件 1已获取 2获取失败）、"
                    + "startDate / endDate（实际开票日期），分页参数 pageNum / pageSize")
    @PreAuthorize("@ss.hasPermi('pc:record:list')")
    @GetMapping("/invoices")
    public PcResult<PcPage<PcInvoiceTicketVO>> list(@RequestParam("enterpriseId") Long enterpriseId,
                                                    @ModelAttribute PcRequests.InvoiceTicketQuery query) {
        query.setEnterpriseId(enterpriseId);
        PcPages.start(query, "invoice_date desc, invoice_id desc");
        List<PcInvoiceTicketVO> list = pcInvoiceQueryService.tickets(query);
        return PcResult.ok(PcPage.of(list));
    }
}
