package com.ruoyi.system.mini.constant;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 小程序附件业务类型与材料类型常量
 *
 * <p>材料类型码按小程序功能设计（P14 注册信息采集 / P16 已提交注册资料）的“适用材料”归集，
 * 落库到 t_attachment.material_type；同一业务下多份同类材料（如多名股东的身份证）用 ref_key 区分。</p>
 *
 * @author sui-ANY1
 */
public class MiniAttachConstants {

    /** 业务类型：未关联业务的临时上传（表单提交时回填为具体业务） */
    public static final String BIZ_TEMP = "temp";

    /** 业务类型：注册收件 */
    public static final String BIZ_REG_APPLY = "reg_apply";

    /** 业务类型：开票企业主体 */
    public static final String BIZ_ENTERPRISE = "enterprise";

    /** 业务类型：开票申请 */
    public static final String BIZ_INVOICE_APPLY = "invoice_apply";

    /** 业务类型：红字申请 */
    public static final String BIZ_RED_APPLY = "red_apply";

    /** 上传接口允许的业务类型（防止前端写入任意业务类型） */
    private static final Set<String> ALLOWED_BIZ_TYPES = new LinkedHashSet<>();

    static {
        ALLOWED_BIZ_TYPES.add(BIZ_TEMP);
        ALLOWED_BIZ_TYPES.add(BIZ_REG_APPLY);
        ALLOWED_BIZ_TYPES.add(BIZ_ENTERPRISE);
        ALLOWED_BIZ_TYPES.add(BIZ_INVOICE_APPLY);
        ALLOWED_BIZ_TYPES.add(BIZ_RED_APPLY);
    }

    /** 材料类型：营业执照 */
    public static final String BUSINESS_LICENSE = "BUSINESS_LICENSE";
    /** 材料类型：法人（法定代表人）身份证正面 */
    public static final String LEGAL_PERSON_ID_CARD_FRONT = "LEGAL_PERSON_ID_CARD_FRONT";
    /** 材料类型：法人身份证反面 */
    public static final String LEGAL_PERSON_ID_CARD_BACK = "LEGAL_PERSON_ID_CARD_BACK";
    /** 材料类型：自然人股东身份证正面 */
    public static final String SHAREHOLDER_ID_CARD_FRONT = "SHAREHOLDER_ID_CARD_FRONT";
    /** 材料类型：自然人股东身份证反面 */
    public static final String SHAREHOLDER_ID_CARD_BACK = "SHAREHOLDER_ID_CARD_BACK";
    /** 材料类型：机构股东资格材料 */
    public static final String ORG_SHAREHOLDER_QUALIFICATION = "ORG_SHAREHOLDER_QUALIFICATION";
    /** 材料类型：经营者身份证正面 */
    public static final String OPERATOR_ID_CARD_FRONT = "OPERATOR_ID_CARD_FRONT";
    /** 材料类型：经营者身份证反面 */
    public static final String OPERATOR_ID_CARD_BACK = "OPERATOR_ID_CARD_BACK";
    /** 材料类型：住所证明 */
    public static final String RESIDENCE_PROOF = "RESIDENCE_PROOF";
    /** 材料类型：亲属关系证明（家庭经营） */
    public static final String KINSHIP_PROOF = "KINSHIP_PROOF";
    /** 材料类型：场所使用证明 */
    public static final String PREMISES_PROOF = "PREMISES_PROOF";
    /** 材料类型：受托委托材料 */
    public static final String PROXY_BOOK = "PROXY_BOOK";
    /** 材料类型：交易证明（合同/订单） */
    public static final String DEAL_PROOF = "DEAL_PROOF";
    /** 材料类型：其他许可／联络资料 */
    public static final String OTHER_PERMIT = "OTHER_PERMIT";

    /** 有限公司允许的材料类型 */
    private static final Set<String> COMPANY_MATERIALS = new LinkedHashSet<>();
    /** 个体工商户允许的材料类型 */
    private static final Set<String> SOLE_MATERIALS = new LinkedHashSet<>();
    /** 主体认证（企业/个体户主体资料）允许的材料类型 */
    private static final Set<String> ENTERPRISE_MATERIALS = new LinkedHashSet<>();
    /** 开票申请允许的材料类型 */
    private static final Set<String> INVOICE_MATERIALS = new LinkedHashSet<>();
    /** 红字申请允许的材料类型 */
    private static final Set<String> RED_MATERIALS = new LinkedHashSet<>();
    /** 有限公司必传材料类型 */
    private static final Set<String> COMPANY_REQUIRED = new LinkedHashSet<>();
    /** 个体工商户必传材料类型 */
    private static final Set<String> SOLE_REQUIRED = new LinkedHashSet<>();

    static {
        COMPANY_MATERIALS.add(BUSINESS_LICENSE);
        COMPANY_MATERIALS.add(LEGAL_PERSON_ID_CARD_FRONT);
        COMPANY_MATERIALS.add(LEGAL_PERSON_ID_CARD_BACK);
        COMPANY_MATERIALS.add(SHAREHOLDER_ID_CARD_FRONT);
        COMPANY_MATERIALS.add(SHAREHOLDER_ID_CARD_BACK);
        COMPANY_MATERIALS.add(ORG_SHAREHOLDER_QUALIFICATION);
        COMPANY_MATERIALS.add(OTHER_PERMIT);

        SOLE_MATERIALS.add(OPERATOR_ID_CARD_FRONT);
        SOLE_MATERIALS.add(OPERATOR_ID_CARD_BACK);
        SOLE_MATERIALS.add(RESIDENCE_PROOF);
        SOLE_MATERIALS.add(KINSHIP_PROOF);
        SOLE_MATERIALS.add(PREMISES_PROOF);
        SOLE_MATERIALS.add(PROXY_BOOK);
        SOLE_MATERIALS.add(OTHER_PERMIT);

        ENTERPRISE_MATERIALS.add(BUSINESS_LICENSE);
        ENTERPRISE_MATERIALS.add(LEGAL_PERSON_ID_CARD_FRONT);
        ENTERPRISE_MATERIALS.add(LEGAL_PERSON_ID_CARD_BACK);
        ENTERPRISE_MATERIALS.add(OPERATOR_ID_CARD_FRONT);
        ENTERPRISE_MATERIALS.add(OPERATOR_ID_CARD_BACK);
        ENTERPRISE_MATERIALS.add(PROXY_BOOK);
        ENTERPRISE_MATERIALS.add(ORG_SHAREHOLDER_QUALIFICATION);
        ENTERPRISE_MATERIALS.add(OTHER_PERMIT);

        INVOICE_MATERIALS.add(DEAL_PROOF);
        INVOICE_MATERIALS.add(BUSINESS_LICENSE);
        INVOICE_MATERIALS.add(OTHER_PERMIT);

        RED_MATERIALS.add(DEAL_PROOF);
        RED_MATERIALS.add(OTHER_PERMIT);

        COMPANY_REQUIRED.add(LEGAL_PERSON_ID_CARD_FRONT);
        COMPANY_REQUIRED.add(LEGAL_PERSON_ID_CARD_BACK);

        SOLE_REQUIRED.add(OPERATOR_ID_CARD_FRONT);
        SOLE_REQUIRED.add(OPERATOR_ID_CARD_BACK);
    }

    private MiniAttachConstants() {
    }

    /**
     * 是否为已知材料类型码（上传接口在不知道主体类型时只做“是否为平台材料码”的校验）
     */
    public static boolean isKnownMaterial(String materialType) {
        return materialType != null
                && (COMPANY_MATERIALS.contains(materialType) || SOLE_MATERIALS.contains(materialType)
                        || ENTERPRISE_MATERIALS.contains(materialType) || INVOICE_MATERIALS.contains(materialType)
                        || RED_MATERIALS.contains(materialType));
    }

    public static boolean isAllowedBizType(String bizType) {
        return bizType != null && ALLOWED_BIZ_TYPES.contains(bizType);
    }

    /**
     * 按业务类型校验材料类型是否可用
     *
     * @param bizType 业务类型
     * @param subjectType 主体类型（仅注册收件需要：1有限公司 2个体工商户，其余可为 null）
     * @param materialType 材料类型码
     */
    public static boolean isAllowedMaterial(String bizType, String subjectType, String materialType) {
        if (materialType == null) {
            return false;
        }
        if (BIZ_REG_APPLY.equals(bizType)) {
            return isAllowedRegMaterial(subjectType, materialType);
        }
        if (BIZ_ENTERPRISE.equals(bizType)) {
            return ENTERPRISE_MATERIALS.contains(materialType);
        }
        if (BIZ_INVOICE_APPLY.equals(bizType)) {
            return INVOICE_MATERIALS.contains(materialType);
        }
        if (BIZ_RED_APPLY.equals(bizType)) {
            return RED_MATERIALS.contains(materialType);
        }
        return isKnownMaterial(materialType);
    }

    /**
     * 校验注册材料类型是否属于该主体类型
     *
     * @param subjectType 主体类型（1有限公司 2个体工商户）
     * @param materialType 材料类型码
     */
    public static boolean isAllowedRegMaterial(String subjectType, String materialType) {
        if (materialType == null) {
            return false;
        }
        return "2".equals(subjectType) ? SOLE_MATERIALS.contains(materialType)
                : COMPANY_MATERIALS.contains(materialType);
    }

    /**
     * 该主体类型的必传材料类型
     */
    public static Set<String> requiredRegMaterials(String subjectType) {
        return "2".equals(subjectType) ? SOLE_REQUIRED : COMPANY_REQUIRED;
    }
}
