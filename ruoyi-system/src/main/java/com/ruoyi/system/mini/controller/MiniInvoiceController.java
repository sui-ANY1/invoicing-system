package com.ruoyi.system.mini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ruoyi.system.mini.dto.R;
import com.ruoyi.system.mini.dto.request.InvoiceApplyRequest;
import com.ruoyi.system.mini.dto.vo.IdResultVO;
import com.ruoyi.system.mini.dto.vo.InvoiceApplyDetailVO;
import com.ruoyi.system.mini.dto.vo.InvoiceApplyVO;
import com.ruoyi.system.mini.dto.vo.PageResult;
import com.ruoyi.system.mini.security.MiniSecurityUtils;
import com.ruoyi.system.mini.service.MiniInvoiceService;
import com.ruoyi.system.mini.support.MiniPageUtils;
import jakarta.validation.Valid;

/**
 * 小程序开票办理
 *
 * 统一约定：RESTful 资源路径；列表用 GET + query 参数，详情用 GET + path 变量，
 * path 中的 refresh=true 表示重新拉取开票状态。
 */
@Tag(name = "开票办理",
        description = "开票申请提交、开票中心、详情与状态刷新（RESTful：GET 查询、POST 创建）")
@RestController
@RequestMapping("/api/mini/invoice-applications")
public class MiniInvoiceController {

    @Autowired
    private MiniInvoiceService invoiceService;

    @Operation(summary = "提交开票申请",
            description = "提交主单 + 至少一条明细；提交后状态为已提交，返回申请ID")
    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public R<IdResultVO> apply(@Valid @RequestBody InvoiceApplyRequest request) {
        Long memberId = MiniSecurityUtils.getMemberId();
        Long id = invoiceService.apply(memberId, request);
        return R.ok(new IdResultVO(id));
    }

    @Operation(summary = "查询开票中心",
            description = "当前会员的开票申请列表；分页参数 pageNum/pageSize（默认 1/10，上限 200）；enterpriseName 按公司名称模糊搜索，status 按开票状态筛选，keyword 按企业名称/业务编号/发票号码/购买方模糊搜索；返回 {total,rows}")
    @GetMapping
    public R<PageResult<InvoiceApplyVO>> center(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "enterpriseName", required = false) String enterpriseName,
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Long memberId = MiniSecurityUtils.getMemberId();
        List<InvoiceApplyVO> list = invoiceService.center(memberId);
        String statusFilter = (status == null || status.isEmpty()) ? null : status;
        String kw = (keyword == null || keyword.trim().isEmpty()) ? null : keyword.trim().toLowerCase();
        String nameKw = (enterpriseName == null || enterpriseName.trim().isEmpty()) ? null
                : enterpriseName.trim().toLowerCase();
        return R.ok(MiniPageUtils.of(list, pageNum, pageSize, vo ->
                (statusFilter == null || statusFilter.equals(vo.getApplyStatus()))
                        && (nameKw == null || MiniPageUtils.containsIgnoreCase(vo.getEnterpriseName(), nameKw))
                        && (kw == null
                        || MiniPageUtils.containsIgnoreCase(vo.getEnterpriseName(), kw)
                        || MiniPageUtils.containsIgnoreCase(vo.getApplyNo(), kw)
                        || MiniPageUtils.containsIgnoreCase(vo.getInvoiceNo(), kw)
                        || MiniPageUtils.containsIgnoreCase(vo.getBuyerName(), kw))));
    }

    @Operation(summary = "查询开票申请详情",
            description = "query 参数 applyId；refresh=true 时重新拉取开票状态（阶段5对接RPA），否则直接查库；返回主单 + 明细列表 + 已回传发票列表")
    @GetMapping("/detail")
    public R<InvoiceApplyDetailVO> detail(@RequestParam("applyId") Long applyId,
                                          @RequestParam(value = "refresh", defaultValue = "false") boolean refresh) {
        Long memberId = MiniSecurityUtils.getMemberId();
        return R.ok(refresh
                ? invoiceService.refresh(memberId, applyId)
                : invoiceService.detail(memberId, applyId));
    }
}
