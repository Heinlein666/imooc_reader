package com.imooc.reader.controller.management;

import com.imooc.reader.entity.User;
import com.imooc.reader.service.UserService;
import com.imooc.reader.service.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/user")
public class MUserController {
    @Resource
    private UserService userService;

    /**
     * show login page
     * @return
     */
    @GetMapping("/login.html")
    public  ModelAndView showLogin() {
        return  new ModelAndView("/management/login");
    }

    /**
     * check user login
     * @param username
     * @param password
     * @param session
     * @return
     */
    @PostMapping("/check_login")
    @ResponseBody
    public Map<String, String> checkLogin(String username, String password, HttpSession session) {
        Map<String, String> result = new HashMap<>();
        try {
            User user = userService.checkLogin(username, password);
            session.setAttribute("loginUser", user);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BusinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }
}
