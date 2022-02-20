package com.imooc.reader.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class KaptchaController {
    @Resource
    private Producer kaptchaProducer;

    /**
     * Generate a verification code
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/verify_code")
    public void createVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        The response expires immediately
        response.setDateHeader("Expires", 0);
//        No picture data is cached
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.setHeader("Cache-Control", "post-check=0,pre-check=0");
        response.setHeader("Pragma", "no-cache");
//        Returns the picture type
        response.setContentType("image/png");

        String verifyCode = kaptchaProducer.createText(); //Produces captcha character text
        request.getSession().setAttribute("kaptchaVerifyCode", verifyCode);
        BufferedImage image = kaptchaProducer.createImage(verifyCode); //Create a captcha image
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "png", out);
        out.flush();
        out.close();
    }
}
