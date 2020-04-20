package com.huang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;


/**
 * mvc扩展类
 */
@Configuration
public class MyConfig implements WebMvcConfigurer{

    /**
     * 静态资源映射（用户头像图片路径映射）
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/photo/**")
                .addResourceLocations("file:///D:/xiaodanBlog/photo/");
    }

    /**
     * 加密方式注册到spring
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ProcessInterceptor());
    }

    /**
     * 配置spring security 跨域过滤器
     * @return
     */
    @Bean
    CorsFilter corsFilter(){
        return new CorsFilter(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest httpServletRequest) {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.addAllowedHeader("*");
                cfg.addAllowedMethod("*");
                cfg.addAllowedOrigin("http://localhost:8080");
                cfg.setAllowCredentials(true);
                cfg.checkOrigin("http://localhost:8080");
                return cfg;
            }
        });
    }
}
