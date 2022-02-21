package com.imooc.reader.service;

import com.imooc.reader.entity.Member;

public interface MemberService {
    /**
     * Member registration, creation of new members
     * @param username
     * @param password
     * @param nickname
     * @return new member object
     */
    Member createMember(String username, String password, String nickname);
}
