package com.imooc.reader.service;

import com.imooc.reader.entity.Evaluation;

import java.util.List;

public interface EvaluationService {
    /**
     * Query book valid evaluation by Id
     * @param bookId
     * @return
     */
    List<Evaluation> selectByBookId(Long bookId);
}
