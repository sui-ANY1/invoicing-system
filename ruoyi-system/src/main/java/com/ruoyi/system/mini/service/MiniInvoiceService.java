package com.ruoyi.system.mini.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.Enterprise;
import com.ruoyi.system.domain.Invoice;
import com.ruoyi.system.domain.InvoiceApply;
import com.ruoyi.system.domain.InvoiceItem;
import com.ruoyi.system.mini.constant.MiniAttachConstants;
import com.ruoyi.system.mini.dto.request.InvoiceApplyRequest;
import com.ruoyi.system.mini.dto.vo.InvoiceApplyDetailVO;
import com.ruoyi.system.mini.dto.vo.InvoiceApplyVO;
import com.ruoyi.system.mini.dto.vo.InvoiceItemVO;
import com.ruoyi.system.mini.dto.vo.InvoiceTicketVO;
import com.ruoyi.system.service.IEnterpriseService;
import com.ruoyi.system.service.IInvoiceApplyService;
import com.ruoyi.system.service.IInvoiceItemService;
import com.ruoyi.system.service.IInvoiceService;

/**
 * 小程序开票办理业务编排
 */
@Service
public class MiniInvoiceService {

    private static final Logger log = LoggerFactory.getLogger(MiniInvoiceService.class);

    @Autowired
    private IInvoiceApplyService applyService;

    @Autowired
    private IInvoiceItemService itemService;

    @Autowired
    private IEnterpriseService enterpriseService;

    @Autowired
    private MiniAttachService attachService;

    @Autowired
    private IInvoiceService invoiceService;

    /**
     * 提交开票申请（主单 + 明细），返回申请 id
     */
    @Transactional(rollbackFor = Exception.class)
    public Long apply(Long memberId, InvoiceApplyRequest request) {
        if (request == null || request.getApply() == null) {
            throw new ServiceException("开票内容不能为空");
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new ServiceException("至少需要一条开票明细");
        }

        InvoiceApplyRequest.InvoiceApplyForm form = request.getApply();
        // 购买方为企业/组织时税号必填（原型 P05：购买方税号“*”，个人不填）
        if ("1".equals(form.getBuyerType())
                && (form.getBuyerTaxNumber() == null || form.getBuyerTaxNumber().trim().isEmpty())) {
            throw new ServiceException("购买方为企业/组织时，购买方税号不能为空");
        }

        // 校验销售主体：存在、属于本人、已核验通过
        Enterprise enterprise = enterpriseService.selectEnterpriseByEnterpriseId(form.getEnterpriseId());
        if (enterprise == null) {
            throw new RuntimeException("销售主体不存在");
        }
        if (!memberId.equals(enterprise.getMemberId())) {
            throw new RuntimeException("请使用本人认证的企业开票");
        }
        if (!"2".equals(enterprise.getAuthStatus())) {
            throw new RuntimeException("企业主体尚未核验通过，暂不能开票");
        }

        // 主单
        InvoiceApply apply = new InvoiceApply();
        apply.setEnterpriseId(form.getEnterpriseId());
        apply.setInvoiceType(form.getInvoiceType());
        apply.setBuyerType(form.getBuyerType());
        apply.setBuyerName(form.getBuyerName());
        apply.setBuyerTaxNumber(form.getBuyerTaxNumber());
        apply.setBuyerAddressPhone(form.getBuyerAddressPhone());
        apply.setBuyerBank(form.getBuyerBank());
        apply.setDealProof(form.getDealProof());
        apply.setReceiveEmail(form.getReceiveEmail());
        apply.setMemberId(memberId);
        apply.setApplyNo(generateApplyNo());
        apply.setApplyStatus("0");
        apply.setFailReason(null);
        apply.setDelFlag("0");
        applyService.insertInvoiceApply(apply);
        Long applyId = apply.getApplyId();

        // 明细（票面金额/税率/税额由 RPA 回传，提交时强制为空）
        int sort = 1;
        for (InvoiceApplyRequest.InvoiceItemForm f : request.getItems()) {
            InvoiceItem item = new InvoiceItem();
            item.setApplyId(applyId);
            item.setItemName(f.getItemName());
            item.setCategory(f.getCategory());
            item.setSpec(f.getSpec());
            item.setQuantity(f.getQuantity());
            item.setUnit(f.getUnit());
            item.setOriginalPrice(f.getOriginalPrice());
            item.setPriceCaliber(f.getPriceCaliber());
            item.setAgreedAmount(f.getAgreedAmount());
            item.setInvoiceAmount(null);
            item.setTaxRate(null);
            item.setTaxAmount(null);
            item.setSortNo(f.getSortNo() == null ? (long) sort : f.getSortNo());
            itemService.insertInvoiceItem(item);
            sort++;
        }

        // 材料（附件）登记：交易证明（合同/订单）与请求体显式材料清单
        attachService.bindUrl(memberId, MiniAttachConstants.BIZ_INVOICE_APPLY, applyId,
                MiniAttachConstants.DEAL_PROOF, null, form.getDealProof());
        attachService.bind(memberId, MiniAttachConstants.BIZ_INVOICE_APPLY, applyId, null, request.getMaterials(),
                false);

        return applyId;
    }

    /**
     * 开票中心：当前会员的全部申请
     */
    public List<InvoiceApplyVO> center(Long memberId) {
        InvoiceApply query = new InvoiceApply();
        query.setMemberId(memberId);
        List<InvoiceApply> list = applyService.selectInvoiceApplyList(query);
        List<InvoiceApplyVO> result = new ArrayList<>();
        if (list != null) {
            for (InvoiceApply a : list) {
                InvoiceApplyVO vo = toApplyVO(a);
                fillInvoiceBrief(vo);
                result.add(vo);
            }
        }
        return result;
    }

    /**
     * 申请详情（主单 + 明细）
     */
    public InvoiceApplyDetailVO detail(Long memberId, Long id) {
        return buildDetail(getOwnedApply(memberId, id));
    }

    /**
     * 刷新状态：阶段3 直接查库返回；阶段5 在此触发 RPA 状态查询
     */
    public InvoiceApplyDetailVO refresh(Long memberId, Long id) {
        InvoiceApply apply = getOwnedApply(memberId, id);
        // TODO 阶段5：调用 RPA 查询最新开票状态并回写
        return buildDetail(apply);
    }

    /**
     * 停止办理：进行中的申请（0~4）可停止，置为 7
     */
    public InvoiceApplyDetailVO stop(Long memberId, Long id) {
        InvoiceApply apply = getOwnedApply(memberId, id);
        String status = apply.getApplyStatus();
        if ("5".equals(status) || "6".equals(status) || "7".equals(status)) {
            throw new RuntimeException("当前状态不可停止");
        }
        apply.setApplyStatus("7");
        applyService.updateInvoiceApply(apply);
        // TODO 阶段5：通知 RPA 终止任务
        log.warn("停止开票申请 applyId={}：RPA 未接入，未通知外部任务终止（仅本地置状态 7）", apply.getApplyId());
        return buildDetail(apply);
    }

    /**
     * 重新获取发票文件：阶段3 占位；阶段5 在此通知 RPA 重新交付
     */
    public InvoiceApplyDetailVO refetchFile(Long memberId, Long id) {
        InvoiceApply apply = getOwnedApply(memberId, id);
        String status = apply.getApplyStatus();
        if (!"4".equals(status) && !"5".equals(status)) {
            throw new RuntimeException("当前状态无需重新获取文件");
        }
        // TODO 阶段5：调用 RPA 重新获取/交付发票文件
        return buildDetail(apply);
    }

    /**
     * 取出申请并校验存在与归属
     */
    private InvoiceApply getOwnedApply(Long memberId, Long id) {
        InvoiceApply apply = applyService.selectInvoiceApplyByApplyId(id);
        if (apply == null) {
            throw new RuntimeException("开票申请不存在");
        }
        if (!memberId.equals(apply.getMemberId())) {
            throw new RuntimeException("无权操作该申请");
        }
        return apply;
    }

    /**
     * 组装详情
     */
    private InvoiceApplyDetailVO buildDetail(InvoiceApply apply) {
        InvoiceItem itemQuery = new InvoiceItem();
        itemQuery.setApplyId(apply.getApplyId());
        List<InvoiceItem> items = itemService.selectInvoiceItemList(itemQuery);

        List<InvoiceItemVO> itemVOs = new ArrayList<>();
        if (items != null) {
            for (InvoiceItem i : items) {
                itemVOs.add(toItemVO(i));
            }
        }

        InvoiceApplyDetailVO vo = new InvoiceApplyDetailVO();
        InvoiceApplyVO applyVO = toApplyVO(apply);
        fillInvoiceBrief(applyVO);
        vo.setApply(applyVO);
        vo.setItems(itemVOs);
        vo.setInvoices(toTicketVOs(listInvoices(apply.getApplyId())));
        return vo;
    }

    /**
     * 按申请取出已回传的真实发票（一票一行，蓝红都在内）
     */
    private List<Invoice> listInvoices(Long applyId) {
        Invoice query = new Invoice();
        query.setApplyId(applyId);
        List<Invoice> invoices = invoiceService.selectInvoiceList(query);
        return invoices == null ? new ArrayList<>() : invoices;
    }

    /**
     * 主单上的发票摘要：张数 + 首张发票 id / 号码（列表卡片要展示「业务编号 / 发票号码」）
     */
    private void fillInvoiceBrief(InvoiceApplyVO vo) {
        List<Invoice> invoices = listInvoices(vo.getId());
        vo.setInvoiceCount(invoices.size());
        vo.setInvoiceId(invoices.isEmpty() ? null : invoices.get(0).getInvoiceId());
        vo.setInvoiceNo(invoices.isEmpty() ? null : invoices.get(0).getInvoiceNo());
        // 列表卡片展示企业名称
        if (vo.getEnterpriseId() != null) {
            Enterprise enterprise = enterpriseService.selectEnterpriseByEnterpriseId(vo.getEnterpriseId());
            if (enterprise != null) {
                vo.setEnterpriseName(enterprise.getEnterpriseName());
            }
        }
    }

    private List<InvoiceTicketVO> toTicketVOs(List<Invoice> invoices) {
        List<InvoiceTicketVO> result = new ArrayList<>();
        for (Invoice inv : invoices) {
            result.add(toTicketVO(inv));
        }
        return result;
    }

    private InvoiceTicketVO toTicketVO(Invoice inv) {
        InvoiceTicketVO vo = new InvoiceTicketVO();
        vo.setInvoiceId(inv.getInvoiceId());
        vo.setInvoiceNo(inv.getInvoiceNo());
        vo.setInvoiceCode(inv.getInvoiceCode());
        vo.setInvoiceKind(inv.getInvoiceKind());
        vo.setInvoiceDate(inv.getInvoiceDate());
        vo.setSellerName(inv.getSellerName());
        vo.setSellerTaxNumber(inv.getSellerTaxNumber());
        vo.setBuyerName(inv.getBuyerName());
        vo.setBuyerTaxNumber(inv.getBuyerTaxNumber());
        vo.setTotalAmount(inv.getTotalAmount());
        vo.setTotalTax(inv.getTotalTax());
        vo.setTotalPriceTax(inv.getTotalPriceTax());
        vo.setRedStatus(inv.getRedStatus());
        vo.setFileStatus(inv.getFileStatus());
        vo.setPdfUrl(inv.getPdfUrl());
        vo.setOfdUrl(inv.getOfdUrl());
        vo.setXmlUrl(inv.getXmlUrl());
        return vo;
    }

    private InvoiceApplyVO toApplyVO(InvoiceApply a) {
        InvoiceApplyVO vo = new InvoiceApplyVO();
        vo.setId(a.getApplyId());
        vo.setApplyNo(a.getApplyNo());
        vo.setEnterpriseId(a.getEnterpriseId());
        vo.setInvoiceType(a.getInvoiceType());
        vo.setBuyerType(a.getBuyerType());
        vo.setBuyerName(a.getBuyerName());
        vo.setBuyerTaxNumber(a.getBuyerTaxNumber());
        vo.setBuyerAddressPhone(a.getBuyerAddressPhone());
        vo.setBuyerBank(a.getBuyerBank());
        vo.setDealProof(a.getDealProof());
        vo.setReceiveEmail(a.getReceiveEmail());
        vo.setApplyStatus(a.getApplyStatus());
        vo.setFailReason(a.getFailReason());
        vo.setCreateTime(a.getCreateTime());
        return vo;
    }

    private InvoiceItemVO toItemVO(InvoiceItem i) {
        InvoiceItemVO vo = new InvoiceItemVO();
        vo.setId(i.getItemId());
        vo.setItemName(i.getItemName());
        vo.setCategory(i.getCategory());
        vo.setSpec(i.getSpec());
        vo.setQuantity(i.getQuantity());
        vo.setUnit(i.getUnit());
        vo.setOriginalPrice(i.getOriginalPrice());
        vo.setPriceCaliber(i.getPriceCaliber());
        vo.setAgreedAmount(i.getAgreedAmount());
        vo.setInvoiceAmount(i.getInvoiceAmount());
        vo.setTaxRate(i.getTaxRate());
        vo.setTaxAmount(i.getTaxAmount());
        vo.setSortNo(i.getSortNo());
        return vo;
    }

    /**
     * 生成业务单号：INV + 时间戳 + 4位随机数
     */
    private String generateApplyNo() {
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int random = (int) (Math.random() * 9000) + 1000;
        return "INV" + date + random;
    }
}
