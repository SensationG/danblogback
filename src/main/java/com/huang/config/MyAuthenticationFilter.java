package com.huang.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huang.service.impl.UsersServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * spring security 重写过滤器
 * 重写该方法以实现json登陆
 */
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static Logger logger = LoggerFactory.getLogger(MyAuthenticationFilter.class);

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String mail = null;
        String password = null;
        try {
            //将传入的json数据转为map，再通过get("key")获取
           Map<String,String> map = new ObjectMapper().readValue(request.getInputStream(),Map.class);
            mail = map.get("mail");
           password = map.get("password");
        }catch (IOException e) {
            e.printStackTrace();
        }

        if(mail == null || password == null){
            throw new NullPointerException("邮箱或密码为空");
        }

        //密码验证
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                mail,password);

        return this.getAuthenticationManager().authenticate(authRequest);

        //如果不是json请求，则使用默认的获取表单的形式获取用户名密码
        //return super.attemptAuthentication(request, response);
    }
}
