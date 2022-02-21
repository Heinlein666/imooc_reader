package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.Member;
import com.imooc.reader.mapper.MemberMapper;
import com.imooc.reader.service.MemberService;
import com.imooc.reader.service.exception.BusinessException;
import com.imooc.reader.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("memberService")
@Transactional
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    /**
     * Member registration, creation of new members
     * @param username username
     * @param password password
     * @param nickname nickname
     * @return new member object
     */
    @Override
    public Member createMember(String username, String password, String nickname) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Member> username1 = queryWrapper.eq("username", username);
        List<Member> memberList = memberMapper.selectList(queryWrapper);
        if (memberList.size() > 0) {
            throw new BusinessException("M01", "用户已存在");
        }
        Member member = new Member();
        member.setUsername(username);
        member.setNickname(nickname);
        int salt = new Random().nextInt(1000) + 1000; //salt
        String md5 = MD5Utils.md5Digest(password, salt);
        member.setPassword(md5);
        member.setSalt(salt);
        member.setCreateTime(new Date());
        memberMapper.insert(member);
        return member;
    }

    /**
     * Login check
     * @param username username
     * @param password password
     * @return Member object
     */
    @Override
    public Member checkLogin(String username, String password) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Member member = memberMapper.selectOne(queryWrapper);
        if (member == null) {
            throw new BusinessException("M02", "用户不存在");
        }
        String md5 = MD5Utils.md5Digest(password, member.getSalt());
        if (!md5.equals(member.getPassword())){
            throw new BusinessException("M03", "输入密码有误");
        }
        return member;
    }
}
