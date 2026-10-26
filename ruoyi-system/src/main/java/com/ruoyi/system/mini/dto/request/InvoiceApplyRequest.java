package com.ruoyi.system.mini.dto.request;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "开票申请提交请求")
public class InvoiceApplyRequest {

    @Schema(description = "开票申请主信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "开票信息不能为空")
    @Valid
    private InvoiceApplyForm apply;

    @Schema(description = "开票明细列表（至少 1 条）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "至少填写一条开票明细")
    @Valid
    private List<InvoiceItemForm> items;

    @Schema(description = "补充材料清单（可选；交易证明也可直接放在 apply.dealProof）")
    private List<MaterialForm> materials;

    public InvoiceApplyForm getApply() {
        return apply;
    }

    public void setApply(InvoiceApplyForm apply) {
        this.apply = apply;
    }

    public List<MaterialForm> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialForm> materials) {
        this.materials = materials;
    }

    public List<InvoiceItemForm> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemForm> items) {
        this.items = items;
    }

    @Schema(description = "开票主信息")
    public static class InvoiceApplyForm {

        @Schema(description = "销售主体ID（须为本人已核验通过的企业，P05 必填）", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "请选择销售主体")
        private Long enterpriseId;

        @Schema(description = "发票类型：1普通发票 2增值税专用发票（P05 必填）", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "发票类型不能为空（1普通发票 2增值税专用发票）")
        @Pattern(regexp = "^[12]$", message = "发票类型只能是 1 普通发票 或 2 增值税专用发票")
        private String invoiceType;

        @Schema(description = "购买方类型：1企业/组织 2个人（P05 必填）", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "购买方类型不能为空（1企业/组织 2个人）")
        @Pattern(regexp = "^[12]$", message = "购买方类型只能是 1 企业/组织 或 2 个人")
        private String buyerType;

        @Schema(description = "购买方名称/姓名（P05 必填）", example = "某某市某某科技有限公司",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "购买方名称不能为空")
        private String buyerName;

        @Schema(description = "购买方税号（购买方为企业/组织时必填，个人不填）", example = "91320500MA123456XY",
                requiredMode = Schema.RequiredMode.REQUIRED)
        private String buyerTaxNumber;

        @Schema(description = "购买方地址、电话（专票建议填写）", example = "某某市工业园区XX路 0512-88888888")
        private String buyerAddressPhone;

        @Schema(description = "购买方开户行及账号（专票建议填写）", example = "中国银行某某市分行 1234567890")
        private String buyerBank;

        @Schema(description = "交易证明（合同/订单）附件URL", example = "/profile/upload/2026/10/14/contract.png")
        private String dealProof;

        @Schema(description = "发票接收邮箱", example = "buyer@example.com")
        private String receiveEmail;

        public Long getEnterpriseId() {
            return enterpriseId;
        }

        public void setEnterpriseId(Long enterpriseId) {
            this.enterpriseId = enterpriseId;
        }

        public String getInvoiceType() {
            return invoiceType;
        }

        public void setInvoiceType(String invoiceType) {
            this.invoiceType = invoiceType;
        }

        public String getBuyerType() {
            return buyerType;
        }

        public void setBuyerType(String buyerType) {
            this.buyerType = buyerType;
        }

        public String getBuyerName() {
            return buyerName;
        }

        public void setBuyerName(String buyerName) {
            this.buyerName = buyerName;
        }

        public String getBuyerTaxNumber() {
            return buyerTaxNumber;
        }

        public void setBuyerTaxNumber(String buyerTaxNumber) {
            this.buyerTaxNumber = buyerTaxNumber;
        }

        public String getBuyerAddressPhone() {
            return buyerAddressPhone;
        }

        public void setBuyerAddressPhone(String buyerAddressPhone) {
            this.buyerAddressPhone = buyerAddressPhone;
        }

        public String getBuyerBank() {
            return buyerBank;
        }

        public void setBuyerBank(String buyerBank) {
            this.buyerBank = buyerBank;
        }

        public String getDealProof() {
            return dealProof;
        }

        public void setDealProof(String dealProof) {
            this.dealProof = dealProof;
        }

        public String getReceiveEmail() {
            return receiveEmail;
        }

        public void setReceiveEmail(String receiveEmail) {
            this.receiveEmail = receiveEmail;
        }
    }

    @Schema(description = "开票明细")
    public static class InvoiceItemForm {

        @Schema(description = "项目名称（商品或服务，P05 明细必填）", example = "技术服务费",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "明细的项目名称不能为空")
        private String itemName;

        @Schema(description = "业务类目/税收分类（P05 明细必填）", example = "信息技术服务",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "明细的业务类目不能为空")
        private String category;

        @Schema(description = "规格型号", example = "标准版")
        private String spec;

        @Schema(description = "数量", example = "1")
        private BigDecimal quantity;

        @Schema(description = "单位", example = "项")
        private String unit;

        @Schema(description = "原始单价（交易约定单价）", example = "10000.00")
        private BigDecimal originalPrice;

        @Schema(description = "计价口径：1含税 2不含税", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        private String priceCaliber;

        @Schema(description = "合同约定金额", example = "10000.00")
        private BigDecimal agreedAmount;

        @Schema(description = "排序号（不传则后端按顺序生成）", example = "1")
        private Long sortNo;

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public BigDecimal getQuantity() {
            return quantity;
        }

        public void setQuantity(BigDecimal quantity) {
            this.quantity = quantity;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public BigDecimal getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(BigDecimal originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getPriceCaliber() {
            return priceCaliber;
        }

        public void setPriceCaliber(String priceCaliber) {
            this.priceCaliber = priceCaliber;
        }

        public BigDecimal getAgreedAmount() {
            return agreedAmount;
        }

        public void setAgreedAmount(BigDecimal agreedAmount) {
            this.agreedAmount = agreedAmount;
        }

        public Long getSortNo() {
            return sortNo;
        }

        public void setSortNo(Long sortNo) {
            this.sortNo = sortNo;
        }
    }
}
