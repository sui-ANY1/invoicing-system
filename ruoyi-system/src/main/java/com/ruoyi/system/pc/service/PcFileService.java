package com.ruoyi.system.pc.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.Attachment;
import com.ruoyi.system.domain.Invoice;
import com.ruoyi.system.service.IAttachmentService;
import com.ruoyi.system.service.IInvoiceService;

/**
 * PC 受控文件访问（D03 行内下载 / D12 受权附件预览）
 *
 * 入口：POST /pc/file/invoice/download（附件下载）与 POST /pc/file/attachment/download、
 * POST /pc/reg/attachment/preview（行内预览）。
 * 说明：只允许访问平台内已归档的 /profile 相对路径文件；外部永久地址一律拒绝，
 * 避免把公开的敏感文件地址暴露给前端（AC28/§10.1）。
 */
@Service
public class PcFileService {

    @Autowired
    private IInvoiceService invoiceService;

    @Autowired
    private IAttachmentService attachmentService;

    /**
     * 真实发票文件（pdf 优先、ofd/xml 按需；未交付只提示补取，不重新开票）
     */
    public PcFileRef invoiceFile(Long invoiceId, String format) {
        if (invoiceId == null) {
            throw new ServiceException("参数缺失：invoiceId");
        }
        Invoice invoice = invoiceService.selectInvoiceByInvoiceId(invoiceId);
        if (invoice == null) {
            throw new ServiceException("发票不存在");
        }

        String fmt = (format == null || format.trim().isEmpty()) ? "pdf" : format.trim().toLowerCase();
        String url;
        String contentType;
        String suffix;
        if ("pdf".equals(fmt)) {
            url = invoice.getPdfUrl();
            contentType = "application/pdf";
            suffix = "pdf";
        } else if ("ofd".equals(fmt)) {
            url = invoice.getOfdUrl();
            contentType = "application/octet-stream";
            suffix = "ofd";
        } else if ("xml".equals(fmt)) {
            url = invoice.getXmlUrl();
            contentType = "application/xml";
            suffix = "xml";
        } else {
            throw new ServiceException("不支持的文件格式：" + format);
        }

        if (!hasText(url)) {
            // 默认格式回退：请求 pdf 但还没有 pdf 时，用已有的受支持格式（ofd 优先，其次 xml）
            if ("pdf".equals(fmt) && hasText(invoice.getOfdUrl())) {
                url = invoice.getOfdUrl();
                contentType = "application/octet-stream";
                suffix = "ofd";
            } else if ("pdf".equals(fmt) && hasText(invoice.getXmlUrl())) {
                url = invoice.getXmlUrl();
                contentType = "application/xml";
                suffix = "xml";
            } else {
                throw new ServiceException("发票文件尚未交付，请稍后再试");
            }
        }

        String path = toLocalPath(url);
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            throw new ServiceException("发票文件尚未交付，请稍后再试");
        }

        String base = hasText(invoice.getInvoiceNo()) ? invoice.getInvoiceNo() : ("invoice-" + invoiceId);
        return new PcFileRef(path, base + "." + suffix, contentType, file.length());
    }

    /**
     * 业务附件（材料）受控预览：按 attachment_id 取，拒绝外部地址
     *
     * 传入 submissionId 时校验附件确实属于该注册收件（防止只改 attachmentId 越权预览别人资料）。
     */
    public PcFileRef attachmentFile(Long attachmentId, Long submissionId) {
        if (attachmentId == null) {
            throw new ServiceException("参数缺失：attachmentId");
        }
        Attachment attachment = attachmentService.selectAttachmentByAttachmentId(attachmentId);
        if (attachment == null) {
            throw new ServiceException("附件不存在");
        }
        if (submissionId != null
                && (!"reg_apply".equals(attachment.getBizType()) || !submissionId.equals(attachment.getBizId()))) {
            throw new ServiceException("该附件不属于当前注册资料，无权预览");
        }
        if (!hasText(attachment.getFileUrl())) {
            throw new ServiceException("附件地址缺失，请重新上传");
        }
        String path = toLocalPath(attachment.getFileUrl());
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            throw new ServiceException("附件文件不存在或已被清理");
        }
        String fileName = hasText(attachment.getFileName()) ? attachment.getFileName()
                : ("attachment-" + attachmentId + extension(attachment.getFileUrl()));
        return new PcFileRef(path, fileName, contentTypeOf(fileName), file.length());
    }

    /**
     * 相对地址转本地路径：仅支持 /profile 归档路径
     */
    public String toLocalPath(String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            throw new ServiceException("外部文件地址不受支持，请先归档到平台");
        }
        String relative = url.startsWith("/profile") ? url.substring("/profile".length()) : url;
        if (!relative.startsWith("/")) {
            relative = "/" + relative;
        }
        return RuoYiConfig.getProfile() + relative;
    }

    private String contentTypeOf(String fileName) {
        String lower = fileName == null ? "" : fileName.toLowerCase();
        if (lower.endsWith(".pdf")) {
            return "application/pdf";
        }
        if (lower.endsWith(".ofd")) {
            return "application/octet-stream";
        }
        if (lower.endsWith(".xml")) {
            return "application/xml";
        }
        if (lower.endsWith(".png")) {
            return "image/png";
        }
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if (lower.endsWith(".gif")) {
            return "image/gif";
        }
        if (lower.endsWith(".zip")) {
            return "application/zip";
        }
        return "application/octet-stream";
    }

    private String extension(String url) {
        if (url == null) {
            return "";
        }
        int index = url.lastIndexOf('.');
        return index < 0 ? "" : url.substring(index);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * 受控文件引用
     */
    public static class PcFileRef {
        private final String path;
        private final String fileName;
        private final String contentType;
        private final long size;

        public PcFileRef(String path, String fileName, String contentType, long size) {
            this.path = path;
            this.fileName = fileName;
            this.contentType = contentType;
            this.size = size;
        }

        public String getPath() {
            return path;
        }

        public String getFileName() {
            return fileName;
        }

        public String getContentType() {
            return contentType;
        }

        public long getSize() {
            return size;
        }
    }
}
