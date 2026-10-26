package com.ruoyi.system.mini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ruoyi.system.mini.dto.R;
import com.ruoyi.system.mini.dto.request.EnterpriseSubmitRequest;
import com.ruoyi.system.mini.dto.request.TaxCredentialUpdateRequest;
import com.ruoyi.system.mini.dto.vo.EnterpriseDetailVO;
import com.ruoyi.system.mini.dto.vo.EnterpriseListVO;
import com.ruoyi.system.mini.dto.vo.IdResultVO;
import com.ruoyi.system.mini.dto.vo.PageResult;
import com.ruoyi.system.mini.security.MiniSecurityUtils;
import com.ruoyi.system.mini.service.MiniEnterpriseService;
import com.ruoyi.system.mini.support.MiniPageUtils;
import jakarta.validation.Valid;

/**
 * 小程序主体认证
 *
 * 统一约定：RESTful 资源路径；列表用 GET + query 参数，详情用 GET + path 变量，
 * 提交用 POST + JSON body。
 */
@Tag(name = "主体认证", description = "企业主体认证资料的提交与查询（RESTful：GET 查询、POST 提交）")
@RestController
@RequestMapping("/api/mini/enterprises")
public class MiniEnterpriseController {

    @Autowired
    private MiniEnterpriseService enterpriseService;

    @Operation(summary = "查询我的企业列表",
            description = "分页参数 pageNum/pageSize（默认 1/10，上限 200）；enterpriseName 按公司名称模糊搜索；返回 {total,rows}")
    @GetMapping
    public R<PageResult<EnterpriseListVO>> list(
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "enterpriseName", required = false) String enterpriseName) {
        Long memberId = MiniSecurityUtils.getMemberId();
        List<EnterpriseListVO> list = enterpriseService.listByMember(memberId);
        String kw = (enterpriseName == null || enterpriseName.trim().isEmpty()) ? null
                : enterpriseName.trim().toLowerCase();
        return R.ok(MiniPageUtils.of(list, pageNum, pageSize,
                vo -> kw == null || MiniPageUtils.containsIgnoreCase(vo.getEnterpriseName(), kw)));
    }

    @Operation(summary = "提交主体认证资料",
            description = "一次提交企业主体、经办人、税务凭据；提交后进入核验中，返回企业ID")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public R<IdResultVO> submit(@Valid @RequestBody EnterpriseSubmitRequest request) {
        Long memberId = MiniSecurityUtils.getMemberId();
        Long id = enterpriseService.submit(memberId, request);
        return R.ok(new IdResultVO(id));
    }

    @Operation(summary = "查询主体认证详情",
            description = "query 参数 enterpriseId；返回企业主体、经办人、税务凭据；税务账号脱敏、密码不返回")
    @GetMapping("/detail")
    public R<EnterpriseDetailVO> detail(@RequestParam("enterpriseId") Long enterpriseId) {
        Long memberId = MiniSecurityUtils.getMemberId();
        return R.ok(enterpriseService.detail(memberId, enterpriseId));
    }

    @Operation(summary = "维护税务凭据",
            description = "body 传 enterpriseId 指定主体，以及 taxAccount/taxPassword/authScope/authStartDate/authEndDate 中任意项；"
                    + "密码留空表示保持原值，密码不回显")
    @PutMapping("/tax-credential")
    public R<Void> updateCredential(@Valid @RequestBody TaxCredentialUpdateRequest request) {
        Long memberId = MiniSecurityUtils.getMemberId();
        enterpriseService.updateCredential(memberId, request.getEnterpriseId(), request);
        return R.ok();
    }
}
