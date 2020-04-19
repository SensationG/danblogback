package com.huang.controller;

import com.huang.service.impl.UsersServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @RequestMapping("/login")
    @ResponseBody
    public void isLogin(HttpServletResponse response) {
        logger.info("未登录");
        //未登录 返回状态码 414
        response.setStatus(414);
    }

}
