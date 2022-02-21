package com.imooc.reader.service;

import com.imooc.reader.entity.Member;

public interface MemberService {
    /**
     * Member registration, creation of new members
     * @param username username
     * @param password  password
     * @param nickname nickname
     * @return new member object
     */
    Member createMember(String username, String password, String nickname);

    /**
     * Login check
     * @param username username
     * @param password password
     * @return Member object
     */
    public Member checkLogin(String username, String password);
}
