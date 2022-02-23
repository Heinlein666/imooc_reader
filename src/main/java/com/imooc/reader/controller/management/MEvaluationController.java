package com.imooc.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.User;
import com.imooc.reader.service.EvaluationService;
import com.imooc.reader.service.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/evaluation")
public class MEvaluationController {
    @Resource
    EvaluationService evaluationService;

    @GetMapping("/index.html")
    public ModelAndView showEvaluation(HttpSession session) {
        User user = (User)session.getAttribute("loginUser");
        if (user == null) {
            return new ModelAndView("/management/login");
        }
        return  new ModelAndView("/management/evaluation");
    }

    /**
     * Query evaluation by page
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> list(Integer page, Integer limit){
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        IPage<Evaluation> pageObject = evaluationService.paging(page, limit);
        // layui need this responded
        Map<String, Object> result = new HashMap<>();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", pageObject.getRecords());    // current page data
        result.put("count", pageObject.getTotal()); // total number book
        return result;
    }

    @PostMapping("/disable")
    @ResponseBody
    public  Map<String, String> disable(Long evaluationId, @RequestParam(defaultValue = "disable")String state, String disableReason) {
        Map<String, String> result = new HashMap<>();
        try {
            evaluationService.changeState(evaluationId, state, disableReason, new Date());
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BusinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return  result;
    }
}
