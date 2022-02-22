package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.MemberReadState;
import com.imooc.reader.mapper.BookMapper;
import com.imooc.reader.mapper.EvaluationMapper;
import com.imooc.reader.mapper.MemberReadStateMapper;
import com.imooc.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMapper;
    /**
     * Query books by page
     * @param categoryId
     * @param order
     * @param page
     * @param row
     * @return page object
     */
    @Override
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer row) {
        Page<Book> p = new Page<>(page, row);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        if (categoryId != null && categoryId != -1) {
            queryWrapper.eq("category_id", categoryId);
        }
        if(order != null) {
            if (order.equals("quantity")) {
                queryWrapper.orderByDesc("evaluation_quantity");
            }else if(order.equals("score")) {
                queryWrapper.orderByDesc("evaluation_score");
            }
        }
        Page<Book> pageObject = bookMapper.selectPage(p, queryWrapper);
        return pageObject;
    }


    /**
     * query book object by book Id
     * @param bookId
     * @return
     */
    @Override
    public Book selectById(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        return book;
    }

    /**
     * Update book evaluation, number of reviews
     */
    @Transactional
    @Override
    public void updateEvaluation() {
        bookMapper.updateEvaluation();
    }

    /**
     * create a new book
     * @param book
     * @return
     */
    @Transactional
    @Override
    public Book createBook(Book book) {
        bookMapper.insert(book);
        return book;
    }

    /**
     * Update book
     * @param book
     * @return
     */
    @Override
    @Transactional
    public Book updateBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    /**
     * delete book and relate data
     * @param bookId
     */
    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        bookMapper.deleteById(bookId);
        QueryWrapper<MemberReadState> mrsQueryWrapper = new QueryWrapper<>();
        mrsQueryWrapper.eq("book_id", bookId);
        memberReadStateMapper.delete(mrsQueryWrapper);
        QueryWrapper<Evaluation> evaluationQueryWrapper = new QueryWrapper<>();
        evaluationQueryWrapper.eq("book_id", bookId);
        evaluationMapper.delete(evaluationQueryWrapper);
    }
}
