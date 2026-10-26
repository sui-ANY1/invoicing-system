package com.ruoyi.system.mini.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 小程序通用材料（附件）提交项
 *
 * <p>先调 /api/mini/file/upload 取得 fileUrl 或 attachmentId，再随业务提交一起回传；
 * 后端按材料类型码落 t_attachment，供受控预览与 PC 侧只读查看。</p>
 */
@Schema(description = "材料（附件）提交项")
public class MaterialForm {

    @Schema(description = "材料类型码：BUSINESS_LICENSE 营业执照、LEGAL_PERSON_ID_CARD_FRONT/BACK 法人身份证正反面、"
            + "SHAREHOLDER_ID_CARD_FRONT/BACK 自然人股东身份证正反面、ORG_SHAREHOLDER_QUALIFICATION 机构股东资格材料、"
            + "OPERATOR_ID_CARD_FRONT/BACK 经办人身份证正反面、RESIDENCE_PROOF 住所证明、KINSHIP_PROOF 亲属关系证明、"
            + "PREMISES_PROOF 场所使用证明、PROXY_BOOK 受托委托材料、DEAL_PROOF 交易证明（合同/订单）、"
            + "OTHER_PERMIT 其他许可／联络资料",
            example = "LEGAL_PERSON_ID_CARD_FRONT", requiredMode = Schema.RequiredMode.REQUIRED)
    private String materialType;

    @Schema(description = "上传接口返回的文件访问路径", example = "/profile/upload/2026/10/14/front_xxx.png")
    private String fileUrl;

    @Schema(description = "上传接口返回的附件ID（与 fileUrl 二选一，同时传时以 attachmentId 为准）", example = "12")
    private Long attachmentId;

    @Schema(description = "原始文件名（可选，缺省时以上传登记的文件名为准）", example = "front.png")
    private String fileName;

    @Schema(description = "关联对象键（同一业务下多份同类材料的人员标识，如股东/经办人姓名）", example = "张三")
    private String refKey;

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRefKey() {
        return refKey;
    }

    public void setRefKey(String refKey) {
        this.refKey = refKey;
    }
}
