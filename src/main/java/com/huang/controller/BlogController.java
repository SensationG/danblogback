package com.huang.controller;

import com.huang.pojo.Blog;
import com.huang.service.BlogService;
import com.huang.service.impl.UsersServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    private static Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    /**
     * 查找全部博客
     */
    @GetMapping("/getBlogs")
    @ResponseBody
    public List<Blog> getAllBlogs(){
        List<Blog> blogs = blogService.AllBlog();
        return blogs;
    }

    /**
     * 图片/文字上传
     */
    @PostMapping("/pic")
    @ResponseBody
    public void UploadPic(@RequestParam(value = "file",required = false) MultipartFile file,
                          @RequestParam Map<String,String> map,
                           Authentication authentication, HttpServletResponse response){

        //当文件不为空，且文字不为空时，才允许进入
        if(!file.isEmpty() && map.get("textarea")!=null){
            logger.info(file.getOriginalFilename()+map.get("textarea"));
            //获取当前用户名称
            map.put("user_mail",authentication.getName());
            Boolean res = blogService.UpdateBlog(file, map);
            if (res){
                response.setStatus(200);
            }else {
                logger.error("服务器保存错误");
                response.setStatus(451);
            }
        }else {
            logger.error("请求内容不规范");
            response.setStatus(450);
        }

    }
}
