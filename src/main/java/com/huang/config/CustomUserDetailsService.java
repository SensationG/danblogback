package com.huang.config;

import com.huang.pojo.Users;
import com.huang.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * Spring Security 访问数据库获取用户数据
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String UserMail) throws UsernameNotFoundException {

        //访问数据库
        Users user = usersService.findUserByMail(UserMail);

        //密码加密方式,如果数据库没有加密，这里要加密
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        //用户权限 要求返回一个集合类型
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_customer"));//默认是ROLE_开头，对应权限customer

        //返回需要保存的用户数据
        return new User(user.getMail(),user.getPasswd(),grantedAuthorities);
    }
}
