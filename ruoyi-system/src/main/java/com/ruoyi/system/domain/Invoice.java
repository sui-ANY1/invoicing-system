package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 发票信息对象 t_invoice
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class Invoice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 发票 id */
    private Long invoiceId;

    /** 发票号码（唯一发票标识，数电票一般 20 位） */
    @Excel(name = "发票号码", readConverterExp = "唯=一发票标识，数电票一般,2=0,位=")
    private String invoiceNo;

    /** 发票代码（数电票可空） */
    @Excel(name = "发票代码", readConverterExp = "数=电票可空")
    private String invoiceCode;

    /** 发票种类（1蓝字 2红字） */
    @Excel(name = "发票种类", readConverterExp = "1=蓝字,2=红字")
    private String invoiceKind;

    /** 关联开票申请 id（蓝票） */
    @Excel(name = "关联开票申请 id", readConverterExp = "蓝=票")
    private Long applyId;

    /** 关联红字申请 id（红票） */
    @Excel(name = "关联红字申请 id", readConverterExp = "红=票")
    private Long redApplyId;

    /** 销售方主体 id */
    @Excel(name = "销售方主体 id")
    private Long enterpriseId;

    /** 销售方名称（票面快照） */
    @Excel(name = "销售方名称", readConverterExp = "票=面快照")
    private String sellerName;

    /** 销售方税号（票面快照） */
    @Excel(name = "销售方税号", readConverterExp = "票=面快照")
    private String sellerTaxNumber;

    /** 购买方名称（票面快照） */
    @Excel(name = "购买方名称", readConverterExp = "票=面快照")
    private String buyerName;

    /** 购买方税号（票面快照） */
    @Excel(name = "购买方税号", readConverterExp = "票=面快照")
    private String buyerTaxNumber;

    /** 票面金额合计（不含税，票面回传） */
    @Excel(name = "票面金额合计", readConverterExp = "不=含税，票面回传")
    private BigDecimal totalAmount;

    /** 税额合计（票面回传） */
    @Excel(name = "税额合计", readConverterExp = "票=面回传")
    private BigDecimal totalTax;

    /** 价税合计（票面回传） */
    @Excel(name = "价税合计", readConverterExp = "票=面回传")
    private BigDecimal totalPriceTax;

    /** 开票日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开票日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date invoiceDate;

    /** 红冲状态（0未红冲 1部分红冲 2全额红冲） */
    @Excel(name = "红冲状态", readConverterExp = "0=未红冲,1=部分红冲,2=全额红冲")
    private String redStatus;

    /** PDF 文件 url */
    @Excel(name = "PDF 文件 url")
    private String pdfUrl;

    /** OFD 文件 url */
    @Excel(name = "OFD 文件 url")
    private String ofdUrl;

    /** XML 文件 url */
    @Excel(name = "XML 文件 url")
    private String xmlUrl;

    /** 文件状态（0待文件 1已获取 2获取失败） */
    @Excel(name = "文件状态", readConverterExp = "0=待文件,1=已获取,2=获取失败")
    private String fileStatus;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    public void setInvoiceId(Long invoiceId) 
    {
        this.invoiceId = invoiceId;
    }

    public Long getInvoiceId() 
    {
        return invoiceId;
    }

    public void setInvoiceNo(String invoiceNo) 
    {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceNo() 
    {
        return invoiceNo;
    }

    public void setInvoiceCode(String invoiceCode) 
    {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceCode() 
    {
        return invoiceCode;
    }

    public void setInvoiceKind(String invoiceKind) 
    {
        this.invoiceKind = invoiceKind;
    }

    public String getInvoiceKind() 
    {
        return invoiceKind;
    }

    public void setApplyId(Long applyId) 
    {
        this.applyId = applyId;
    }

    public Long getApplyId() 
    {
        return applyId;
    }

    public void setRedApplyId(Long redApplyId) 
    {
        this.redApplyId = redApplyId;
    }

    public Long getRedApplyId() 
    {
        return redApplyId;
    }

    public void setEnterpriseId(Long enterpriseId) 
    {
        this.enterpriseId = enterpriseId;
    }

    public Long getEnterpriseId() 
    {
        return enterpriseId;
    }

    public void setSellerName(String sellerName) 
    {
        this.sellerName = sellerName;
    }

    public String getSellerName() 
    {
        return sellerName;
    }

    public void setSellerTaxNumber(String sellerTaxNumber) 
    {
        this.sellerTaxNumber = sellerTaxNumber;
    }

    public String getSellerTaxNumber() 
    {
        return sellerTaxNumber;
    }

    public void setBuyerName(String buyerName) 
    {
        this.buyerName = buyerName;
    }

    public String getBuyerName() 
    {
        return buyerName;
    }

    public void setBuyerTaxNumber(String buyerTaxNumber) 
    {
        this.buyerTaxNumber = buyerTaxNumber;
    }

    public String getBuyerTaxNumber() 
    {
        return buyerTaxNumber;
    }

    public void setTotalAmount(BigDecimal totalAmount) 
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
    }

    public void setTotalTax(BigDecimal totalTax) 
    {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotalTax() 
    {
        return totalTax;
    }

    public void setTotalPriceTax(BigDecimal totalPriceTax) 
    {
        this.totalPriceTax = totalPriceTax;
    }

    public BigDecimal getTotalPriceTax() 
    {
        return totalPriceTax;
    }

    public void setInvoiceDate(Date invoiceDate) 
    {
        this.invoiceDate = invoiceDate;
    }

    public Date getInvoiceDate() 
    {
        return invoiceDate;
    }

    public void setRedStatus(String redStatus) 
    {
        this.redStatus = redStatus;
    }

    public String getRedStatus() 
    {
        return redStatus;
    }

    public void setPdfUrl(String pdfUrl) 
    {
        this.pdfUrl = pdfUrl;
    }

    public String getPdfUrl() 
    {
        return pdfUrl;
    }

    public void setOfdUrl(String ofdUrl) 
    {
        this.ofdUrl = ofdUrl;
    }

    public String getOfdUrl() 
    {
        return ofdUrl;
    }

    public void setXmlUrl(String xmlUrl) 
    {
        this.xmlUrl = xmlUrl;
    }

    public String getXmlUrl() 
    {
        return xmlUrl;
    }

    public void setFileStatus(String fileStatus) 
    {
        this.fileStatus = fileStatus;
    }

    public String getFileStatus() 
    {
        return fileStatus;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("invoiceId", getInvoiceId())
            .append("invoiceNo", getInvoiceNo())
            .append("invoiceCode", getInvoiceCode())
            .append("invoiceKind", getInvoiceKind())
            .append("applyId", getApplyId())
            .append("redApplyId", getRedApplyId())
            .append("enterpriseId", getEnterpriseId())
            .append("sellerName", getSellerName())
            .append("sellerTaxNumber", getSellerTaxNumber())
            .append("buyerName", getBuyerName())
            .append("buyerTaxNumber", getBuyerTaxNumber())
            .append("totalAmount", getTotalAmount())
            .append("totalTax", getTotalTax())
            .append("totalPriceTax", getTotalPriceTax())
            .append("invoiceDate", getInvoiceDate())
            .append("redStatus", getRedStatus())
            .append("pdfUrl", getPdfUrl())
            .append("ofdUrl", getOfdUrl())
            .append("xmlUrl", getXmlUrl())
            .append("fileStatus", getFileStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
