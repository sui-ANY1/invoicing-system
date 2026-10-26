package com.ruoyi.system.mini.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.Invoice;
import com.ruoyi.system.domain.InvoiceApply;
import com.ruoyi.system.domain.RedApply;
import com.ruoyi.system.mini.dto.vo.MiniInvoiceDetailVO;
import com.ruoyi.system.service.IInvoiceApplyService;
import com.ruoyi.system.service.IInvoiceService;
import com.ruoyi.system.service.IRedApplyService;

/**
 * 小程序发票详情（P19）
 *
 * 只做「查询 + 现状判断」：不重算税额与合计（AC20/AC29），
 * 剩余可处理金额 = 票面价税合计 − 已红冲合计，票面金额未回传时返回 null。
 */
@Service
public class MiniInvoiceDetailService {

    /** 红字申请未完成的状态：0已提交 1处理中 2确认单待确认 3结果核对中 */
    private static final String[] IN_FLIGHT_RED_STATUS = { "0", "1", "2", "3" };

    @Autowired
    private IInvoiceService invoiceService;

    @Autowired
    private IInvoiceApplyService invoiceApplyService;

    @Autowired
    private IRedApplyService redApplyService;

    /**
     * 发票详情：归属校验 + 票面 + 文件可用性 + 红冲资格
     */
    public MiniInvoiceDetailVO detail(Long memberId, Long invoiceId) {
        Invoice invoice = invoiceService.selectInvoiceByInvoiceId(invoiceId);
        if (invoice == null || "2".equals(invoice.getDelFlag())) {
            throw new RuntimeException("发票不存在");
        }

        MiniInvoiceDetailVO vo = new MiniInvoiceDetailVO();
        vo.setInvoiceId(invoice.getInvoiceId());
        vo.setInvoiceKind(invoice.getInvoiceKind());
        vo.setInvoiceKindText("2".equals(invoice.getInvoiceKind()) ? "红字发票" : "蓝字发票");
        vo.setInvoiceNo(invoice.getInvoiceNo());
        vo.setInvoiceCode(invoice.getInvoiceCode());
        vo.setInvoiceDate(invoice.getInvoiceDate());
        vo.setSellerName(invoice.getSellerName());
        vo.setBuyerName(invoice.getBuyerName());
        vo.setTotalAmount(invoice.getTotalAmount());
        vo.setTotalTax(invoice.getTotalTax());
        vo.setTotalPriceTax(invoice.getTotalPriceTax());
        vo.setRedStatus(invoice.getRedStatus());
        vo.setRedStatusText(redStatusText(invoice.getRedStatus()));
        vo.setFileStatus(invoice.getFileStatus());
        vo.setFileStatusText(fileStatusText(invoice.getFileStatus()));
        vo.setPdfAvailable(StringUtils.isNotEmpty(invoice.getPdfUrl()));
        vo.setOfdAvailable(StringUtils.isNotEmpty(invoice.getOfdUrl()));
        vo.setApplyId(invoice.getApplyId());
        vo.setRedApplyId(invoice.getRedApplyId());

        // 归属校验：蓝票看开票申请，红票看红字申请
        if (invoice.getApplyId() != null) {
            InvoiceApply apply = invoiceApplyService.selectInvoiceApplyByApplyId(invoice.getApplyId());
            if (apply == null || !memberId.equals(apply.getMemberId())) {
                throw new RuntimeException("无权查看该发票");
            }
            vo.setApplyNo(apply.getApplyNo());
        } else if (invoice.getRedApplyId() != null) {
            RedApply red = redApplyService.selectRedApplyByRedApplyId(invoice.getRedApplyId());
            if (red == null || !memberId.equals(red.getMemberId())) {
                throw new RuntimeException("无权查看该发票");
            }
            vo.setRedApplyNo(red.getRedApplyNo());
        } else {
            throw new RuntimeException("无权查看该发票");
        }

        if ("1".equals(invoice.getInvoiceKind())) {
            fillRedQualification(vo, invoice);
        } else {
            vo.setRemainingAmount(BigDecimal.ZERO);
            vo.setInFlightRedCount(0);
            vo.setCanApplyRed(false);
            vo.setCannotApplyRedReason("红字发票不能再发起红字申请");
        }
        return vo;
    }

    /**
     * 蓝票的红冲资格：剩余可处理金额 + 在途红字 + 是否可申请
     */
    private void fillRedQualification(MiniInvoiceDetailVO vo, Invoice blueInvoice) {
        boolean delivered = "1".equals(blueInvoice.getFileStatus());
        // 原票未交付时票面金额可能为空，无法算出剩余可处理金额
        boolean amountKnown = blueInvoice.getTotalPriceTax() != null;

        List<RedApply> redApplies = selectRedApplies(blueInvoice.getInvoiceId());
        BigDecimal redTotal = BigDecimal.ZERO;
        long inFlight = 0;
        for (RedApply red : redApplies) {
            if (isInFlight(red.getRedStatus())) {
                inFlight++;
                continue;
            }
            redTotal = redTotal.add(sumRedInvoiceAmount(red.getRedApplyId()));
        }

        vo.setInFlightRedCount(inFlight);
        BigDecimal remaining = amountKnown ? blueInvoice.getTotalPriceTax().subtract(redTotal) : null;
        if (remaining != null && remaining.signum() < 0) {
            remaining = BigDecimal.ZERO;
        }
        vo.setRemainingAmount(remaining);

        String reason = null;
        if (!"1".equals(blueInvoice.getInvoiceKind())) {
            reason = "仅蓝字发票可以申请红字";
        } else if (!delivered) {
            reason = "发票尚未完成交付，暂不能红冲";
        } else if ("2".equals(blueInvoice.getRedStatus())) {
            reason = "该发票已全额红冲";
        } else if (inFlight > 0) {
            reason = "该发票存在进行中的红字申请，请等待处理完成";
        } else if (remaining != null && remaining.signum() <= 0) {
            reason = "该发票已无可红冲金额";
        }
        vo.setCanApplyRed(reason == null);
        vo.setCannotApplyRedReason(reason);
    }

    /**
     * 某张蓝票下的全部红字申请（未删除）
     */
    private List<RedApply> selectRedApplies(Long originalInvoiceId) {
        RedApply query = new RedApply();
        query.setOriginalInvoiceId(originalInvoiceId);
        query.setDelFlag("0");
        return redApplyService.selectRedApplyList(query);
    }

    /**
     * 某次红字申请已开出的红票价税合计
     */
    private BigDecimal sumRedInvoiceAmount(Long redApplyId) {
        Invoice query = new Invoice();
        query.setRedApplyId(redApplyId);
        List<Invoice> list = invoiceService.selectInvoiceList(query);
        BigDecimal sum = BigDecimal.ZERO;
        if (list != null) {
            for (Invoice i : list) {
                if ("2".equals(i.getDelFlag())) {
                    continue;
                }
                if (i.getTotalPriceTax() != null) {
                    sum = sum.add(i.getTotalPriceTax());
                }
            }
        }
        return sum;
    }

    /**
     * 红字申请是否在途（未走到完成/失败/停止）
     */
    public static boolean isInFlight(String redStatus) {
        if (redStatus == null) {
            return false;
        }
        for (String s : IN_FLIGHT_RED_STATUS) {
            if (s.equals(redStatus)) {
                return true;
            }
        }
        return false;
    }

    private String redStatusText(String redStatus) {
        if (redStatus == null) {
            return null;
        }
        switch (redStatus) {
            case "0":
                return "未红冲";
            case "1":
                return "部分红冲";
            case "2":
                return "全额红冲";
            default:
                return redStatus;
        }
    }

    private String fileStatusText(String fileStatus) {
        if (fileStatus == null) {
            return null;
        }
        switch (fileStatus) {
            case "0":
                return "待文件";
            case "1":
                return "已获取";
            case "2":
                return "获取失败";
            default:
                return fileStatus;
        }
    }
}
