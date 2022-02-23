package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.Member;
import com.imooc.reader.mapper.BookMapper;
import com.imooc.reader.mapper.EvaluationMapper;
import com.imooc.reader.mapper.MemberMapper;
import com.imooc.reader.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private BookMapper bookMapper;
    /**
     * Query book valid evaluation by Id
     * @param bookId
     * @return
     */
    @Override
    public List<Evaluation> selectByBookId(Long bookId) {
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("state", "enable");
        queryWrapper.orderByDesc("create_time");
        List<Evaluation> evaluationList = evaluationMapper.selectList(queryWrapper);
        Book book = bookMapper.selectById(bookId);
        for (Evaluation eva : evaluationList) {
            Member member = memberMapper.selectById(eva.getMemberId());
            eva.setMember(member);
            eva.setBook(book);
        }
        return evaluationList;
    }

    /**
     * Query evaluation by page
     * @param page
     * @param row
     * @return page object
     */
    @Override
    public IPage<Evaluation> paging(Integer page, Integer row) {
        Page<Evaluation> p = evaluationMapper.selectPage(new Page<>(page, row), new QueryWrapper<>());
        for (Evaluation eva : p.getRecords()) {
            eva.setBook(bookMapper.selectById(eva.getBookId()));
            eva.setMember(memberMapper.selectById(eva.getMemberId()));
        }
        return p;
    }


    /**
     * change evaluation state
     * @param evaluationId
     * @param state
     * @param disableReason
     * @param disableTime
     */
    @Override
    @Transactional
    public void changeState(Long evaluationId, String state, String disableReason, Date disableTime) {
        evaluationMapper.changeState(evaluationId, state, disableReason, disableTime);
    }
}
