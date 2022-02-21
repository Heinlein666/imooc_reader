package com.imooc.reader.controller;

import com.imooc.reader.entity.Member;
import com.imooc.reader.service.MemberService;
import com.imooc.reader.service.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {
    @Resource
    private MemberService memberService;
    @GetMapping("/register.html")
    public ModelAndView showRegister() {
        return new ModelAndView("/register");
    }

    @GetMapping("/login.html")
    public ModelAndView showLogin() {
        return new ModelAndView("/login");
    }
    @PostMapping("/register")
    @ResponseBody
    public Map<String, String> register(String vc, String username, String password, String nickname, HttpServletRequest request) {
//        Get a verification code
        String verifyCode = (String) request.getSession().getAttribute("kaptchaVerifyCode");
//        Compare the verification codes
        Map<String, String> result = new HashMap<>();
        if(vc == null || verifyCode == null || !vc.equalsIgnoreCase(verifyCode)) {
            result.put("code", "VC01");
            result.put("msg","验证码错误");
        }else {
            try{
                memberService.createMember(username, password, nickname);
                result.put("code", "0");
                result.put("msg","success");
            }catch (BusinessException ex){
                ex.printStackTrace();
                result.put("code", ex.getCode());
                result.put("msg", ex.getMsg());
            }
        }
        return result;
    }

    @PostMapping("/check_login")
    @ResponseBody
    public Map<String, String> checkLogin(String username, String password, String vc, HttpSession session) {
//        Get a verification code
        String verifyCode = (String)session.getAttribute("kaptchaVerifyCode");
//        Compare the verification codes
        Map<String, String> result = new HashMap<>();
        if(vc == null || verifyCode == null || !vc.equalsIgnoreCase(verifyCode)) {
            result.put("code", "VC01");
            result.put("msg","验证码错误");
        }else {
            try {
                Member member = memberService.checkLogin(username, password);
                session.setAttribute("loginMember", member);
                result.put("code", "0");
                result.put("msg", "success");
            } catch (BusinessException ex) {
                ex.printStackTrace();
                result.put("code", ex.getCode());
                result.put("msg", ex.getMsg());
            }
        }
        return result;
    }
}
