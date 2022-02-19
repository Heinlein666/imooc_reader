package com.imooc.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;

public interface BookService {
    /**
     * Query books by page
     * @param categoryId
     * @param order
     * @param page
     * @param row
     * @return page object
     */
    IPage<Book> paging(Long categoryId, String order, Integer page, Integer row);
}
