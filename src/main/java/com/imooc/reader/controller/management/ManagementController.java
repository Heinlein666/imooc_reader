package com.imooc.reader.controller.management;

import com.imooc.reader.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Map;

/**
 * Library management system controller
 */
@Controller
@RequestMapping("/management")
public class ManagementController {

    @GetMapping("/index.html")
    public ModelAndView showIndex(HttpSession session){
        User user = (User)session.getAttribute("loginUser");
        if (user == null) {
          return new ModelAndView("/management/login");
        }
        ModelAndView modelAndView = new ModelAndView("/management/index");
        modelAndView.addObject("loginUser",user);
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session){
        session.removeAttribute("loginUser");
        return new ModelAndView("/management/login");
    }
}
