package com.ruoyi.system.pc.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * PC 后台请求体集合（统一约定：POST + JSON body，不通过 path/query 传参数，便于后续整体加密）
 *
 * 所有分页请求继承 {@link PageQuery}，pageNum/pageSize 放在 body 里。
 */
public class PcRequests {

    /**
     * 分页基类
     */
    public static class PageQuery {
        @Schema(description = "页码，从 1 开始", example = "1")
        private Integer pageNum = 1;

        @Schema(description = "每页条数，默认 10，最大 200", example = "10")
        private Integer pageSize = 10;

        public Integer getPageNum() {
            return pageNum;
        }

        public void setPageNum(Integer pageNum) {
            this.pageNum = pageNum;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }
    }

    /**
     * D01 待办列表查询
     */
    public static class TodoQuery extends PageQuery {
        @Schema(description = "待办类型：audit 待审核 / material 需补资料 / user 待用户操作 / unknown 结果未知 / file 文件异常，为空取全部")
        private String type;

        @Schema(description = "业务状态（原值，见接口文档状态字典）")
        private String status;

        @Schema(description = "关键字（申请编号 / 主体名称 / 购方名称）")
        private String keyword;

        @Schema(description = "提交时间起（yyyy-MM-dd HH:mm:ss）")
        private String startTime;

        @Schema(description = "提交时间止（yyyy-MM-dd HH:mm:ss）")
        private String endTime;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }

    /**
     * D02 企业列表查询
     */
    public static class EnterpriseQuery extends PageQuery {
        @Schema(description = "企业名称（模糊）")
        private String enterpriseName;

        @Schema(description = "统一社会信用代码/税号（模糊）")
        private String taxNumber;

        @Schema(description = "主体类型：1公司 2个体工商户")
        private String enterpriseType;

        @Schema(description = "认证状态：0待提交 1核验中 2已核验 3未通过")
        private String authStatus;

        public String getEnterpriseName() {
            return enterpriseName;
        }

        public void setEnterpriseName(String enterpriseName) {
            this.enterpriseName = enterpriseName;
        }

        public String getTaxNumber() {
            return taxNumber;
        }

        public void setTaxNumber(String taxNumber) {
            this.taxNumber = taxNumber;
        }

        public String getEnterpriseType() {
            return enterpriseType;
        }

        public void setEnterpriseType(String enterpriseType) {
            this.enterpriseType = enterpriseType;
        }

        public String getAuthStatus() {
            return authStatus;
        }

        public void setAuthStatus(String authStatus) {
            this.authStatus = authStatus;
        }
    }

    /**
     * 通用 ID 请求（详情、附件预览等）
     */
    public static class IdRequest {
        @Schema(description = "主键 id", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long id;

        @Schema(description = "业务类型（部分接口用）：1开票 2红字 3注册")
        private String type;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    /**
     * D03 企业开票概览查询（趋势 + 票种分布）
     */
    public static class EnterpriseOverviewQuery {
        @Schema(description = "企业 id", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long enterpriseId;

        @Schema(description = "实际开票日期起（yyyy-MM-dd），为空取全部")
        private String startDate;

        @Schema(description = "实际开票日期止（yyyy-MM-dd）")
        private String endDate;

        @Schema(description = "趋势粒度：month 按月（默认） / day 按日")
        private String granularity;

        public Long getEnterpriseId() {
            return enterpriseId;
        }

        public void setEnterpriseId(Long enterpriseId) {
            this.enterpriseId = enterpriseId;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getGranularity() {
            return granularity;
        }

        public void setGranularity(String granularity) {
            this.granularity = granularity;
        }
    }

    /**
     * D03 一张真实发票一行（分页）
     */
    public static class InvoiceTicketQuery extends PageQuery {
        @Schema(description = "企业 id（可选，按销售主体过滤）")
        private Long enterpriseId;

        @Schema(description = "关键字：发票号码 / 关联申请号 / 销售主体 / 购买方")
        private String keyword;

        @Schema(description = "文件状态：0待文件 1已获取 2获取失败，为空全部")
        private String fileStatus;

        @Schema(description = "实际开票日期起（yyyy-MM-dd）")
        private String startDate;

        @Schema(description = "实际开票日期止（yyyy-MM-dd）")
        private String endDate;

        @Schema(description = "票种：1蓝字 2红字，为空全部")
        private String invoiceKind;

        @Schema(description = "开票申请 id（可选，用于申请维度查看）")
        private Long applyId;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getFileStatus() {
            return fileStatus;
        }

        public void setFileStatus(String fileStatus) {
            this.fileStatus = fileStatus;
        }

        public Long getEnterpriseId() {
            return enterpriseId;
        }

        public void setEnterpriseId(Long enterpriseId) {
            this.enterpriseId = enterpriseId;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getInvoiceKind() {
            return invoiceKind;
        }

        public void setInvoiceKind(String invoiceKind) {
            this.invoiceKind = invoiceKind;
        }

        public Long getApplyId() {
            return applyId;
        }

        public void setApplyId(Long applyId) {
            this.applyId = applyId;
        }
    }

    /**
     * 发票文件受控下载
     */
    public static class InvoiceFileQuery {
        @Schema(description = "真实发票 id（t_invoice.invoice_id）", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long invoiceId;

        @Schema(description = "文件格式：pdf（默认） / ofd / xml")
        private String format;

        public Long getInvoiceId() {
            return invoiceId;
        }

        public void setInvoiceId(Long invoiceId) {
            this.invoiceId = invoiceId;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }
    }

    /**
     * D04 开票与红字统一记录列表查询
     */
    public static class RecordQuery extends PageQuery {
        @Schema(description = "记录类型：1开票 2红字，为空全部（合并分页）")
        private String type;

        @Schema(description = "业务状态（开票 apply_status / 红字 red_status 原值）")
        private String status;

        @Schema(description = "企业 id")
        private Long enterpriseId;

        @Schema(description = "企业名称（模糊）")
        private String enterpriseName;

        @Schema(description = "购方名称（模糊）")
        private String buyerName;

        @Schema(description = "票种：1普票 2专票（仅开票记录有效）")
        private String invoiceType;

        @Schema(description = "发票号码（模糊）")
        private String invoiceNo;

        @Schema(description = "申请编号（模糊）")
        private String applyNo;

        @Schema(description = "提交时间起（yyyy-MM-dd HH:mm:ss）")
        private String startTime;

        @Schema(description = "提交时间止（yyyy-MM-dd HH:mm:ss）")
        private String endTime;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Long getEnterpriseId() {
            return enterpriseId;
        }

        public void setEnterpriseId(Long enterpriseId) {
            this.enterpriseId = enterpriseId;
        }

        public String getEnterpriseName() {
            return enterpriseName;
        }

        public void setEnterpriseName(String enterpriseName) {
            this.enterpriseName = enterpriseName;
        }

        public String getBuyerName() {
            return buyerName;
        }

        public void setBuyerName(String buyerName) {
            this.buyerName = buyerName;
        }

        public String getInvoiceType() {
            return invoiceType;
        }

        public void setInvoiceType(String invoiceType) {
            this.invoiceType = invoiceType;
        }

        public String getInvoiceNo() {
            return invoiceNo;
        }

        public void setInvoiceNo(String invoiceNo) {
            this.invoiceNo = invoiceNo;
        }

        public String getApplyNo() {
            return applyNo;
        }

        public void setApplyNo(String applyNo) {
            this.applyNo = applyNo;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }

    /**
     * D05 记录详情请求
     */
    public static class RecordDetailQuery {
        @Schema(description = "记录类型：1开票 2红字", requiredMode = Schema.RequiredMode.REQUIRED)
        private String type;

        @Schema(description = "记录 id：开票为 applyId，红字为 redApplyId", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long id;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    /**
     * 记录定位请求（补取文件 / 刷新确认单等只需要「哪条记录」的动作）
     */
    public static class RecordIdRequest {
        @Schema(description = "记录类型：1开票 2红字", requiredMode = Schema.RequiredMode.REQUIRED)
        private String bizType;

        @Schema(description = "记录 id：开票为 applyId，红字为 redApplyId", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long bizId;

        public String getBizType() {
            return bizType;
        }

        public void setBizType(String bizType) {
            this.bizType = bizType;
        }

        public Long getBizId() {
            return bizId;
        }

        public void setBizId(Long bizId) {
            this.bizId = bizId;
        }
    }

    /**
     * 开票审核（D05-S1）
     */
    public static class RecordAuditRequest {
        @Schema(description = "开票申请 id", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long applyId;

        @Schema(description = "审核结论：1通过 2退回补件 3不通过", requiredMode = Schema.RequiredMode.REQUIRED)
        private String result;

        @Schema(description = "审核意见（退回补件/不通过时必填）")
        private String opinion;

        @Schema(description = "业务版本号（提交时版本，为空取 1）")
        private Integer bizVersion;

        public Long getApplyId() {
            return applyId;
        }

        public void setApplyId(Long applyId) {
            this.applyId = applyId;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getOpinion() {
            return opinion;
        }

        public void setOpinion(String opinion) {
            this.opinion = opinion;
        }

        public Integer getBizVersion() {
            return bizVersion;
        }

        public void setBizVersion(Integer bizVersion) {
            this.bizVersion = bizVersion;
        }
    }

    /**
     * 附件查询（记录附件列表 / 注册材料清单）
     */
    public static class AttachmentQuery {
        @Schema(description = "业务类型：invoice_apply / red_apply / reg_apply / enterprise / manual_result")
        private String bizType;

        @Schema(description = "业务 id")
        private Long bizId;

        @Schema(description = "附件 id（单条查询时用，优先于 bizType/bizId）")
        private Long attachmentId;

        @Schema(description = "材料码（可选，按材料类型过滤，见材料字典）")
        private String materialType;

        public String getBizType() {
            return bizType;
        }

        public void setBizType(String bizType) {
            this.bizType = bizType;
        }

        public Long getBizId() {
            return bizId;
        }

        public void setBizId(Long bizId) {
            this.bizId = bizId;
        }

        public Long getAttachmentId() {
            return attachmentId;
        }

        public void setAttachmentId(Long attachmentId) {
            this.attachmentId = attachmentId;
        }

        public String getMaterialType() {
            return materialType;
        }

        public void setMaterialType(String materialType) {
            this.materialType = materialType;
        }
    }

    /**
     * 人工结果回填（合法人工录入，留痕）
     */
    public static class ManualResultRequest {
        @Schema(description = "业务类型：1红字 2蓝字", requiredMode = Schema.RequiredMode.REQUIRED)
        private String bizType;

        @Schema(description = "业务 id（红字申请 id 或开票申请 id）", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long bizId;

        @Schema(description = "实际结果：1已交付 2办理失败 9其他", requiredMode = Schema.RequiredMode.REQUIRED)
        private String actualResult;

        @Schema(description = "原始凭证附件 url")
        private String voucherUrl;

        @Schema(description = "回填说明")
        private String fillNote;

        public String getBizType() {
            return bizType;
        }

        public void setBizType(String bizType) {
            this.bizType = bizType;
        }

        public Long getBizId() {
            return bizId;
        }

        public void setBizId(Long bizId) {
            this.bizId = bizId;
        }

        public String getActualResult() {
            return actualResult;
        }

        public void setActualResult(String actualResult) {
            this.actualResult = actualResult;
        }

        public String getVoucherUrl() {
            return voucherUrl;
        }

        public void setVoucherUrl(String voucherUrl) {
            this.voucherUrl = voucherUrl;
        }

        public String getFillNote() {
            return fillNote;
        }

        public void setFillNote(String fillNote) {
            this.fillNote = fillNote;
        }
    }

    /**
     * 红字处理（审核 / 确认单动作）
     */
    public static class RedHandleRequest {
        @Schema(description = "红字申请 id", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long redApplyId;

        @Schema(description = "动作：audit 审核 / confirmStart 发起确认单 / confirmOk 对方已确认 / "
                + "confirmExpire 确认到期 / stop 停止办理", allowableValues = {"audit", "confirmStart",
                "confirmOk", "confirmExpire", "stop"})
        private String action;

        @Schema(description = "审核结论（仅 audit 动作）：1通过 2要求补充 3不通过")
        private String result;

        @Schema(description = "确认单状态（服务端按动作自行推进，入参不读取）")
        private String confirmStatus;

        @Schema(description = "确认单有效小时数（默认 72）")
        private Integer confirmHours;

        @Schema(description = "意见/说明")
        private String opinion;

        public Long getRedApplyId() {
            return redApplyId;
        }

        public void setRedApplyId(Long redApplyId) {
            this.redApplyId = redApplyId;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getConfirmStatus() {
            return confirmStatus;
        }

        public void setConfirmStatus(String confirmStatus) {
            this.confirmStatus = confirmStatus;
        }

        public Integer getConfirmHours() {
            return confirmHours;
        }

        public void setConfirmHours(Integer confirmHours) {
            this.confirmHours = confirmHours;
        }

        public String getOpinion() {
            return opinion;
        }

        public void setOpinion(String opinion) {
            this.opinion = opinion;
        }
    }

    /**
     * 红票凭证回填（生成红字真实发票行）
     */
    public static class RedVoucherRequest {
        @Schema(description = "红字申请 id", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long redApplyId;

        @Schema(description = "红字发票号码", requiredMode = Schema.RequiredMode.REQUIRED)
        private String invoiceNo;

        @Schema(description = "发票代码")
        private String invoiceCode;

        @Schema(description = "实际开票日期（yyyy-MM-dd）")
        private String invoiceDate;

        @Schema(description = "票面合计金额（票面回传，不计算）")
        private String totalAmount;

        @Schema(description = "票面合计税额（票面回传）")
        private String totalTax;

        @Schema(description = "价税合计（票面回传）")
        private String totalPriceTax;

        @Schema(description = "PDF 文件相对路径")
        private String pdfUrl;

        @Schema(description = "OFD 文件相对路径")
        private String ofdUrl;

        @Schema(description = "XML 文件相对路径")
        private String xmlUrl;

        public Long getRedApplyId() {
            return redApplyId;
        }

        public void setRedApplyId(Long redApplyId) {
            this.redApplyId = redApplyId;
        }

        public String getInvoiceNo() {
            return invoiceNo;
        }

        public void setInvoiceNo(String invoiceNo) {
            this.invoiceNo = invoiceNo;
        }

        public String getInvoiceCode() {
            return invoiceCode;
        }

        public void setInvoiceCode(String invoiceCode) {
            this.invoiceCode = invoiceCode;
        }

        public String getInvoiceDate() {
            return invoiceDate;
        }

        public void setInvoiceDate(String invoiceDate) {
            this.invoiceDate = invoiceDate;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getTotalTax() {
            return totalTax;
        }

        public void setTotalTax(String totalTax) {
            this.totalTax = totalTax;
        }

        public String getTotalPriceTax() {
            return totalPriceTax;
        }

        public void setTotalPriceTax(String totalPriceTax) {
            this.totalPriceTax = totalPriceTax;
        }

        public String getPdfUrl() {
            return pdfUrl;
        }

        public void setPdfUrl(String pdfUrl) {
            this.pdfUrl = pdfUrl;
        }

        public String getOfdUrl() {
            return ofdUrl;
        }

        public void setOfdUrl(String ofdUrl) {
            this.ofdUrl = ofdUrl;
        }

        public String getXmlUrl() {
            return xmlUrl;
        }

        public void setXmlUrl(String xmlUrl) {
            this.xmlUrl = xmlUrl;
        }
    }

    /**
     * D11 注册信息列表查询（只读）
     */
    public static class RegQuery extends PageQuery {
        @Schema(description = "主体类型：1有限责任公司 2个体工商户")
        private String subjectType;

        @Schema(description = "收件编号（模糊）")
        private String regApplyNo;

        @Schema(description = "公司名称/拟定字号（模糊）")
        private String companyName;

        @Schema(description = "提交时间起（yyyy-MM-dd HH:mm:ss）")
        private String startTime;

        @Schema(description = "提交时间止（yyyy-MM-dd HH:mm:ss）")
        private String endTime;

        public String getSubjectType() {
            return subjectType;
        }

        public void setSubjectType(String subjectType) {
            this.subjectType = subjectType;
        }

        public String getRegApplyNo() {
            return regApplyNo;
        }

        public void setRegApplyNo(String regApplyNo) {
            this.regApplyNo = regApplyNo;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }
}
