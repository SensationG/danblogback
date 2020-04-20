package com.huang.service;

import com.huang.pojo.Users;

import java.util.Map;

public interface UsersService {

    /**
     * 用户注册
     * @return
     */
    Boolean UserRegiest(Users user);

    /**
     * 根据邮箱查找用户信息
     * @param mail
     * @return
     */
    Users findUserByMail(String mail);

    /**
     * 更换用户头像
     * @param map
     * @return
     */
    Boolean UpdataPhoto(Map<String,String> map);

}
