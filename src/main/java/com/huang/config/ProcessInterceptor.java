package com.huang.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨域拦截器
 */
public class ProcessInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        //支持跨域请求
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        //httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Authorization,Origin, X-Requested-With, Content-Type, Accept,Access-Token");
        httpServletResponse.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
        //默认情况下，跨源请求不提供凭据(cookie、HTTP认证及客户端SSL证明等)，通过将withCredentials属性设置为true，可以指定某个请求应该发送凭据。如果服务器接收带凭据的请求，会用下面的HTTP头部来响应：
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        //httpServletResponse.setHeader("X-Powered-By","Jetty");
        String method= httpServletRequest.getMethod();
        System.out.println(method);
        return true;
    }
}
