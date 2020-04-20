package com.huang.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huang.service.impl.UsersServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * spring security 主配置类
 */
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static Logger logger = LoggerFactory.getLogger(MySecurityConfig.class);

    /**
     * 基于数据库的用户认证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        ///需要一个UserDetailsService实现类，这里是customUserService
        //编码类型要根据数据库内存储的密码的编码类型决定，如果密码没有编码会报错
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/getUser").authenticated()
                .antMatchers("/user/upload").authenticated()
                .and()
                .csrf().disable();
        //将下面的过滤器加进来
        http.addFilter(myAuthenticationFilter());
        //使用自定义登陆接口 /login返回一个json
        http.formLogin().loginPage("/login");
        //开启跨域访问
        http.cors();

    }

    /**
     * 以json形式登陆
     * 使用自定义过滤器
     * @return
     */
    @Bean
    MyAuthenticationFilter myAuthenticationFilter() throws Exception {
        MyAuthenticationFilter filter = new MyAuthenticationFilter();
        //将存有着信息放进去
        filter.setAuthenticationManager(super.authenticationManagerBean());
        //以json登陆的接口
        //filter.setFilterProcessesUrl("/jsonLogin");
        //登陆成功过滤器，响应json
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                logger.info("security-登陆成功");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                Map<String,Object> map = new HashMap<>();
                map.put("status",200);
                map.put("msg","登陆成功");
                //map.put("msg",authentication.getPrincipal());//存放身份信息的类
                out.write(new ObjectMapper().writeValueAsString(map));
                out.flush();
                out.close();
            }
        });
        //登陆失败，响应json
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                logger.info("security-登陆失败");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                Map<String,Object> map = new HashMap<>();
                map.put("status",444);
                map.put("res","登陆失败");
                out.write(new ObjectMapper().writeValueAsString(map));
                out.flush();
                out.close();
            }
        });
        return filter;
    }
}
