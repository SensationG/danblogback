package com.huang.mapper;

import com.huang.pojo.Blog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BlogMapper {

    /**
     * 查找全部博客的内容，并根据所属用户id查找用户信息
     * @return
     */
    List<Blog> findAllBlogs();

    Integer UploadBlog(Blog blog);
}
