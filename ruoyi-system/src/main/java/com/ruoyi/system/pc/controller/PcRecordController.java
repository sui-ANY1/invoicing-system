package com.ruoyi.system.pc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.pc.dto.PcRequests;
import com.ruoyi.system.pc.service.PcRecordService;
import com.ruoyi.system.pc.support.PcPage;
import com.ruoyi.system.pc.support.PcPages;
import com.ruoyi.system.pc.support.PcResult;
import com.ruoyi.system.pc.vo.PcAttachmentVO;
import com.ruoyi.system.pc.vo.PcCreatedVO;
import com.ruoyi.system.pc.vo.PcRecordDetailVO;
import com.ruoyi.system.pc.vo.PcRecordItemVO;

/**
 * PC 开票红字统一记录（D04 列表 / D05 详情与处理动作）
 *
 * 路径中的 bizType：1 开票申请 / 2 红字申请，与列表 type 同口径；bizId 为对应申请 id。
 *
 * 硬规则：结果未知只查询、已开票只补取文件、不重新开票；红字确认单 72 小时；
 * 所有写动作留痕 t_audit_record / t_manual_result 并触发站内消息。
 */
@Tag(name = "记录与处理", description = "D04 开票红字统一记录列表、D05 记录详情与处理动作（GET 查询 + POST 子资源动作）")
@RestController
@RequestMapping("/pc/records")
public class PcRecordController extends BaseController {

    /** 路径 bizType：1 开票申请 */
    private static final String BIZ_TYPE_INVOICE = "1";

    /** 路径 bizType：2 红字申请 */
    private static final String BIZ_TYPE_RED = "2";

    /** 附件表业务类型：开票申请 */
    private static final String ATTACH_BIZ_INVOICE = "invoice_apply";

    /** 附件表业务类型：红字申请 */
    private static final String ATTACH_BIZ_RED = "red_apply";

    @Autowired
    private PcRecordService pcRecordService;

    /**
     * D04 统一记录分页列表（开票 + 红字合并，按类型/状态/企业/购方/票种/票号/时间筛选）
     */
    @Operation(summary = "查询业务记录列表",
            description = "GET /pc/records：D04 开票 + 红字统一记录分页列表，query 参数支持类型（blue/red）/状态/企业/购方/"
                    + "票种/票号/时间筛选，一条记录一张票，分页参数 pageNum / pageSize 走 query")
    @PreAuthorize("@ss.hasPermi('pc:record:list')")
    @GetMapping
    public PcResult<PcPage<PcRecordItemVO>> list(@ModelAttribute PcRequests.RecordQuery query) {
        PcPages.start(query);
        List<PcRecordItemVO> list = pcRecordService.list(query);
        return PcResult.ok(PcPage.of(list));
    }

    /**
     * D05 记录详情聚合（申请 / 明细 / 验证 / RPA 任务 / 发票 / 审核 / 人工结果 / 附件）
     */
    @Operation(summary = "查询记录详情",
            description = "GET /pc/records/detail：query 参数 bizType（1开票 2红字）+ bizId（开票为 applyId，红字为 redApplyId）；"
                    + "返回申请 + 明细 + 税务验证 + RPA 任务 + 发票 + 审核留痕 + 人工结果 + 附件清单")
    @PreAuthorize("@ss.hasPermi('pc:record:query')")
    @GetMapping("/detail")
    public PcResult<PcRecordDetailVO> detail(@RequestParam("bizType") String bizType,
            @RequestParam("bizId") Long bizId) {
        requireRecordBizType(bizType);
        PcRequests.RecordDetailQuery query = new PcRequests.RecordDetailQuery();
        query.setType(bizType);
        query.setId(bizId);
        return PcResult.ok(pcRecordService.detail(query));
    }

    /**
     * D05 开票审核：1通过 2要求补充 3不通过（写审核留痕 + 站内消息）
     */
    @Operation(summary = "开票审核",
            description = "POST /pc/records/audits：D05 开票审核，body 传 applyId + result（1通过 / 2要求补充 / 3不通过）"
                    + "及审核意见；写审核留痕并触发站内消息")
    @PreAuthorize("@ss.hasPermi('pc:invoice:audit')")
    @Log(title = "开票审核", businessType = BusinessType.UPDATE)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/audits")
    public PcResult<Void> audit(@RequestBody PcRequests.RecordAuditRequest request) {
        if (request.getApplyId() == null) {
            throw new ServiceException("参数缺失：applyId");
        }
        pcRecordService.invoiceAudit(request, getUsername());
        return PcResult.okMsg("审核完成");
    }

    /**
     * D05-R3 红字处理：audit 审核 / confirmStart 发起确认单 / confirmOk 对方确认 /
     * confirmExpire 确认到期 / stop 停止办理
     */
    @Operation(summary = "红字处理",
            description = "POST /pc/records/red-handlings：D05-R1/R3 红字处理动作，body 传 redApplyId + action = audit 审核 / "
                    + "confirmStart 发起确认单（72 小时有效） / confirmOk 对方确认 / confirmExpire 确认到期")
    @PreAuthorize("@ss.hasPermi('pc:red:handle')")
    @Log(title = "红字处理", businessType = BusinessType.UPDATE)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/red-handlings")
    public PcResult<Void> redHandle(@RequestBody PcRequests.RedHandleRequest request) {
        if (request.getRedApplyId() == null) {
            throw new ServiceException("参数缺失：redApplyId");
        }
        pcRecordService.redHandle(request, getUsername());
        return PcResult.okMsg("处理完成");
    }

    /**
     * D05-R3 录入实际红票凭证（无自动接口时由授权人员登记，一票一行）
     */
    @Operation(summary = "录入红字凭证",
            description = "POST /pc/records/red-vouchers：D05-R3 录入实际红票凭证，body 传 redApplyId，无自动接口时由授权人员登记，"
                    + "一票一行，返回新登记的发票 ID")
    @PreAuthorize("@ss.hasPermi('pc:red:handle')")
    @Log(title = "红票凭证录入", businessType = BusinessType.INSERT)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/red-vouchers")
    public PcResult<PcCreatedVO> redVoucher(@RequestBody PcRequests.RedVoucherRequest request) {
        if (request.getRedApplyId() == null) {
            throw new ServiceException("参数缺失：redApplyId");
        }
        Long invoiceId = pcRecordService.redVoucher(request, getUsername());
        return PcResult.ok(PcCreatedVO.ofInvoice(invoiceId));
    }

    /**
     * D05-R3 红字人工结果录入（1已交付 2办理失败 9其他，留痕 t_manual_result）
     */
    @Operation(summary = "录入红字人工结果",
            description = "POST /pc/records/manual-results：D05-R3 红字人工结果录入，body 传 bizType（服务口径 1红字）+ bizId + "
                    + "actualResult（1已交付 / 2办理失败 / 9其他）；回写红字状态、写留痕并触发站内消息（蓝字记录不支持，按设计只查原 RPA 任务）")
    @PreAuthorize("@ss.hasPermi('pc:manual:save')")
    @Log(title = "红字人工结果录入", businessType = BusinessType.INSERT)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/manual-results")
    public PcResult<Void> manualResult(@RequestBody PcRequests.ManualResultRequest request) {
        if (request.getBizId() == null) {
            throw new ServiceException("参数缺失：bizId");
        }
        pcRecordService.manualResult(request, getUsername());
        return PcResult.okMsg("录入完成");
    }

    /**
     * D05-S3 补取文件（已有票只补取文件，不重新开票）
     */
    @Operation(summary = "补取发票文件",
            description = "POST /pc/records/file-fetches：D05-S3 补取文件，body 传 bizType（1开票 2红字）+ bizId；"
                    + "已有可用文件时直接返回成功提示，未就绪需由 RPA 重新取票（当前 RPA 未接入，返回 501）")
    @PreAuthorize("@ss.hasPermi('pc:invoice:handle')")
    @Log(title = "补取发票文件", businessType = BusinessType.UPDATE)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/file-fetches")
    public PcResult<Void> refetchFile(@RequestBody PcRequests.RecordIdRequest request) {
        if (request.getBizId() == null) {
            throw new ServiceException("参数缺失：bizId");
        }
        requireRecordBizType(request.getBizType());
        pcRecordService.refetchFile(request.getBizType(), request.getBizId());
        return PcResult.okMsg("文件已可用");
    }

    /**
     * D05-R1 刷新红字确认单实际状态
     */
    @Operation(summary = "刷新红字确认单状态",
            description = "POST /pc/records/confirm-refreshes：D05-R1 刷新确认单实际状态，body 传 bizType（固定 2）+ bizId；"
                    + "当前 RPA 未接入，返回 501")
    @PreAuthorize("@ss.hasPermi('pc:red:handle')")
    @Log(title = "刷新红字确认单", businessType = BusinessType.UPDATE)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/confirm-refreshes")
    public PcResult<Void> refreshRedConfirm(@RequestBody PcRequests.RecordIdRequest request) {
        if (request.getBizId() == null) {
            throw new ServiceException("参数缺失：bizId");
        }
        requireBizType(request.getBizType(), BIZ_TYPE_RED, "刷新确认单仅支持红字申请（bizType=2）");
        pcRecordService.refreshRedConfirm(request.getBizId());
        return PcResult.okMsg("刷新完成");
    }

    /**
     * D05 记录附件清单（受权预览按 attachmentId，不返回平台路径）
     */
    @Operation(summary = "查询记录附件",
            description = "GET /pc/records/attachments：query 参数 bizType（1开票 2红字）+ bizId，可选 materialType 按材料码过滤；"
                    + "不返回平台路径")
    @PreAuthorize("@ss.hasPermi('pc:record:query')")
    @GetMapping("/attachments")
    public PcResult<List<PcAttachmentVO>> attachments(@RequestParam("bizType") String bizType,
            @RequestParam("bizId") Long bizId,
            @RequestParam(value = "materialType", required = false) String materialType) {
        requireRecordBizType(bizType);
        PcRequests.AttachmentQuery query = new PcRequests.AttachmentQuery();
        query.setBizType(toAttachmentBizType(bizType));
        query.setBizId(bizId);
        query.setMaterialType(materialType);
        return PcResult.ok(pcRecordService.attachmentList(query));
    }

    /**
     * 校验路径 bizType 只能是 1 开票 / 2 红字
     */
    private static void requireRecordBizType(String bizType) {
        if (!BIZ_TYPE_INVOICE.equals(bizType) && !BIZ_TYPE_RED.equals(bizType)) {
            throw new ServiceException("bizType 不合法（1开票 / 2红字）");
        }
    }

    /**
     * 校验路径 bizType 与动作适用的业务类型一致，避免把红字 id 当开票 id 处理（或相反）
     */
    private static void requireBizType(String bizType, String expected, String message) {
        if (!expected.equals(bizType)) {
            throw new ServiceException(message);
        }
    }

    /**
     * 路径 bizType（1 开票 / 2 红字）转附件表业务类型（invoice_apply / red_apply）
     */
    private static String toAttachmentBizType(String bizType) {
        return BIZ_TYPE_RED.equals(bizType) ? ATTACH_BIZ_RED : ATTACH_BIZ_INVOICE;
    }
}
