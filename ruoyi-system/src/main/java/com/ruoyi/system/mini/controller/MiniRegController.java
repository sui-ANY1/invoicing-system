package com.ruoyi.system.mini.controller;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.Attachment;
import com.ruoyi.system.mini.dto.R;
import com.ruoyi.system.mini.dto.request.RegApplyRequest;
import com.ruoyi.system.mini.dto.vo.IdResultVO;
import com.ruoyi.system.mini.dto.vo.PageResult;
import com.ruoyi.system.mini.dto.vo.RegApplyVO;
import com.ruoyi.system.mini.dto.vo.RegDetailVO;
import com.ruoyi.system.mini.security.MiniSecurityUtils;
import com.ruoyi.system.mini.service.MiniRegService;
import com.ruoyi.system.mini.support.MiniPageUtils;
import jakarta.validation.Valid;

/**
 * 小程序注册收件
 *
 * 统一约定：RESTful 资源路径；列表用 GET + query 参数，详情/附件用 GET + path 变量，
 * 提交用 POST + JSON body；附件预览的响应为二进制文件流。
 */
@Tag(name = "注册收件",
        description = "注册信息提交、历史、详情、附件预览（RESTful：GET 查询、POST 提交）")
@RestController
@RequestMapping("/api/mini/registration-submissions")
public class MiniRegController {

    /**
     * 未指定 pageSize 时的默认分页大小（上限）
     */
    private static final int DEFAULT_PAGE_SIZE = 200;

    @Autowired
    private MiniRegService regService;

    @Operation(summary = "提交注册信息",
            description = "可选鉴权：匿名可提交（归属为空，之后无法在小程序回看），已登录提交则绑定当前会员；"
                    + "有限公司传 company（+可选 shareholders），个体户传 sole；materials 传已上传材料（材料类型 + url/attachmentId）；"
                    + "成功返回 201 与收件ID")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public R<IdResultVO> apply(@Valid @RequestBody RegApplyRequest request) {
        Long id = regService.apply(request, MiniSecurityUtils.getMemberIdOrNull());
        return R.ok(new IdResultVO(id));
    }

    @Operation(summary = "查询注册历史",
            description = "当前登录会员提交的注册收件单列表；分页参数 pageNum/pageSize（默认 1/10，上限 200）；companyName 按拟定名称（公司名/字号）模糊搜索；返回 {total,rows}")
    @GetMapping
    public R<PageResult<RegApplyVO>> history(
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "companyName", required = false) String companyName) {
        Long memberId = MiniSecurityUtils.getMemberId();
        List<RegApplyVO> list = regService.history(memberId);
        String kw = (companyName == null || companyName.trim().isEmpty()) ? null
                : companyName.trim().toLowerCase();
        return R.ok(MiniPageUtils.of(list, pageNum, pageSize,
                vo -> kw == null || MiniPageUtils.containsIgnoreCase(vo.getCompanyName(), kw)));
    }

    @Operation(summary = "查询注册详情",
            description = "query 参数 submissionId；返回主单 + 公司/个体户资料 + 股东列表 + 已提交材料清单（需登录且为本人提交）")
    @GetMapping("/detail")
    public R<RegDetailVO> detail(@RequestParam("submissionId") Long submissionId) {
        Long memberId = MiniSecurityUtils.getMemberId();
        return R.ok(regService.detail(memberId, submissionId));
    }

    @Operation(summary = "预览/下载注册附件",
            description = "query 参数 submissionId + attachmentId；直接返回文件流（不是 JSON，Apifox 响应类型选“文件”）")
    @GetMapping("/attachments/file")
    public void file(@RequestParam("submissionId") Long submissionId,
                     @RequestParam("attachmentId") Long attachmentId,
                     HttpServletResponse response) throws Exception {
        Long memberId = MiniSecurityUtils.getMemberId();
        Attachment att = regService.getOwnedAttachment(memberId, attachmentId);
        if (!submissionId.equals(att.getBizId())) {
            throw new RuntimeException("附件不属于该注册收件单");
        }
        File file = toDiskFile(att.getFileUrl());

        response.setContentType(contentType(att));
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String encoded = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8).replace("+", "%20");
        response.setHeader("Content-Disposition",
                "inline; filename=\"" + encoded + "\"; filename*=UTF-8''" + encoded);
        Files.copy(file.toPath(), response.getOutputStream());
        response.getOutputStream().flush();
    }

    private File toDiskFile(String url) {
        String relative = url;
        if (relative.startsWith(Constants.RESOURCE_PREFIX)) {
            relative = relative.substring(Constants.RESOURCE_PREFIX.length());
        }
        File file = new File(RuoYiConfig.getProfile(), relative);
        if (!file.exists() || !file.isFile()) {
            throw new RuntimeException("文件丢失或尚未交付");
        }
        return file;
    }

    private String contentType(Attachment att) {
        String t = StringUtils.isEmpty(att.getFileType()) ? "" : att.getFileType().toLowerCase();
        if (t.contains("pdf")) {
            return "application/pdf";
        }
        if (t.contains("ofd")) {
            return "application/ofd";
        }
        if (t.contains("png")) {
            return "image/png";
        }
        if (t.contains("jpg") || t.contains("jpeg")) {
            return "image/jpeg";
        }
        return "application/octet-stream";
    }
}
