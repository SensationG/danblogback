package com.huang.service.impl;

import com.huang.mapper.BlogMapper;
import com.huang.mapper.UserMapper;
import com.huang.pojo.Blog;
import com.huang.pojo.Users;
import com.huang.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    UserMapper userMapper;

    Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Override
    public List<Blog> AllBlog() {
        List<Blog> allBlogs = blogMapper.findAllBlogs();
        if (allBlogs == null){
            throw new NullPointerException("查不到blog");
        }
        return allBlogs;
    }

    @Override
    public Boolean UpdateBlog(MultipartFile file, Map<String,String> map) {
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        System.out.println(map.get("textarea")+ map.get("user_mail"));
        //查找用户邮箱对应的用户id
        Users user = userMapper.findUsersByMail(map.get("user_mail"));
        //保存图片
        File dest = new File("D://xiaodanBlog/picture/"+originalFilename);
        try {
            //将文件转移到给定的路径（保存）
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("保存异常");
        }
        //更新内容
        Blog blog = new Blog();
        blog.setTitle(map.get("title"));
        blog.setContent(map.get("textarea"));
        blog.setUsers(user);
        blog.setPic("/picture/"+originalFilename);
        Integer res = blogMapper.UploadBlog(blog);
        if (res == 1){
            return true;
        }
        return false;
    }
}
