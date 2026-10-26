package com.ruoyi.system.mini.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.system.domain.Attachment;
import com.ruoyi.system.mini.constant.MiniAttachConstants;
import com.ruoyi.system.mini.dto.R;
import com.ruoyi.system.mini.dto.vo.UploadVO;
import com.ruoyi.system.mini.security.MiniSecurityUtils;
import com.ruoyi.system.service.IAttachmentService;

/**
 * 小程序通用文件上传
 *
 * <p>上传即登记 t_attachment（biz_type 默认 temp、biz_id 为 0），业务提交时按 fileUrl 回填业务归属，
 * 使 P16“已提交附件”、主体认证材料、票面文件等受控预览有据可查。</p>
 */
@Tag(name = "文件上传", description = "通用文件上传")
@RestController
@RequestMapping("/api/mini")
public class MiniFileController {

    @Autowired
    private IAttachmentService attachmentService;

    @Operation(summary = "上传文件",
            description = "POST /api/mini/files，multipart/form-data 上传，字段名固定为 file；其余参数以表单字段提交（不放 query、不放 path）："
                    + "bizType/materialType/refKey；返回 attachmentId 与相对访问路径，前端用 baseURL 拼接，"
                    + "提交业务时回传 attachmentId 或 url")
    @PostMapping(value = "/files", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<UploadVO> upload(
            @Parameter(description = "要上传的文件（字段名固定为 file）", required = true)
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "业务类型：temp（默认，未关联）/reg_apply/enterprise/invoice_apply/red_apply")
            @RequestParam(value = "bizType", required = false, defaultValue = MiniAttachConstants.BIZ_TEMP) String bizType,
            @Parameter(description = "材料类型码，如 LEGAL_PERSON_ID_CARD_FRONT、BUSINESS_LICENSE")
            @RequestParam(value = "materialType", required = false) String materialType,
            @Parameter(description = "关联对象键（同一业务下多份同类材料的人员标识，如股东姓名）")
            @RequestParam(value = "refKey", required = false) String refKey) {
        if (!MiniAttachConstants.isAllowedBizType(bizType)) {
            return R.fail("不支持的业务类型");
        }
        if (StringUtils.isNotEmpty(materialType) && !MiniAttachConstants.isKnownMaterial(materialType)) {
            return R.fail("不支持的材料类型");
        }
        try {
            String fileName = FileUploadUtils.upload(RuoYiConfig.getUploadPath(), file);
            String originalFilename = file.getOriginalFilename();
            String fileType = extensionOf(originalFilename);

            Attachment attachment = new Attachment();
            attachment.setBizType(bizType);
            attachment.setBizId(0L);
            attachment.setMaterialType(StringUtils.isNotEmpty(materialType) ? materialType : null);
            attachment.setRefKey(StringUtils.isNotEmpty(refKey) ? refKey : null);
            attachment.setFileType(fileType);
            attachment.setFileName(originalFilename);
            attachment.setFileUrl(fileName);
            attachment.setFileSize(file.getSize());
            Long memberId = MiniSecurityUtils.getMemberIdOrNull();
            attachment.setUploadBy(memberId == null ? "" : String.valueOf(memberId));
            attachment.setDelFlag("0");
            attachment.setCreateTime(new Date());
            attachmentService.insertAttachment(attachment);

            UploadVO vo = new UploadVO();
            vo.setAttachmentId(attachment.getAttachmentId());
            vo.setUrl(fileName);
            vo.setFileName(fileName);
            vo.setOriginalFilename(originalFilename);
            vo.setFileType(fileType);
            vo.setFileSize(file.getSize());
            vo.setMaterialType(attachment.getMaterialType());
            return R.ok(vo);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 取扩展名（不含点，统一小写）
     */
    private String extensionOf(String originalFilename) {
        if (StringUtils.isEmpty(originalFilename) || !originalFilename.contains(".")) {
            return null;
        }
        return originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
    }
}
