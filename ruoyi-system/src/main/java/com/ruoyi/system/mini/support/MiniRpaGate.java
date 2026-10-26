package com.ruoyi.system.mini.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 阶段五能力开关：电子税局自动化（RPA）是否已接入。
 *
 * <p>未接入时，涉及外部动作的接口必须明确拒绝，不能静默成功——
 * 否则用户会以为已提交/已重取，实际什么都没发生。</p>
 *
 * @author sui-ANY1
 */
@Component
public class MiniRpaGate {

    /**
     * 阶段五接入后由部署配置 MINI_RPA_ENABLED=true 打开
     */
    @Value("${mini.rpa.enabled:false}")
    private boolean enabled;

    public boolean enabled() {
        return enabled;
    }
}