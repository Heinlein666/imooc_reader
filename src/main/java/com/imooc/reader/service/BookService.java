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

    /**
     * query book object by book Id
     * @param bookId
     * @return
     */
    Book selectById(Long bookId);

    /**
     * Update book ratings, number of reviews
     */
    void updateEvaluation();

    /**
     * Create a new book
     * @param book
     * @return
     */
    Book createBook(Book book);

    /**
     * Update book
     * @param book
     * @return
     */
    Book updateBook(Book book);

    /**
     * delete book and relate data
     * @param bookId
     */
    void deleteBook(Long bookId);
}
