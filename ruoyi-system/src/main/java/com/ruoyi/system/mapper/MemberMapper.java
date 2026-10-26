package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Member;

/**
 * 小程序用户Mapper接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface MemberMapper 
{
    /**
     * 查询小程序用户
     * 
     * @param memberId 小程序用户主键
     * @return 小程序用户
     */
    public Member selectMemberByMemberId(Long memberId);

    /**
     * 查询小程序用户列表
     * 
     * @param member 小程序用户
     * @return 小程序用户集合
     */
    public List<Member> selectMemberList(Member member);

    /**
     * 新增小程序用户
     * 
     * @param member 小程序用户
     * @return 结果
     */
    public int insertMember(Member member);

    /**
     * 修改小程序用户
     * 
     * @param member 小程序用户
     * @return 结果
     */
    public int updateMember(Member member);

    /**
     * 删除小程序用户
     * 
     * @param memberId 小程序用户主键
     * @return 结果
     */
    public int deleteMemberByMemberId(Long memberId);

    /**
     * 批量删除小程序用户
     * 
     * @param memberIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMemberByMemberIds(Long[] memberIds);
}
