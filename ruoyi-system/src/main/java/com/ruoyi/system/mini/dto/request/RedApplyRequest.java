package com.ruoyi.system.mini.dto.request;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "红字申请提交请求")
public class RedApplyRequest {

    @Schema(description = "红字申请主信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "红字申请信息不能为空")
    @Valid
    private RedApplyForm apply;

    @Schema(description = "红字明细（部分红冲时必填，全部红冲可空）")
    @Valid
    private List<RedItemForm> items;

    @Schema(description = "补充材料清单（可选；证明材料也可直接放在 apply.proofUrl）")
    private List<MaterialForm> materials;

    public RedApplyForm getApply() {
        return apply;
    }

    public void setApply(RedApplyForm apply) {
        this.apply = apply;
    }

    public List<MaterialForm> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialForm> materials) {
        this.materials = materials;
    }

    public List<RedItemForm> getItems() {
        return items;
    }

    public void setItems(List<RedItemForm> items) {
        this.items = items;
    }

    @Schema(description = "红字申请主信息")
    public static class RedApplyForm {

        @Schema(description = "原蓝字发票ID（须为本人已交付、未全额红冲的蓝票）", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "原蓝字发票不能为空")
        private Long originalInvoiceId;

        @Schema(description = "红字原因：1开票有误 2退货折让 3服务中止 9其他", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "红字原因不能为空")
        private String redReason;

        @Schema(description = "处理范围：1全部 2部分", example = "2",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "处理范围不能为空（1全部 2部分）")
        @Pattern(regexp = "^[12]$", message = "处理范围只能是 1 全部 或 2 部分")
        private String redRange;

        @Schema(description = "情况说明", example = "发票商品名称开具有误，申请红冲重开")
        private String description;

        @Schema(description = "相关依据附件URL", example = "/profile/upload/2026/10/14/proof.png")
        private String proofUrl;

        public Long getOriginalInvoiceId() {
            return originalInvoiceId;
        }

        public void setOriginalInvoiceId(Long originalInvoiceId) {
            this.originalInvoiceId = originalInvoiceId;
        }

        public String getRedReason() {
            return redReason;
        }

        public void setRedReason(String redReason) {
            this.redReason = redReason;
        }

        public String getRedRange() {
            return redRange;
        }

        public void setRedRange(String redRange) {
            this.redRange = redRange;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getProofUrl() {
            return proofUrl;
        }

        public void setProofUrl(String proofUrl) {
            this.proofUrl = proofUrl;
        }
    }

    @Schema(description = "红字明细（部分红冲）")
    public static class RedItemForm {

        @Schema(description = "原开票明细ID（标识红冲哪一条）", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        private Long originalItemId;

        @Schema(description = "项目名称", example = "技术服务费")
        private String itemName;

        @Schema(description = "红冲数量（按票面，红字一般为负数）", example = "-1")
        private BigDecimal quantity;

        @Schema(description = "排序号（不传则后端按顺序生成）", example = "1")
        private Long sortNo;

        public Long getOriginalItemId() {
            return originalItemId;
        }

        public void setOriginalItemId(Long originalItemId) {
            this.originalItemId = originalItemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public BigDecimal getQuantity() {
            return quantity;
        }

        public void setQuantity(BigDecimal quantity) {
            this.quantity = quantity;
        }

        public Long getSortNo() {
            return sortNo;
        }

        public void setSortNo(Long sortNo) {
            this.sortNo = sortNo;
        }
    }
}
