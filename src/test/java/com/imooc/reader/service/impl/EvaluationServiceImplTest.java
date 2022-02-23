package com.imooc.reader.service.impl;

import com.imooc.reader.service.EvaluationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class EvaluationServiceImplTest {
    @Resource
    private EvaluationService evaluationService;
    @Test
    public void changeState() {
        evaluationService.changeState(2L, "disable", "xx", new Date());
    }
}