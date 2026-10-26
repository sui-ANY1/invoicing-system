package com.ruoyi.system.mini.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.Member;
import com.ruoyi.system.mini.dto.R;
import com.ruoyi.system.mini.dto.request.LoginRequest;
import com.ruoyi.system.mini.dto.vo.LoginVO;
import com.ruoyi.system.mini.security.MiniLoginUser;
import com.ruoyi.system.mini.security.MiniSecurityUtils;
import com.ruoyi.system.mini.security.MiniTokenService;
import com.ruoyi.system.mini.wx.MiniWxService;
import com.ruoyi.system.service.IMemberService;

/**
 * 小程序会话登录
 */
@Tag(name = "登录认证", description = "微信登录与手机号绑定")
@RestController
@RequestMapping("/api/mini")
public class MiniAuthController {

    @Autowired
    private IMemberService memberService;

    @Autowired
    private MiniTokenService miniTokenService;

    @Autowired
    private MiniWxService miniWxService;

    @Operation(summary = "登录（创建会话）",
            description = "POST /api/mini/sessions：传入 wx.login() 的 code 换 openid，未注册自动注册并签发 token")
    @PostMapping("/sessions")
    public R<LoginVO> login(@RequestBody LoginRequest request) {
        String code = request == null ? null : request.getCode();
        if (StringUtils.isEmpty(code)) {
            return R.fail("缺少登录 code");
        }
        if (miniWxService.isMock() && !miniWxService.isMockLoginAllowed()) {
            return R.fail("微信凭证未配置且模拟登录已关闭，无法登录");
        }

        String openid = miniWxService.codeToOpenid(code);

        Member query = new Member();
        query.setOpenid(openid);
        List<Member> list = memberService.selectMemberList(query);
        Member member;
        if (list != null && !list.isEmpty()) {
            member = list.get(0);
        } else {
            member = new Member();
            member.setOpenid(openid);
            member.setStatus("0");
            member.setDelFlag("0");
            member.setGender(0L);
            memberService.insertMember(member);
        }

        member.setLastLoginTime(new Date());
        memberService.updateMember(member);

        MiniLoginUser loginUser = buildLoginUser(member);
        String token = miniTokenService.createToken(loginUser);

        LoginVO vo = new LoginVO();
        vo.setToken(token);
        vo.setMemberId(member.getMemberId());
        vo.setPhone(member.getPhone());
        vo.setBindPhone(StringUtils.isNotEmpty(member.getPhone()));
        return R.ok(vo);
    }

    private MiniLoginUser buildLoginUser(Member member) {
        MiniLoginUser loginUser = new MiniLoginUser();
        loginUser.setMemberId(member.getMemberId());
        loginUser.setOpenid(member.getOpenid());
        loginUser.setPhone(member.getPhone());
        loginUser.setNickName(member.getNickName());
        loginUser.setAvatar(member.getAvatar());
        return loginUser;
    }
}
