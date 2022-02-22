package com.imooc.reader.service;

import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.Member;
import com.imooc.reader.entity.MemberReadState;

import java.util.Date;

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
    Member checkLogin(String username, String password);

    /**
     * Gets the reading status
     * @param memberId memberId
     * @param bookId bookId
     * @return MemberReadStateMapper
     */
    MemberReadState selectMemberReadState(Long memberId, Long bookId);

    /**
     *Update the user reading status
     * @param memberId member Id
     * @param bookId  book Id
     * @param readState readState
     * @return MemberReadState
     */
    MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState);

    /**
     * Post a new short review
     * @param memberId member Id
     * @param bookId book Id
     * @param score score of the book
     * @param content evaluation content
     * @return Evaluation
     */
    Evaluation evaluate(Long memberId, Long bookId, Integer score, String content);

    /**
     * Comments like
     * @param evaluationId evaluation Id
     * @return Evaluation
     */
    Evaluation enjoy(Long evaluationId);
}
