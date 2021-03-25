package com.wang.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Controller
@Api(tags="验证码功能")
@Transactional
public class KapchaController {
    @Resource
    private DefaultKaptcha captchaProducer;

    @ApiOperation("生成验证码")
    @GetMapping("/kaptcha")
    public void verifycode(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        byte[] captchaOutputStream = null;
        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String imgCode = captchaProducer.createText();
            request.getSession().setAttribute("imgCode", imgCode);
            BufferedImage challenge = captchaProducer.createImage(imgCode);
            ImageIO.write(challenge, "jpg", imgOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        captchaOutputStream = imgOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(captchaOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
