package com.ruoyi.system.pc.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.Attachment;
import com.ruoyi.system.domain.AuditRecord;
import com.ruoyi.system.domain.Enterprise;
import com.ruoyi.system.domain.Invoice;
import com.ruoyi.system.domain.InvoiceApply;
import com.ruoyi.system.domain.InvoiceItem;
import com.ruoyi.system.domain.ManualResult;
import com.ruoyi.system.domain.Message;
import com.ruoyi.system.domain.RedApply;
import com.ruoyi.system.domain.RedItem;
import com.ruoyi.system.domain.RpaTask;
import com.ruoyi.system.domain.TaxVerify;
import com.ruoyi.system.pc.dto.PcRequests;
import com.ruoyi.system.pc.mapper.PcRecordMapper;
import com.ruoyi.system.pc.support.PcAttachments;
import com.ruoyi.system.pc.support.PcDict;
import com.ruoyi.system.pc.vo.PcAttachmentVO;
import com.ruoyi.system.pc.vo.PcInvoiceTicketVO;
import com.ruoyi.system.pc.vo.PcRecordDetailVO;
import com.ruoyi.system.pc.vo.PcRecordItemVO;
import com.ruoyi.system.service.IAttachmentService;
import com.ruoyi.system.service.IAuditRecordService;
import com.ruoyi.system.service.IEnterpriseService;
import com.ruoyi.system.service.IInvoiceApplyService;
import com.ruoyi.system.service.IInvoiceItemService;
import com.ruoyi.system.service.IInvoiceService;
import com.ruoyi.system.service.IManualResultService;
import com.ruoyi.system.service.IMessageService;
import com.ruoyi.system.service.IRedApplyService;
import com.ruoyi.system.service.IRedItemService;
import com.ruoyi.system.service.IRpaTaskService;
import com.ruoyi.system.service.ITaxVerifyService;

/**
 * PC 开票与红字统一记录（D04 列表 / D05 详情与处理动作）
 *
 * 硬规则：申请与发票不混为一谈（一张真实票一行）；结果未知只允许查询原任务，禁止重复提交；
 * 已开票只允许补取文件，不重新开票；红字确认单 72 小时，超时只核对实际状态；
 * 人工回填与审核动作一律写 t_audit_record / t_manual_result 留痕，平台不重算税额。
 */
@Service
public class PcRecordService {

    /** 审核留痕业务类型：1主体认证 2开票 3红字 */
    private static final String AUDIT_BIZ_APPLY = "2";
    private static final String AUDIT_BIZ_RED = "3";

    /** 人工回填业务类型：1红字 2蓝字 */
    private static final String MANUAL_BIZ_RED = "1";

    /** 站内消息业务类型：1开票 2红字 3注册 4认证 9通用 */
    private static final String MSG_BIZ_APPLY = "1";
    private static final String MSG_BIZ_RED = "2";

    private static final String BIZ_INVOICE_APPLY = "invoice_apply";
    private static final String BIZ_RED_APPLY = "red_apply";

    private static final int DEFAULT_CONFIRM_HOURS = 72;

    @Autowired
    private PcRecordMapper recordMapper;

    @Autowired
    private PcInvoiceQueryService invoiceQueryService;

    @Autowired
    private IInvoiceApplyService invoiceApplyService;

    @Autowired
    private IInvoiceItemService invoiceItemService;

    @Autowired
    private IRedApplyService redApplyService;

    @Autowired
    private IRedItemService redItemService;

    @Autowired
    private ITaxVerifyService taxVerifyService;

    @Autowired
    private IRpaTaskService rpaTaskService;

    @Autowired
    private IAuditRecordService auditRecordService;

    @Autowired
    private IManualResultService manualResultService;

    @Autowired
    private IAttachmentService attachmentService;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IInvoiceService invoiceService;

    @Autowired
    private IEnterpriseService enterpriseService;

    /**
     * D04 统一记录分页列表（开票 + 红字合并；分页由 Controller 控制）
     */
    public List<PcRecordItemVO> list(PcRequests.RecordQuery query) {
        List<PcRecordItemVO> list = recordMapper.selectRecordList(query);
        List<PcRecordItemVO> result = new ArrayList<>();
        if (list == null) {
            return result;
        }
        for (PcRecordItemVO item : list) {
            item.setTypeText(PcDict.text(PcDict.RECORD_TYPE, item.getType()));
            item.setStatusText("2".equals(item.getType())
                    ? PcDict.text(PcDict.RED_STATUS, item.getStatus())
                    : PcDict.text(PcDict.APPLY_STATUS, item.getStatus()));
            item.setInvoiceTypeText(PcDict.text(PcDict.INVOICE_TYPE, item.getInvoiceType()));
            item.setConfirmStatusText(PcDict.text(PcDict.CONFIRM_STATUS, item.getConfirmStatus()));
            // 列表口径：脏数据只降级为「待返回」，不能让一行异常拖垮整页（D04）
            item.setAmountDisplay(PcDict.amount(parseAmountLenient(item.getAmountDisplay())));
            result.add(item);
        }
        return result;
    }

    /**
     * D05 记录详情聚合
     */
    public PcRecordDetailVO detail(PcRequests.RecordDetailQuery query) {
        if (query.getId() == null) {
            throw new ServiceException("参数缺失：id");
        }
        if ("2".equals(query.getType())) {
            return redDetail(query.getId());
        }
        return invoiceDetail(query.getId());
    }

    /**
     * 开票记录详情
     */
    public PcRecordDetailVO invoiceDetail(Long applyId) {
        InvoiceApply apply = invoiceApplyService.selectInvoiceApplyByApplyId(applyId);
        if (apply == null) {
            throw new ServiceException("开票申请不存在");
        }
        PcRecordDetailVO vo = new PcRecordDetailVO();
        vo.setType("1");
        vo.setBizId(applyId);
        vo.setBizNo(apply.getApplyNo());
        vo.setStatus(apply.getApplyStatus());
        vo.setStatusText(PcDict.text(PcDict.APPLY_STATUS, apply.getApplyStatus()));
        vo.setApply(apply);

        InvoiceItem itemQuery = new InvoiceItem();
        itemQuery.setApplyId(applyId);
        vo.setItems(new ArrayList<Object>(invoiceItemService.selectInvoiceItemList(itemQuery)));

        TaxVerify verifyQuery = new TaxVerify();
        verifyQuery.setBizType("1");
        verifyQuery.setBizId(applyId);
        vo.setVerifies(taxVerifyService.selectTaxVerifyList(verifyQuery));

        RpaTask taskQuery = new RpaTask();
        taskQuery.setBizType("1");
        taskQuery.setBizId(applyId);
        vo.setRpaTasks(rpaTaskService.selectRpaTaskList(taskQuery));

        vo.setInvoices(invoiceQueryService.ticketsByApply(applyId));

        AuditRecord auditQuery = new AuditRecord();
        auditQuery.setBizType(AUDIT_BIZ_APPLY);
        auditQuery.setBizId(applyId);
        vo.setAudits(auditRecordService.selectAuditRecordList(auditQuery));

        vo.setAttachments(attachments(BIZ_INVOICE_APPLY, applyId));
        vo.setAllowedActions(invoiceActions(apply.getApplyStatus()));
        vo.setRuleNote(invoiceRuleNote(apply.getApplyStatus()));
        return vo;
    }

    /**
     * 红字记录详情（与原蓝票、确认单同页展示）
     */
    public PcRecordDetailVO redDetail(Long redApplyId) {
        RedApply apply = redApplyService.selectRedApplyByRedApplyId(redApplyId);
        if (apply == null) {
            throw new ServiceException("红字申请不存在");
        }
        PcRecordDetailVO vo = new PcRecordDetailVO();
        vo.setType("2");
        vo.setBizId(redApplyId);
        vo.setBizNo(apply.getRedApplyNo());
        vo.setStatus(apply.getRedStatus());
        vo.setStatusText(PcDict.text(PcDict.RED_STATUS, apply.getRedStatus()));
        vo.setApply(apply);

        if (apply.getOriginalInvoiceId() != null) {
            vo.setOriginalInvoice(invoiceQueryService.ticket(apply.getOriginalInvoiceId()));
        }

        RedItem itemQuery = new RedItem();
        itemQuery.setRedApplyId(redApplyId);
        vo.setItems(new ArrayList<Object>(redItemService.selectRedItemList(itemQuery)));

        TaxVerify verifyQuery = new TaxVerify();
        verifyQuery.setBizType("2");
        verifyQuery.setBizId(redApplyId);
        vo.setVerifies(taxVerifyService.selectTaxVerifyList(verifyQuery));

        RpaTask taskQuery = new RpaTask();
        taskQuery.setBizType("2");
        taskQuery.setBizId(redApplyId);
        vo.setRpaTasks(rpaTaskService.selectRpaTaskList(taskQuery));

        vo.setInvoices(invoiceQueryService.ticketsByRedApply(redApplyId));

        AuditRecord auditQuery = new AuditRecord();
        auditQuery.setBizType(AUDIT_BIZ_RED);
        auditQuery.setBizId(redApplyId);
        vo.setAudits(auditRecordService.selectAuditRecordList(auditQuery));

        ManualResult manualQuery = new ManualResult();
        manualQuery.setBizType(MANUAL_BIZ_RED);
        manualQuery.setBizId(redApplyId);
        vo.setManualResults(manualResultService.selectManualResultList(manualQuery));

        vo.setAttachments(attachments(BIZ_RED_APPLY, redApplyId));
        vo.setRedConfirm(buildConfirm(apply));
        vo.setAllowedActions(redActions(apply));
        vo.setRuleNote(redRuleNote(apply));
        return vo;
    }

    /**
     * 开票审核：1通过（转开票处理中）2要求补充 3不通过（明确失败）
     */
    @Transactional(rollbackFor = Exception.class)
    public void invoiceAudit(PcRequests.RecordAuditRequest request, String auditBy) {
        if (request.getApplyId() == null) {
            throw new ServiceException("参数缺失：applyId");
        }
        InvoiceApply apply = invoiceApplyService.selectInvoiceApplyByApplyId(request.getApplyId());
        if (apply == null) {
            throw new ServiceException("开票申请不存在");
        }
        if (!"0".equals(apply.getApplyStatus()) && !"1".equals(apply.getApplyStatus())) {
            throw new ServiceException("当前开票申请不是待审核状态，无法审核");
        }

        String result = requireResult(request.getResult());
        String opinion = request.getOpinion();
        if (!"1".equals(result) && (opinion == null || opinion.trim().isEmpty())) {
            throw new ServiceException("要求补充或不通过时，审核意见必填");
        }

        Date now = new Date();
        InvoiceApply update = new InvoiceApply();
        update.setApplyId(apply.getApplyId());
        update.setUpdateBy(auditBy);
        update.setUpdateTime(now);

        String title;
        String content;
        if ("1".equals(result)) {
            update.setApplyStatus("2");
            title = "开票申请已受理";
            content = "您的开票申请「" + apply.getApplyNo() + "」审核通过，已进入开票处理。";
        } else if ("2".equals(result)) {
            // 要求补充：保持「已提交」，需补资料桶由 t_audit_record.audit_result='2' 判定（不占用 1=待税务验证）
            update.setApplyStatus("0");
            title = "开票申请需要补充资料";
            content = "您的开票申请「" + apply.getApplyNo() + "」需要补充资料：" + opinion;
        } else {
            update.setApplyStatus("6");
            update.setFailReason(opinion);
            title = "开票申请未通过";
            content = "您的开票申请「" + apply.getApplyNo() + "」未通过：" + opinion;
        }
        invoiceApplyService.updateInvoiceApply(update);

        writeAudit(AUDIT_BIZ_APPLY, apply.getApplyId(), result, opinion,
                request.getBizVersion() == null ? null : request.getBizVersion().longValue(), auditBy, now);
        sendMessage(enterpriseMemberId(apply.getEnterpriseId()), "2", title, content,
                MSG_BIZ_APPLY, apply.getApplyId(), auditBy, now);
    }

    /**
     * 红字处理动作：audit 审核 / confirmStart 发起确认单 / confirmOk 对方已确认 /
     * confirmExpire 确认到期 / stop 停止办理
     */
    @Transactional(rollbackFor = Exception.class)
    public void redHandle(PcRequests.RedHandleRequest request, String auditBy) {
        if (request.getRedApplyId() == null) {
            throw new ServiceException("参数缺失：redApplyId");
        }
        RedApply apply = redApplyService.selectRedApplyByRedApplyId(request.getRedApplyId());
        if (apply == null) {
            throw new ServiceException("红字申请不存在");
        }

        String action = request.getAction();
        if (action == null || action.trim().isEmpty()) {
            throw new ServiceException("参数缺失：action");
        }

        Date now = new Date();
        RedApply update = new RedApply();
        update.setRedApplyId(apply.getRedApplyId());
        update.setUpdateBy(auditBy);
        update.setUpdateTime(now);

        String title;
        String content;

        if ("audit".equals(action)) {
            if (!"0".equals(apply.getRedStatus())) {
                throw new ServiceException("当前红字申请不是待审核状态，无法审核");
            }
            String result = requireResult(request.getResult());
            String opinion = request.getOpinion();
            if (!"1".equals(result) && (opinion == null || opinion.trim().isEmpty())) {
                throw new ServiceException("要求补充或不通过时，审核意见必填");
            }
            if ("1".equals(result)) {
                update.setRedStatus("1");
                title = "红字申请已受理";
                content = "您的红字申请「" + apply.getRedApplyNo() + "」审核通过，等待税务或对方确认。";
            } else if ("2".equals(result)) {
                title = "红字申请需要补充资料";
                content = "您的红字申请「" + apply.getRedApplyNo() + "」需要补充资料：" + opinion;
            } else {
                update.setRedStatus("6");
                update.setFailReason(opinion);
                title = "红字申请未通过";
                content = "您的红字申请「" + apply.getRedApplyNo() + "」未通过：" + opinion;
            }
            writeAudit(AUDIT_BIZ_RED, apply.getRedApplyId(), result, opinion, null, auditBy, now);
        } else if ("confirmStart".equals(action)) {
            // 前置校验：只允许在「已提交/待税务·对方确认」阶段发起，且不可重复发起（72h 窗口不被重置）
            if (!"0".equals(apply.getRedStatus()) && !"1".equals(apply.getRedStatus())) {
                throw new ServiceException("当前红字申请状态不允许发起确认单");
            }
            if ("1".equals(apply.getConfirmStatus())) {
                throw new ServiceException("确认单已发起且仍在有效期内，不能重复发起");
            }
            if ("2".equals(apply.getConfirmStatus())) {
                throw new ServiceException("确认单已确认，不能重复发起");
            }
            int hours = request.getConfirmHours() == null || request.getConfirmHours() <= 0
                    ? DEFAULT_CONFIRM_HOURS : request.getConfirmHours();
            update.setConfirmStatus("1");
            update.setConfirmStartTime(now);
            update.setConfirmExpireTime(new Date(now.getTime() + hours * 3600_000L));
            update.setRedStatus("1");
            title = "红字确认单已发起";
            content = "红字申请「" + apply.getRedApplyNo() + "」确认单已发起，请在 " + hours + " 小时内完成确认。";
        } else if ("confirmOk".equals(action)) {
            if (!"1".equals(apply.getConfirmStatus())) {
                throw new ServiceException("当前红字申请没有待确认的确认单");
            }
            update.setConfirmStatus("2");
            update.setRedStatus("3");
            title = "红字确认单已确认";
            content = "红字申请「" + apply.getRedApplyNo() + "」确认单已确认，进入处理中。";
        } else if ("confirmExpire".equals(action)) {
            if (!"1".equals(apply.getConfirmStatus())) {
                throw new ServiceException("当前没有待确认的确认单，无法置为确认到期");
            }
            update.setConfirmStatus("3");
            update.setRedStatus("2");
            title = "红字确认单已失效";
            content = "红字申请「" + apply.getRedApplyNo() + "」确认单已超时失效，原蓝票不受影响。";
        } else {
            throw new ServiceException("不支持的处理动作：" + action);
        }

        redApplyService.updateRedApply(update);
        sendMessage(redMemberId(apply), "3", title, content, MSG_BIZ_RED, apply.getRedApplyId(), auditBy, now);
    }

    /**
     * 录入实际红票凭证（无自动接口时由授权人员登记，一票一行）
     */
    @Transactional(rollbackFor = Exception.class)
    public Long redVoucher(PcRequests.RedVoucherRequest request, String auditBy) {
        if (request.getRedApplyId() == null) {
            throw new ServiceException("参数缺失：redApplyId");
        }
        if (request.getInvoiceNo() == null || request.getInvoiceNo().trim().isEmpty()) {
            throw new ServiceException("参数缺失：invoiceNo");
        }
        RedApply apply = redApplyService.selectRedApplyByRedApplyId(request.getRedApplyId());
        if (apply == null) {
            throw new ServiceException("红字申请不存在");
        }

        Invoice original = apply.getOriginalInvoiceId() == null ? null
                : invoiceService.selectInvoiceByInvoiceId(apply.getOriginalInvoiceId());
        Date now = new Date();

        Invoice invoice = new Invoice();
        invoice.setInvoiceNo(request.getInvoiceNo().trim());
        invoice.setInvoiceCode(request.getInvoiceCode());
        invoice.setInvoiceKind("2");
        invoice.setApplyId(original == null ? null : original.getApplyId());
        invoice.setRedApplyId(apply.getRedApplyId());
        invoice.setEnterpriseId(original == null ? null : original.getEnterpriseId());
        if (original != null) {
            invoice.setSellerName(original.getSellerName());
            invoice.setSellerTaxNumber(original.getSellerTaxNumber());
            invoice.setBuyerName(original.getBuyerName());
            invoice.setBuyerTaxNumber(original.getBuyerTaxNumber());
        }
        invoice.setTotalAmount(parseAmount(request.getTotalAmount()));
        invoice.setTotalTax(parseAmount(request.getTotalTax()));
        invoice.setTotalPriceTax(parseAmount(request.getTotalPriceTax()));
        invoice.setInvoiceDate(parseDate(request.getInvoiceDate()));
        invoice.setPdfUrl(request.getPdfUrl());
        invoice.setOfdUrl(request.getOfdUrl());
        invoice.setXmlUrl(request.getXmlUrl());
        invoice.setFileStatus(hasText(request.getPdfUrl()) || hasText(request.getOfdUrl())
                || hasText(request.getXmlUrl()) ? "1" : "0");
        invoice.setRedStatus("0");
        invoice.setDelFlag("0");
        invoice.setCreateBy(auditBy);
        invoice.setCreateTime(now);
        invoiceService.insertInvoice(invoice);

        RedApply update = new RedApply();
        update.setRedApplyId(apply.getRedApplyId());
        update.setRedStatus("1".equals(invoice.getFileStatus()) ? "5" : "4");
        update.setUpdateBy(auditBy);
        update.setUpdateTime(now);
        redApplyService.updateRedApply(update);

        writeAudit(AUDIT_BIZ_RED, apply.getRedApplyId(), "1",
                "录入实际红票凭证：" + invoice.getInvoiceNo(), null, auditBy, now);
        sendMessage(redMemberId(apply), "3", "红字发票已登记",
                "红字申请「" + apply.getRedApplyNo() + "」已登记实际红票：" + invoice.getInvoiceNo(),
                MSG_BIZ_RED, apply.getRedApplyId(), auditBy, now);
        return invoice.getInvoiceId();
    }

    /**
     * 红字人工结果录入（只登记实际结果与凭证，不代替对方确认）
     *
     * 设计 D05-R3 只对红字记录开放；蓝字结果未知时按设计只允许查询原 RPA 任务状态。
     */
    @Transactional(rollbackFor = Exception.class)
    public void manualResult(PcRequests.ManualResultRequest request, String auditBy) {
        if (request.getBizId() == null) {
            throw new ServiceException("参数缺失：bizId");
        }
        if (!MANUAL_BIZ_RED.equals(request.getBizType())) {
            throw new ServiceException("人工结果录入只对红字记录开放（bizType 固定为 1）");
        }
        String bizType = MANUAL_BIZ_RED;
        String actualResult = request.getActualResult();
        if (actualResult == null
                || (!"1".equals(actualResult) && !"2".equals(actualResult) && !"9".equals(actualResult))) {
            throw new ServiceException("实际结果不合法（1已交付 2办理失败 9其他）");
        }

        Date now = new Date();

        // 业务主体必须存在：红字申请（并回写状态、通知主体）
        Long memberId;
        String bizNo;
        RedApply red = redApplyService.selectRedApplyByRedApplyId(request.getBizId());
        if (red == null) {
            throw new ServiceException("红字申请不存在");
        }
        bizNo = red.getRedApplyNo();
        memberId = redMemberId(red);

        ManualResult result = new ManualResult();
        result.setBizType(bizType);
        result.setBizId(request.getBizId());
        result.setFillBy(auditBy);
        result.setActualResult(actualResult);
        result.setVoucherUrl(request.getVoucherUrl());
        result.setFillNote(request.getFillNote());
        result.setFillTime(now);
        result.setCreateTime(now);
        manualResultService.insertManualResult(result);

        // 1已交付 / 2办理失败：回写业务状态；9其他只登记留痕，不动状态
        String statusText = PcDict.text(PcDict.MANUAL_RESULT, actualResult);
        if ("1".equals(actualResult) || "2".equals(actualResult)) {
            boolean failed = "2".equals(actualResult);
            RedApply update = new RedApply();
            update.setRedApplyId(request.getBizId());
            update.setRedStatus(failed ? "6" : "5");
            if (failed && hasText(request.getFillNote())) {
                update.setFailReason(request.getFillNote());
            }
            update.setUpdateBy(auditBy);
            update.setUpdateTime(now);
            redApplyService.updateRedApply(update);
        }

        writeAudit(AUDIT_BIZ_RED, request.getBizId(), "1",
                "人工结果录入：" + statusText
                        + (hasText(request.getFillNote()) ? "；" + request.getFillNote() : ""),
                null, auditBy, now);
        sendMessage(memberId, "3",
                "业务结果已更新",
                "红字申请「" + bizNo + "」人工核对结果：" + statusText + "。",
                MSG_BIZ_RED, request.getBizId(), auditBy, now);
    }

    /**
     * 附件清单（受权预览：只给 attachmentId，不给平台内路径）
     */
    public List<PcAttachmentVO> attachments(String bizType, Long bizId) {
        Attachment query = new Attachment();
        query.setBizType(bizType);
        query.setBizId(bizId);
        query.setDelFlag("0");
        return PcAttachments.toVOList(attachmentService.selectAttachmentList(query));
    }

    public List<PcAttachmentVO> attachmentList(PcRequests.AttachmentQuery query) {
        // 单条定位：按 attachmentId 查（任意业务），其余情况必须带 bizType + bizId，
        // 避免条件全空时退化为「列出全平台附件」
        if (query.getAttachmentId() != null) {
            Attachment one = attachmentService.selectAttachmentByAttachmentId(query.getAttachmentId());
            if (one == null || "2".equals(one.getDelFlag())) {
                return new ArrayList<>();
            }
            List<Attachment> single = new ArrayList<>();
            single.add(one);
            return PcAttachments.toVOList(single);
        }
        if (!hasText(query.getBizType()) || query.getBizId() == null) {
            throw new ServiceException("参数缺失：bizType/bizId（或 attachmentId）");
        }
        Attachment condition = new Attachment();
        condition.setBizType(query.getBizType());
        condition.setBizId(query.getBizId());
        condition.setMaterialType(query.getMaterialType());
        condition.setDelFlag("0");
        return PcAttachments.toVOList(attachmentService.selectAttachmentList(condition));
    }

    /**
     * 红字确认单信息（72 小时窗口）
     */
    private PcRecordDetailVO.RedConfirm buildConfirm(RedApply apply) {
        PcRecordDetailVO.RedConfirm confirm = new PcRecordDetailVO.RedConfirm();
        confirm.setConfirmStatus(apply.getConfirmStatus());
        confirm.setConfirmStatusText(PcDict.text(PcDict.CONFIRM_STATUS, apply.getConfirmStatus()));
        confirm.setConfirmStartTime(apply.getConfirmStartTime());
        confirm.setConfirmExpireTime(apply.getConfirmExpireTime());
        if (apply.getConfirmExpireTime() != null) {
            long diff = apply.getConfirmExpireTime().getTime() - System.currentTimeMillis();
            confirm.setRemainingHours(diff / 3600_000L);
            confirm.setExpired(diff <= 0);
        } else {
            confirm.setExpired(Boolean.FALSE);
        }
        return confirm;
    }

    /**
     * 当前登录人是否拥有指定按钮权限（admin 视为全权）。
     * 用于 allowedActions：列表/详情返回的动作按钮与实际可调用的权限保持一致，避免前端点了报 403。
     */
    private boolean hasPerm(String perm) {
        try {
            if (SecurityUtils.isAdmin()) {
                return true;
            }
            return SecurityUtils.hasPermi(perm);
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * D05-S3 补取文件：已有可用文件时直接放行，否则需 RPA 重新取票（当前未接入）
     */
    public void refetchFile(String bizType, Long bizId) {
        PcRecordDetailVO vo = "2".equals(bizType) ? redDetail(bizId) : invoiceDetail(bizId);
        if (vo != null && vo.getInvoices() != null) {
            for (PcInvoiceTicketVO ticket : vo.getInvoices()) {
                if (Boolean.TRUE.equals(ticket.getDownloadable())) {
                    return;
                }
            }
        }
        throw new ServiceException("补取文件需接入 RPA（当前未接入）：文件未就绪时请等待，或由后台人工录入实际红票凭证", 501);
    }

    /**
     * D05-R1 刷新红字确认单实际状态（需 RPA）
     */
    public void refreshRedConfirm(Long redApplyId) {
        RedApply apply = redApplyService.selectRedApplyByRedApplyId(redApplyId);
        if (apply == null) {
            throw new ServiceException("红字申请不存在");
        }
        throw new ServiceException("刷新确认单状态需接入 RPA（当前未接入）：请以后台人工确认结果为准", 501);
    }

    private List<String> invoiceActions(String status) {
        List<String> actions = new ArrayList<>();
        boolean pending = "0".equals(status) || "1".equals(status);
        if (pending && hasPerm("pc:invoice:audit")) {
            actions.add("audit");
        }
        if ("3".equals(status)) {
            actions.add("query");
        }
        if ("4".equals(status) && hasPerm("pc:invoice:handle")) {
            actions.add("refetchFile");
        }
        return actions;
    }

    private String invoiceRuleNote(String status) {
        if ("3".equals(status)) {
            return "结果未知：只允许查询原 RPA 任务状态，禁止重复提交开票。";
        }
        if ("4".equals(status)) {
            return "已开票待文件：只允许补取文件，不重新开票。";
        }
        if ("5".equals(status)) {
            return "已交付完成：金额与税额以票面回传值为准，平台不重算。";
        }
        return null;
    }

    private List<String> redActions(RedApply apply) {
        String status = apply.getRedStatus();
        List<String> actions = new ArrayList<>();
        boolean canHandle = hasPerm("pc:red:handle");
        if ("0".equals(status) && canHandle) {
            actions.add("audit");
            actions.add("confirmStart");
        }
        if ("1".equals(status) && canHandle) {
            actions.add("confirmOk");
            actions.add("confirmExpire");
            actions.add("refreshConfirm");
        }
        if ("2".equals(status) && canHandle) {
            actions.add("confirmExpire");
        }
        if ("3".equals(status)) {
            actions.add("query");
        }
        if ("4".equals(status) && canHandle) {
            actions.add("redVoucher");
        }
        return actions;
    }

    private String redRuleNote(RedApply apply) {
        if ("1".equals(apply.getConfirmStatus())) {
            return "确认单有效期为发起后 72 小时，超时只核对实际状态，不盲目重新发起；"
                    + "确认单失效不代表原蓝票失效，重开蓝票需另行提交申请。";
        }
        if ("3".equals(apply.getRedStatus())) {
            return "结果未知：只允许查询原任务状态，不重新发起红字办理。";
        }
        return null;
    }

    private String requireResult(String result) {
        if (result == null || (!"1".equals(result) && !"2".equals(result) && !"3".equals(result))) {
            throw new ServiceException("审核结果不合法（1通过 2要求补充 3不通过）");
        }
        return result;
    }

    private void writeAudit(String bizType, Long bizId, String result, String opinion,
                            Long bizVersion, String auditBy, Date now) {
        AuditRecord record = new AuditRecord();
        record.setBizType(bizType);
        record.setBizId(bizId);
        record.setAuditBy(auditBy);
        record.setAuditResult(result);
        record.setAuditOpinion(opinion);
        record.setAuditTime(now);
        if (bizVersion != null) {
            record.setBizVersion(bizVersion);
        }
        record.setCreateBy(auditBy);
        record.setCreateTime(now);
        auditRecordService.insertAuditRecord(record);
    }

    private void sendMessage(Long memberId, String messageType, String title, String content,
                             String bizType, Long bizId, String createBy, Date now) {
        if (memberId == null) {
            return;
        }
        Message message = new Message();
        message.setMemberId(memberId);
        message.setMessageType(messageType);
        message.setTitle(title);
        message.setContent(content);
        message.setBizType(bizType);
        message.setBizId(bizId);
        message.setIsRead("0");
        message.setDelFlag("0");
        message.setCreateBy(createBy);
        message.setCreateTime(now);
        messageService.insertMessage(message);
    }

    private Long enterpriseMemberId(Long enterpriseId) {
        if (enterpriseId == null) {
            return null;
        }
        Enterprise enterprise = enterpriseService.selectEnterpriseByEnterpriseId(enterpriseId);
        return enterprise == null ? null : enterprise.getMemberId();
    }

    private Long redMemberId(RedApply apply) {
        if (apply.getOriginalInvoiceId() == null) {
            return null;
        }
        Invoice original = invoiceService.selectInvoiceByInvoiceId(apply.getOriginalInvoiceId());
        return original == null ? null : enterpriseMemberId(original.getEnterpriseId());
    }

    private BigDecimal parseAmount(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException ex) {
            throw new ServiceException("金额格式不正确：" + value);
        }
    }

    /**
     * 只读展示用的宽松金额解析：非数字一律降级为 null（展示为「待返回」），不抛异常
     */
    private BigDecimal parseAmountLenient(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private Date parseDate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(value.trim());
        } catch (ParseException ex) {
            throw new ServiceException("日期格式不正确（应为 yyyy-MM-dd）：" + value);
        }
    }

    private String fileNameOf(String url) {
        int index = url.lastIndexOf('/');
        return index < 0 ? url : url.substring(index + 1);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
