package com.huang.service;

import com.huang.pojo.Blog;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BlogService {

    /**
     * 查找所有博客
     * @return
     */
    List<Blog> AllBlog();

    /**
     * 上传博客
     * @return
     */
    Boolean UpdateBlog(MultipartFile file, Map<String,String> map);
}
