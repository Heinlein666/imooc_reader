package com.imooc.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;

import java.util.Date;
import java.util.List;

public interface EvaluationService {
    /**
     * Query book valid evaluation by Id
     * @param bookId
     * @return
     */
    List<Evaluation> selectByBookId(Long bookId);
    /**
     * Query evaluation by page
     * @param page
     * @param row
     * @return page object
     */
    IPage<Evaluation> paging(Integer page, Integer row);

    /**
     * change evaluation state
     * @param evaluationId
     * @param state
     * @param disableReason
     * @param disableTime
     */
    void changeState(Long evaluationId, String state, String disableReason, Date disableTime);
}
