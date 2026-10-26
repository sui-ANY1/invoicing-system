package com.ruoyi.system.mini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.ruoyi.system.mini.dto.request.RedApplyRequest;
import com.ruoyi.system.mini.dto.vo.IdResultVO;
import com.ruoyi.system.mini.dto.vo.PageResult;
import com.ruoyi.system.mini.dto.vo.RedApplyDetailVO;
import com.ruoyi.system.mini.dto.vo.RedApplyListItemVO;
import com.ruoyi.system.mini.security.MiniSecurityUtils;
import com.ruoyi.system.mini.service.MiniRedService;
import com.ruoyi.system.mini.support.MiniPageUtils;
import jakarta.validation.Valid;

/**
 * 小程序红字办理
 *
 * 统一约定：RESTful 资源路径；详情/刷新用 GET + path 变量，提交用 POST + JSON body。
 */
@Tag(name = "红字办理", description = "红字申请提交、详情、状态刷新（RESTful：GET 查询、POST 提交）")
@RestController
@RequestMapping("/api/mini/red-applications")
public class MiniRedController {

    @Autowired
    private MiniRedService redService;

    @Operation(summary = "提交红字申请",
            description = "对本人已交付的蓝字发票发起红冲；全部红冲可不传明细，部分红冲必须传明细；成功返回 201 与新申请ID")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public R<IdResultVO> apply(@Valid @RequestBody RedApplyRequest request) {
        Long memberId = MiniSecurityUtils.getMemberId();
        Long id = redService.apply(memberId, request);
        return R.ok(new IdResultVO(id));
    }

    @Operation(summary = "查询红字记录列表",
            description = "本人全部红字申请；分页参数 pageNum/pageSize（默认 1/10，上限 200）；enterpriseName 按公司名称模糊搜索，status 按红字状态筛选，keyword 按红字单号/原票号码/企业名称模糊搜索；返回 {total,rows}")
    @GetMapping
    public R<PageResult<RedApplyListItemVO>> list(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "enterpriseName", required = false) String enterpriseName,
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Long memberId = MiniSecurityUtils.getMemberId();
        List<RedApplyListItemVO> list = redService.list(memberId);
        String statusFilter = (status == null || status.isEmpty()) ? null : status;
        String kw = (keyword == null || keyword.trim().isEmpty()) ? null : keyword.trim().toLowerCase();
        String nameKw = (enterpriseName == null || enterpriseName.trim().isEmpty()) ? null
                : enterpriseName.trim().toLowerCase();
        return R.ok(MiniPageUtils.of(list, pageNum, pageSize, vo ->
                (statusFilter == null || statusFilter.equals(vo.getRedStatus()))
                        && (nameKw == null || MiniPageUtils.containsIgnoreCase(vo.getEnterpriseName(), nameKw))
                        && (kw == null
                        || MiniPageUtils.containsIgnoreCase(vo.getRedApplyNo(), kw)
                        || MiniPageUtils.containsIgnoreCase(vo.getOriginalInvoiceNo(), kw)
                        || MiniPageUtils.containsIgnoreCase(vo.getEnterpriseName(), kw))));
    }

    @Operation(summary = "查询红字申请详情",
            description = "query 参数 redApplyId；refresh=true 时重新拉取红字状态，否则直接查库；返回红字主单 + 明细")
    @GetMapping("/detail")
    public R<RedApplyDetailVO> detail(@RequestParam("redApplyId") Long redApplyId,
                                      @RequestParam(value = "refresh", defaultValue = "false") boolean refresh) {
        Long memberId = MiniSecurityUtils.getMemberId();
        return R.ok(refresh
                ? redService.refresh(memberId, redApplyId)
                : redService.detail(memberId, redApplyId));
    }
}
