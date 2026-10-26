package com.ruoyi.system.mini.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.Invoice;
import com.ruoyi.system.domain.InvoiceApply;
import com.ruoyi.system.domain.RedApply;
import com.ruoyi.system.domain.RedItem;
import com.ruoyi.system.mini.constant.MiniAttachConstants;
import com.ruoyi.system.mini.dto.request.RedApplyRequest;
import com.ruoyi.system.mini.dto.vo.RedApplyDetailVO;
import com.ruoyi.system.mini.dto.vo.RedApplyListItemVO;
import com.ruoyi.system.mini.dto.vo.RedApplyVO;
import com.ruoyi.system.mini.dto.vo.RedItemVO;
import com.ruoyi.system.service.IInvoiceApplyService;
import com.ruoyi.system.service.IInvoiceService;
import com.ruoyi.system.service.IRedApplyService;
import com.ruoyi.system.service.IRedItemService;

/**
 * 小程序红字办理业务编排
 */
@Service
public class MiniRedService {

    @Autowired
    private MiniAttachService attachService;

    @Autowired
    private IRedApplyService redApplyService;

    @Autowired
    private IRedItemService redItemService;

    @Autowired
    private IInvoiceService invoiceService;

    @Autowired
    private IInvoiceApplyService applyService;

    /**
     * 提交红字申请（主单 + 明细），返回红字申请 id
     */
    @Transactional(rollbackFor = Exception.class)
    public Long apply(Long memberId, RedApplyRequest request) {
        if (request == null || request.getApply() == null) {
            throw new ServiceException("红字申请内容不能为空");
        }
        RedApplyRequest.RedApplyForm form = request.getApply();
        if (form.getOriginalInvoiceId() == null) {
            throw new ServiceException("请选择需要红冲的发票");
        }

        // 校验原蓝票：存在、蓝字、本人、已交付、未全额红冲
        Invoice invoice = invoiceService.selectInvoiceByInvoiceId(form.getOriginalInvoiceId());
        if (invoice == null) {
            throw new RuntimeException("原发票不存在");
        }
        if (!"1".equals(invoice.getInvoiceKind())) {
            throw new RuntimeException("仅支持对蓝字发票发起红字");
        }
        InvoiceApply owner = applyService.selectInvoiceApplyByApplyId(invoice.getApplyId());
        if (owner == null || !memberId.equals(owner.getMemberId())) {
            throw new RuntimeException("无权操作该发票");
        }
        if (!"1".equals(invoice.getFileStatus())) {
            throw new RuntimeException("发票尚未完成交付，暂不能红冲");
        }
        if ("2".equals(invoice.getRedStatus())) {
            throw new RuntimeException("该发票已全额红冲");
        }

        // 在途红字与剩余可处理金额：同一张蓝票不允许并发多条红字，也不允许超出剩余可红冲金额
        BigDecimal redTotal = BigDecimal.ZERO;
        boolean hasInFlight = false;
        RedApply redQuery = new RedApply();
        redQuery.setOriginalInvoiceId(invoice.getInvoiceId());
        redQuery.setDelFlag("0");
        List<RedApply> historyReds = redApplyService.selectRedApplyList(redQuery);
        if (historyReds != null) {
            for (RedApply historyRed : historyReds) {
                if (MiniInvoiceDetailService.isInFlight(historyRed.getRedStatus())) {
                    hasInFlight = true;
                    continue;
                }
                Invoice redInvoiceQuery = new Invoice();
                redInvoiceQuery.setRedApplyId(historyRed.getRedApplyId());
                List<Invoice> redInvoices = invoiceService.selectInvoiceList(redInvoiceQuery);
                if (redInvoices != null) {
                    for (Invoice redInvoice : redInvoices) {
                        if (!"2".equals(redInvoice.getDelFlag()) && redInvoice.getTotalPriceTax() != null) {
                            redTotal = redTotal.add(redInvoice.getTotalPriceTax().abs());
                        }
                    }
                }
            }
        }
        if (hasInFlight) {
            throw new ServiceException("该发票存在进行中的红字申请，请等待处理完成后再发起");
        }
        BigDecimal remaining = invoice.getTotalPriceTax() == null ? null
                : invoice.getTotalPriceTax().subtract(redTotal);
        if (remaining != null && remaining.signum() <= 0) {
            throw new ServiceException("该发票已无可红冲金额（剩余可处理金额为 0）");
        }

        // 原因 / 范围校验
        String reason = form.getRedReason();
        if (StringUtils.isEmpty(reason) || (!"1".equals(reason) && !"2".equals(reason)
                && !"3".equals(reason) && !"9".equals(reason))) {
            throw new ServiceException("请选择正确的红字原因（1开票有误 2退货折让 3服务中止 9其他）");
        }
        String range = form.getRedRange();
        if (StringUtils.isEmpty(range) || (!"1".equals(range) && !"2".equals(range))) {
            throw new ServiceException("请选择处理范围（全部/部分）");
        }
        if ("2".equals(range) && (request.getItems() == null || request.getItems().isEmpty())) {
            throw new ServiceException("部分红冲需提供红冲明细");
        }

        // 主单
        RedApply apply = new RedApply();
        apply.setOriginalInvoiceId(form.getOriginalInvoiceId());
        apply.setRedReason(reason);
        apply.setRedRange(range);
        apply.setDescription(form.getDescription());
        apply.setProofUrl(form.getProofUrl());
        apply.setMemberId(memberId);
        apply.setRedApplyNo(generateRedApplyNo());
        apply.setRedStatus("0");           // 已提交
        apply.setConfirmStatus("0");       // 红字确认单未发起
        apply.setConfirmStartTime(null);
        apply.setConfirmExpireTime(null);
        apply.setFailReason(null);
        apply.setDelFlag("0");
        redApplyService.insertRedApply(apply);
        Long redApplyId = apply.getRedApplyId();

        // 明细（部分红冲；票面金额/税率/税额由 RPA 回传，提交时强制为空）
        if (request.getItems() != null) {
            int sort = 1;
            for (RedApplyRequest.RedItemForm f : request.getItems()) {
                RedItem item = new RedItem();
                item.setRedApplyId(redApplyId);
                item.setOriginalItemId(f.getOriginalItemId());
                item.setItemName(f.getItemName());
                item.setQuantity(f.getQuantity());
                item.setInvoiceAmount(null);
                item.setTaxRate(null);
                item.setTaxAmount(null);
                item.setSortNo(f.getSortNo() == null ? (long) sort : f.getSortNo());
                redItemService.insertRedItem(item);
                sort++;
            }
        }

        // 材料（附件）登记：红字证明与其他补充材料
        attachService.bindUrl(memberId, MiniAttachConstants.BIZ_RED_APPLY, redApplyId,
                MiniAttachConstants.DEAL_PROOF, null, form.getProofUrl());
        attachService.bind(memberId, MiniAttachConstants.BIZ_RED_APPLY, redApplyId, null, request.getMaterials(),
                false);

        return redApplyId;
    }

    /**
     * 红字记录列表（发票中心「红字记录」页签）：本人全部红字申请，按提交时间倒序
     */
    public List<RedApplyListItemVO> list(Long memberId) {
        RedApply query = new RedApply();
        query.setMemberId(memberId);
        List<RedApply> rows = redApplyService.selectRedApplyList(query);
        List<RedApplyListItemVO> result = new ArrayList<>();
        if (rows == null) {
            return result;
        }
        for (RedApply a : rows) {
            RedApplyListItemVO vo = new RedApplyListItemVO();
            vo.setId(a.getRedApplyId());
            vo.setRedApplyNo(a.getRedApplyNo());
            vo.setOriginalInvoiceId(a.getOriginalInvoiceId());
            vo.setRedReason(a.getRedReason());
            vo.setRedStatus(a.getRedStatus());
            vo.setConfirmStatus(a.getConfirmStatus());
            vo.setCreateTime(a.getCreateTime());
            // 原蓝票号码与企业名称：列表卡片需要展示「原票号码 / 企业名称」
            Invoice invoice = a.getOriginalInvoiceId() == null ? null
                    : invoiceService.selectInvoiceByInvoiceId(a.getOriginalInvoiceId());
            if (invoice != null) {
                vo.setOriginalInvoiceNo(invoice.getInvoiceNo());
                vo.setEnterpriseName(invoice.getSellerName());
            }
            result.add(vo);
        }
        // 按提交时间倒序，最新的在前
        result.sort((x, y) -> {
            Date dx = x.getCreateTime();
            Date dy = y.getCreateTime();
            if (dx == null && dy == null) {
                return 0;
            }
            if (dx == null) {
                return 1;
            }
            if (dy == null) {
                return -1;
            }
            return dy.compareTo(dx);
        });
        return result;
    }

    /**
     * 红字申请详情（主单 + 明细）
     */
    public RedApplyDetailVO detail(Long memberId, Long id) {
        RedApply apply = getOwnedRedApply(memberId, id);
        return buildDetail(apply);
    }

    /**
     * 刷新红字状态：阶段3 查库；阶段5 向 RPA 查询
     */
    public RedApplyDetailVO refresh(Long memberId, Long id) {
        RedApply apply = getOwnedRedApply(memberId, id);
        // TODO 阶段5：向 RPA 查询红字确认单 / 红票状态并回写
        return buildDetail(apply);
    }

    /**
     * 取出红字申请并校验存在与归属
     */
    private RedApply getOwnedRedApply(Long memberId, Long id) {
        RedApply apply = redApplyService.selectRedApplyByRedApplyId(id);
        if (apply == null) {
            throw new RuntimeException("红字申请不存在");
        }
        if (!memberId.equals(apply.getMemberId())) {
            throw new RuntimeException("无权操作该红字申请");
        }
        return apply;
    }

    /**
     * 组装详情
     */
    private RedApplyDetailVO buildDetail(RedApply apply) {
        RedItem query = new RedItem();
        query.setRedApplyId(apply.getRedApplyId());
        List<RedItem> items = redItemService.selectRedItemList(query);

        List<RedItemVO> itemVOs = new ArrayList<>();
        if (items != null) {
            for (RedItem i : items) {
                itemVOs.add(toItemVO(i));
            }
        }

        RedApplyDetailVO vo = new RedApplyDetailVO();
        vo.setApply(toApplyVO(apply));
        vo.setItems(itemVOs);
        return vo;
    }

    private RedApplyVO toApplyVO(RedApply a) {
        RedApplyVO vo = new RedApplyVO();
        vo.setId(a.getRedApplyId());
        vo.setRedApplyNo(a.getRedApplyNo());
        vo.setOriginalInvoiceId(a.getOriginalInvoiceId());
        vo.setRedReason(a.getRedReason());
        vo.setRedRange(a.getRedRange());
        vo.setRedStatus(a.getRedStatus());
        vo.setConfirmStatus(a.getConfirmStatus());
        vo.setConfirmStartTime(a.getConfirmStartTime());
        vo.setConfirmExpireTime(a.getConfirmExpireTime());
        vo.setDescription(a.getDescription());
        vo.setProofUrl(a.getProofUrl());
        vo.setFailReason(a.getFailReason());
        vo.setCreateTime(a.getCreateTime());
        return vo;
    }

    private RedItemVO toItemVO(RedItem i) {
        RedItemVO vo = new RedItemVO();
        vo.setId(i.getRedItemId());
        vo.setOriginalItemId(i.getOriginalItemId());
        vo.setItemName(i.getItemName());
        vo.setQuantity(i.getQuantity());
        vo.setInvoiceAmount(i.getInvoiceAmount());
        vo.setTaxRate(i.getTaxRate());
        vo.setTaxAmount(i.getTaxAmount());
        vo.setSortNo(i.getSortNo());
        return vo;
    }

    /**
     * 生成红字单号：RED + 时间戳 + 4位随机
     */
    private String generateRedApplyNo() {
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int random = (int) (Math.random() * 9000) + 1000;
        return "RED" + date + random;
    }
}
