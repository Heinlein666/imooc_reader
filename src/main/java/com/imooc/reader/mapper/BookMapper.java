package com.imooc.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.reader.entity.Book;

public interface BookMapper extends BaseMapper<Book> {
    /**
     * Update book ratings, number of reviews
     */
    void updateEvaluation();
}
