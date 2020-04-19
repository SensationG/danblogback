package com.huang.service;

import com.huang.pojo.Users;

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

}
