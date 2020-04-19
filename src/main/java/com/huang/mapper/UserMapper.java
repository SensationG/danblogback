package com.huang.mapper;


import com.huang.pojo.Users;
import org.springframework.stereotype.Repository;

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

}
