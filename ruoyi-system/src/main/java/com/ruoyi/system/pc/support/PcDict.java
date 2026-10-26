package com.ruoyi.system.pc.support;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * PC 后台状态字典与展示口径
 *
 * 口径说明（对应需求 AC20/AC29）：金额一律展示票面回传值，平台不重算；
 * 未返回（null）统一显示「待返回」，不显示 0，避免被误读为真实金额。
 */
public final class PcDict {

    private PcDict() {
    }

    /** 开票申请状态（t_invoice_apply.apply_status） */
    public static final Map<String, String> APPLY_STATUS = of(
            "0", "已提交", "1", "待税务验证", "2", "开票处理中", "3", "结果核对中",
            "4", "已开票待文件", "5", "交付完成", "6", "明确失败", "7", "停止处理中");

    /** 红字申请状态（t_red_apply.red_status） */
    public static final Map<String, String> RED_STATUS = of(
            "0", "已提交", "1", "待税务/对方确认", "2", "确认到期", "3", "处理中",
            "4", "已开红票待文件", "5", "已交付", "6", "明确失败", "7", "已停止");

    /** 红字确认单状态（t_red_apply.confirm_status） */
    public static final Map<String, String> CONFIRM_STATUS = of(
            "0", "未发起", "1", "已发起待确认", "2", "已确认", "3", "已失效");

    /** 主体认证状态（t_enterprise.auth_status）：业务口径只有三态——已认证 / 核验中 / 认证不通过 */
    public static final Map<String, String> AUTH_STATUS = of(
            "0", "核验中", "1", "核验中", "2", "已认证", "3", "认证不通过");

    /** 注册完成状态（t_reg_apply.complete_status） */
    public static final Map<String, String> REG_COMPLETE_STATUS = of(
            "0", "待完成", "1", "已完成");

    /** 主体类型 */
    public static final Map<String, String> SUBJECT_TYPE = of(
            "1", "有限责任公司", "2", "个体工商户");

    /** 票种（开票申请 invoice_type：普票/专票；此处为 PC 展示简称，DB 注释为「增值税普通发票/增值税专用发票」全称） */
    public static final Map<String, String> INVOICE_TYPE = of(
            "1", "普通发票", "2", "专用发票");

    /** 票性质（t_invoice.invoice_kind：蓝字/红字） */
    public static final Map<String, String> INVOICE_KIND = of(
            "1", "蓝字发票", "2", "红字发票");

    /** 发票红冲状态（t_invoice.red_status） */
    public static final Map<String, String> INVOICE_RED_FLAG = of(
            "0", "未红冲", "1", "部分红冲", "2", "全额红冲");

    /** 发票文件状态（t_invoice.file_status） */
    public static final Map<String, String> FILE_STATUS = of(
            "0", "待文件", "1", "已获取", "2", "获取失败");

    /** RPA 任务状态（t_rpa_task.task_status） */
    public static final Map<String, String> RPA_STATUS = of(
            "0", "待下发", "1", "待用户验证", "2", "执行中", "3", "结果待核对",
            "4", "已开票待文件", "5", "已完成", "6", "明确失败", "7", "已停止");

    /** 税务验证状态（t_tax_verify.verify_status） */
    public static final Map<String, String> VERIFY_STATUS = of(
            "0", "已发起待验证", "1", "已提交待填", "2", "验证通过", "3", "验证失败/过期");

    /** 红字原因（t_red_apply.red_reason） */
    public static final Map<String, String> RED_REASON = of(
            "1", "开票有误", "2", "退货折让", "3", "服务中止", "9", "其他");

    /** 红字范围（t_red_apply.red_range） */
    public static final Map<String, String> RED_RANGE = of(
            "1", "全额红冲", "2", "部分红冲");

    /** 人工回填实际结果（t_manual_result.actual_result） */
    public static final Map<String, String> MANUAL_RESULT = of(
            "1", "已交付", "2", "办理失败", "9", "其他");

    /** 待办类型（D01 四类待办） */
    public static final Map<String, String> TODO_TYPE = of(
            "audit", "待审核", "material", "需补资料", "user", "待用户操作",
            "unknown", "结果未知");

    /** 待办状态字典来源 */
    public static final Map<String, String> STATUS_KIND = of(
            "apply", "开票状态", "red", "红字状态", "rpa", "RPA 任务状态",
            "verify", "税务验证状态", "file", "文件状态");

    /** 统一记录类型（D04） */
    public static final Map<String, String> RECORD_TYPE = of(
            "1", "开票记录", "2", "红字记录");

    /** 审核结论（t_audit_record.audit_result） */
    public static final Map<String, String> AUDIT_RESULT = of(
            "1", "通过", "2", "要求补充", "3", "不通过");

    /** 材料类型中文名（与小程序材料码一致） */
    public static final Map<String, String> MATERIAL = of(
            "BUSINESS_LICENSE", "营业执照",
            "LEGAL_PERSON_ID_CARD_FRONT", "法人身份证正面",
            "LEGAL_PERSON_ID_CARD_BACK", "法人身份证反面",
            "SHAREHOLDER_ID_CARD_FRONT", "股东身份证正面",
            "SHAREHOLDER_ID_CARD_BACK", "股东身份证反面",
            "ORG_SHAREHOLDER_QUALIFICATION", "机构股东资格证明",
            "OPERATOR_ID_CARD_FRONT", "经营者身份证正面",
            "OPERATOR_ID_CARD_BACK", "经营者身份证反面",
            "RESIDENCE_PROOF", "住所证明",
            "KINSHIP_PROOF", "亲属关系证明",
            "PREMISES_PROOF", "经营场所证明",
            "PROXY_BOOK", "授权委托书",
            "DEAL_PROOF", "交易凭证",
            "OTHER_PERMIT", "其他许可材料");

    /**
     * 取字典文本，未命中返回原值
     */
    public static String text(Map<String, String> dict, String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        String text = dict.get(code);
        return text == null ? code : text;
    }

    /**
     * 待办状态文本（按 statusKind 选择字典，避免不同业务状态码同值异义）
     */
    public static String todoStatusText(String statusKind, String status) {
        if (statusKind == null) {
            return status;
        }
        switch (statusKind) {
            case "apply":
                return text(APPLY_STATUS, status);
            case "red":
                return text(RED_STATUS, status);
            case "rpa":
                return text(RPA_STATUS, status);
            case "verify":
                return text(VERIFY_STATUS, status);
            case "file":
                return text(FILE_STATUS, status);
            default:
                return status;
        }
    }

    /**
     * 金额展示：票面回传值保留两位；未返回显示「待返回」
     */
    public static String amount(BigDecimal value) {
        if (value == null) {
            return "待返回";
        }
        return value.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    private static Map<String, String> of(String... kv) {
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i + 1 < kv.length; i += 2) {
            map.put(kv[i], kv[i + 1]);
        }
        return Collections.unmodifiableMap(map);
    }
}
