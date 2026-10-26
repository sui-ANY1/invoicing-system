package com.ruoyi.system.pc.support;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import jakarta.servlet.http.HttpServletResponse;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.pc.service.PcFileService;

/**
 * PC 受控文件流输出（发票文件下载 / 附件受权预览）
 *
 * 只由鉴权接口输出流，不向前端暴露平台内真实路径，也不提供公开永久地址（AC28/§10.1）。
 */
public final class PcFileStreams {

    private PcFileStreams() {
    }

    public static void write(PcFileService.PcFileRef ref, HttpServletResponse response) {
        write(ref, response, false);
    }

    /**
     * @param download true=浏览器按附件下载（真实发票文件），false=行内预览（材料受权预览）
     */
    public static void write(PcFileService.PcFileRef ref, HttpServletResponse response, boolean download) {
        if (ref == null) {
            throw new ServiceException("文件不存在");
        }
        File file = new File(ref.getPath());
        if (!file.exists() || !file.isFile()) {
            throw new ServiceException("文件不存在或已被清理");
        }
        response.setContentType(ref.getContentType());
        response.setCharacterEncoding("UTF-8");
        response.setContentLengthLong(ref.getSize());
        response.setHeader("Content-Disposition", contentDisposition(ref.getFileName(), download));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        try (OutputStream out = response.getOutputStream()) {
            Files.copy(file.toPath(), out);
            out.flush();
        } catch (IOException ex) {
            throw new ServiceException("文件输出失败：" + ex.getMessage());
        }
    }

    private static String contentDisposition(String fileName, boolean download) {
        String name = (fileName == null || fileName.trim().isEmpty()) ? "file" : fileName.trim();
        String encoded = URLEncoder.encode(name, StandardCharsets.UTF_8).replace("+", "%20");
        String type = download ? "attachment" : "inline";
        return type + "; filename=\"" + encoded + "\"; filename*=UTF-8''" + encoded;
    }
}
