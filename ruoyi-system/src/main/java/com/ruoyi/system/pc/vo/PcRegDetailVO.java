package com.ruoyi.system.pc.vo;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import com.ruoyi.system.domain.RegApply;
import com.ruoyi.system.domain.RegCompany;
import com.ruoyi.system.domain.RegShareholder;
import com.ruoyi.system.domain.RegSole;

/**
 * D12 注册详情（只读）
 *
 * AC11/AC12：PC 注册模块只有列表 + 详情 + 受权附件预览，没有任何写/审核/补件/导出能力。
 */
@Schema(description = "注册详情（只读，D12）")
public class PcRegDetailVO {

    @Schema(description = "注册申请主单")
    private RegApply regApply;

    @Schema(description = "主体类型名称：有限责任公司 / 个体工商户")
    private String subjectTypeText;

    @Schema(description = "完成状态名称：待完成 / 已完成")
    private String completeStatusText;

    @Schema(description = "公司资料（主体类型=1 有限责任公司）")
    private RegCompany company;

    @Schema(description = "股东列表（主体类型=1）")
    private List<RegShareholder> shareholders = new ArrayList<>();

    @Schema(description = "个体工商户资料（主体类型=2）")
    private RegSole sole;

    @Schema(description = "已提交材料清单（受权附件预览）")
    private List<PcAttachmentVO> materials = new ArrayList<>();

    @Schema(description = "只读标记（恒为 true）")
    private Boolean readOnly = Boolean.TRUE;

    @Schema(description = "规则说明")
    private String ruleNote = "注册信息为只读视图，不提供编辑、审核、补件、导出能力；已完成仅代表后台登记了注册结果，不自动创建或通过开票企业认证";

    public RegApply getRegApply() {
        return regApply;
    }

    public void setRegApply(RegApply regApply) {
        this.regApply = regApply;
    }

    public String getSubjectTypeText() {
        return subjectTypeText;
    }

    public void setSubjectTypeText(String subjectTypeText) {
        this.subjectTypeText = subjectTypeText;
    }

    public String getCompleteStatusText() {
        return completeStatusText;
    }

    public void setCompleteStatusText(String completeStatusText) {
        this.completeStatusText = completeStatusText;
    }

    public RegCompany getCompany() {
        return company;
    }

    public void setCompany(RegCompany company) {
        this.company = company;
    }

    public List<RegShareholder> getShareholders() {
        return shareholders;
    }

    public void setShareholders(List<RegShareholder> shareholders) {
        this.shareholders = shareholders;
    }

    public RegSole getSole() {
        return sole;
    }

    public void setSole(RegSole sole) {
        this.sole = sole;
    }

    public List<PcAttachmentVO> getMaterials() {
        return materials;
    }

    public void setMaterials(List<PcAttachmentVO> materials) {
        this.materials = materials;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getRuleNote() {
        return ruleNote;
    }

    public void setRuleNote(String ruleNote) {
        this.ruleNote = ruleNote;
    }
}
