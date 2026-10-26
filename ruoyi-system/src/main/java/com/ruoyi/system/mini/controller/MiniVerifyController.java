package com.ruoyi.system.mini.controller;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.system.mini.support.MiniRpaGate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ruoyi.system.mini.dto.R;
import com.ruoyi.system.mini.dto.request.TaxVerifyCreateRequest;
import com.ruoyi.system.mini.dto.request.VerifyCodeSubmitRequest;
import com.ruoyi.system.mini.dto.request.VerifyIdRequest;
import com.ruoyi.system.mini.dto.vo.TaxVerifyVO;
import com.ruoyi.system.mini.security.MiniSecurityUtils;
import com.ruoyi.system.mini.service.MiniVerifyService;

/**
 * 小程序税务验证
 *
 * 统一约定：RESTful 资源路径；发起/提交/重发验证码用 POST 动作（body 传 verifyId），查询状态用 GET + query 参数。
 */
@Tag(name = "税务验证", description = "税务验证发起、短信验证码提交/重发与结果查询（RESTful：GET 查询、POST 动作）")
@RestController
@RequestMapping("/api/mini/tax-verifications")
public class MiniVerifyController {

    @Autowired
    private MiniVerifyService verifyService;

    @Autowired
    private MiniRpaGate rpaGate;

    @Operation(summary = "发起税务验证",
            description = "body 传 enterpriseId（可选 applyId）；触发电子税局下发短信验证码；RPA 未接入时返回 501")
    @PostMapping
    public R<TaxVerifyVO> create(@RequestBody(required = false) TaxVerifyCreateRequest request) {
        if (!rpaGate.enabled()) {
            return R.fail(HttpStatus.NOT_IMPLEMENTED,
                    "税务自动验证尚未开通（RPA 未接入），暂不能发起验证；如需继续，请由后台人工回填验证结果");
        }
        // TODO 阶段5：RPA 接入后，校验 request.enterpriseId/applyId 归属并调用 RPA 发起税务验证，落 t_tax_verify
        return R.fail(HttpStatus.NOT_IMPLEMENTED, "税务验证发起逻辑尚未实现（待接入 RPA）");
    }

    @Operation(summary = "提交税务短信验证码",
            description = "body 传 verifyId + code；提交后状态变为已提交待处理；RPA 未接入时返回 501")
    @PostMapping("/code")
    public R<TaxVerifyVO> submit(@RequestBody(required = false) VerifyCodeSubmitRequest request) {
        if (!rpaGate.enabled()) {
            return R.fail(HttpStatus.NOT_IMPLEMENTED,
                    "税务自动验证尚未开通（RPA 未接入），暂不能提交验证码；如需继续，请由后台人工回填验证结果");
        }
        Long memberId = MiniSecurityUtils.getMemberId();
        return R.ok(verifyService.submit(memberId, request == null ? null : request.getVerifyId(),
                request == null ? null : request.getCode()));
    }

    @Operation(summary = "重发税务验证码",
            description = "body 传 verifyId；验证码过期或未收到时重新触发下发；RPA 未接入时返回 501")
    @PostMapping("/resends")
    public R<TaxVerifyVO> resend(@RequestBody(required = false) VerifyIdRequest request) {
        if (!rpaGate.enabled()) {
            return R.fail(HttpStatus.NOT_IMPLEMENTED,
                    "税务自动验证尚未开通（RPA 未接入），暂不能重发验证码；如需继续，请由后台人工回填验证结果");
        }
        // TODO 阶段5：RPA 接入后，校验 verifyId 归属并调用 RPA 重发短信验证码，刷新过期时间
        return R.fail(HttpStatus.NOT_IMPLEMENTED, "税务验证码重发逻辑尚未实现（待接入 RPA）");
    }

    @Operation(summary = "查询税务验证状态",
            description = "query 参数 verifyId；查询最新税务验证状态")
    @GetMapping("/detail")
    public R<TaxVerifyVO> refresh(@RequestParam("verifyId") Long verifyId) {
        Long memberId = MiniSecurityUtils.getMemberId();
        return R.ok(verifyService.refresh(memberId, verifyId));
    }
}
