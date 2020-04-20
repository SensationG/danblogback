package com.huang.mapper;


import com.huang.pojo.Users;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserMapper {

    /**
     * 根据邮箱查找用户信息
     * @param mail
     * @return
     */
    Users findUsersByMail(String mail);

    /**
     * 根据id查找用户信息
     * @param id
     * @return
     */
    Users findUsersById(Integer id);

    /**
     * 用户注册
     * @param users
     * @return
     */
    Integer UserRegiest(Users users);

    /**
     * 根据邮箱更新用户头像url
     * @param map
     * @return
     */
    Integer UpdatePhoto(Map<String,String> map);

}
