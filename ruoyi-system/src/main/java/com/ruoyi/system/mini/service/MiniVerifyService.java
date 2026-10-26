package com.ruoyi.system.mini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.InvoiceApply;
import com.ruoyi.system.domain.TaxVerify;
import com.ruoyi.system.mini.dto.vo.TaxVerifyVO;
import com.ruoyi.system.service.IInvoiceApplyService;
import com.ruoyi.system.service.ITaxVerifyService;

/**
 * 小程序税务验证业务编排
 */
@Service
public class MiniVerifyService {

    @Autowired
    private ITaxVerifyService verifyService;

    @Autowired
    private IInvoiceApplyService applyService;

    /**
     * 提交短信验证码，返回最新验证结果
     */
    public TaxVerifyVO submit(Long memberId, Long verifyId, String code) {
        TaxVerify verify = getOwnedVerify(memberId, verifyId);
        if (StringUtils.isEmpty(code)) {
            throw new RuntimeException("验证码不能为空");
        }
        String status = verify.getVerifyStatus();
        if ("2".equals(status)) {
            throw new RuntimeException("已验证通过，无需重复提交");
        }
        if ("3".equals(status)) {
            throw new RuntimeException("验证已失效，请重新发起");
        }
        verify.setVerifyStatus("1");
        verifyService.updateTaxVerify(verify);
        // TODO 阶段5：把验证码经 RPA 代填到电子税局，按结果置 2 通过 / 3 失败
        return toVO(verify);
    }

    /**
     * 刷新验证结果
     */
    public TaxVerifyVO refresh(Long memberId, Long verifyId) {
        TaxVerify verify = getOwnedVerify(memberId, verifyId);
        // TODO 阶段5：向 RPA 查询验证结果并回写 2/3
        return toVO(verify);
    }

    /**
     * 取出验证记录并校验存在与归属（反查业务单）
     */
    private TaxVerify getOwnedVerify(Long memberId, Long verifyId) {
        TaxVerify verify = verifyService.selectTaxVerifyByVerifyId(verifyId);
        if (verify == null) {
            throw new RuntimeException("验证记录不存在");
        }
        if ("1".equals(verify.getBizType())) {
            InvoiceApply apply = applyService.selectInvoiceApplyByApplyId(verify.getBizId());
            if (apply == null || !memberId.equals(apply.getMemberId())) {
                throw new RuntimeException("无权操作该验证");
            }
        } else if ("2".equals(verify.getBizType())) {
            throw new RuntimeException("红字验证将在后续开放");
        }
        return verify;
    }

    /**
     * 实体 -> VO（不返回内部 verifyKey）
     */
    private TaxVerifyVO toVO(TaxVerify v) {
        TaxVerifyVO vo = new TaxVerifyVO();
        vo.setId(v.getVerifyId());
        vo.setBizType(v.getBizType());
        vo.setBizId(v.getBizId());
        vo.setVerifyType(v.getVerifyType());
        vo.setTargetAccount(v.getTargetAccount());
        vo.setVerifyStatus(v.getVerifyStatus());
        vo.setExpireTime(v.getExpireTime());
        return vo;
    }
}
