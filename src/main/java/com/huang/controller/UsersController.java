package com.huang.controller;

import com.huang.pojo.Users;
import com.huang.service.UsersService;
import com.huang.service.impl.UsersServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    private static Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    /**
     * 用户注册
     * 返回自定义状态码 200:成功 444:失败
     * @param users
     * @param response
     */
    @PostMapping("/regiest")
    @ResponseBody
    public void UserRegiest(@RequestBody Users users, HttpServletResponse response){
        //返回true则注册成功
        Boolean res = usersService.UserRegiest(users);
        //根据结果返回自定义状态码
        if(res){
            logger.info("返回200状态码");
            response.setStatus(200);
        }else {
            logger.info("返回444状态码");
            response.setStatus(444);
        }
    }

    /**
     * 获取用户信息by mail
     * @param
     * @return
     */
    @GetMapping("/getUser")
    @ResponseBody
    public Users getUserByMail(Authentication authentication){
        //从spring security 获取用户信息
        String mail = authentication.getName();
        Users user = usersService.findUserByMail(mail);
        return user;

    }



}
