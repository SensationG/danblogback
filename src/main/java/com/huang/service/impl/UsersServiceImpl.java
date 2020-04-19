package com.huang.service.impl;

import com.huang.mapper.UserMapper;
import com.huang.pojo.Users;
import com.huang.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    private static Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Boolean UserRegiest(Users user) {
        //结果
        Integer res = null;
        logger.info(user.toString());
        //判断用户是否为空，为空直接返回false
        if(user == null){
            logger.info("用户为空");
            return false;
        }
        //密码加密操作
        String passwd = user.getPasswd();
        user.setPasswd(bCryptPasswordEncoder.encode(passwd));
        logger.info(user.toString());
        //执行数据库插入操作
        try{
            res = userMapper.UserRegiest(user);
        }catch (Exception e){
            logger.error("用户注册异常 "+e);
            return false;
        }
        //改变行数为1则插入成功
        if(res == 1){
            logger.info("注册成功");
            return true;
        }
        return false;
    }

    @Override
    public Users findUserByMail(String mail) {
        Users user = null;
        if(mail == null){
            throw new NullPointerException("mail为空");
        }
        try {
            user = userMapper.findUsersByMail(mail);
        }catch (Exception e){
            logger.error("查找用户异常 "+e);
        }
        if(user == null){
            logger.info("用户为空");
            return null;
        }
        return user;
    }
}
