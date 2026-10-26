package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.MemberMapper;
import com.ruoyi.system.domain.Member;
import com.ruoyi.system.service.IMemberService;

/**
 * 小程序用户Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class MemberServiceImpl implements IMemberService 
{
    @Autowired
    private MemberMapper memberMapper;

    /**
     * 查询小程序用户
     * 
     * @param memberId 小程序用户主键
     * @return 小程序用户
     */
    @Override
    public Member selectMemberByMemberId(Long memberId)
    {
        return memberMapper.selectMemberByMemberId(memberId);
    }

    /**
     * 查询小程序用户列表
     * 
     * @param member 小程序用户
     * @return 小程序用户
     */
    @Override
    public List<Member> selectMemberList(Member member)
    {
        return memberMapper.selectMemberList(member);
    }

    /**
     * 新增小程序用户
     * 
     * @param member 小程序用户
     * @return 结果
     */
    @Override
    public int insertMember(Member member)
    {
        member.setCreateTime(DateUtils.getNowDate());
        return memberMapper.insertMember(member);
    }

    /**
     * 修改小程序用户
     * 
     * @param member 小程序用户
     * @return 结果
     */
    @Override
    public int updateMember(Member member)
    {
        member.setUpdateTime(DateUtils.getNowDate());
        return memberMapper.updateMember(member);
    }

    /**
     * 批量删除小程序用户
     * 
     * @param memberIds 需要删除的小程序用户主键
     * @return 结果
     */
    @Override
    public int deleteMemberByMemberIds(Long[] memberIds)
    {
        return memberMapper.deleteMemberByMemberIds(memberIds);
    }

    /**
     * 删除小程序用户信息
     * 
     * @param memberId 小程序用户主键
     * @return 结果
     */
    @Override
    public int deleteMemberByMemberId(Long memberId)
    {
        return memberMapper.deleteMemberByMemberId(memberId);
    }
}
