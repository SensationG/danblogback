package com.huang;

import com.huang.mapper.BlogMapper;
import com.huang.mapper.UserMapper;
import com.huang.pojo.Blog;
import com.huang.pojo.Users;
import com.huang.service.UsersService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class DanblogApplicationTests {

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    UsersService usersService;


    /**
     * 测试mybatis是否能了连通数据库
     * @throws SQLException
     */
    @Test
    void MybatisTest() throws SQLException {
        System.out.println(dataSource.getConnection());
        System.out.println(dataSource.getClass());
        //不报错即可
    }

    /**
     * 根据邮箱查找用户信息
     */
    @Test
    void UsersMapperTest(){
        Users users = userMapper.findUsersByMail("2123123@qq.com");
        System.out.println(users);
    }

    /**
     * 查找全部博客/根据所属用户查找用户信息
     */
    @Test
    void BlogMapperTest(){
        List<Blog> allBlogs = blogMapper.findAllBlogs();
        for(Blog blog:allBlogs){
            System.out.println(blog);
        }
    }


    /**
     * 用户service测试
     */
    @Test
	void UserServiceTest() {
        Users user = usersService.findUserByMail("2123123@qq.com");
        System.out.println(user);
    }

    /**
     * 用户注册
     */
    @Test
    void UserRegiestTest(){
        Users users = new Users();
        users.setMail("954123@qq.com");
        users.setPasswd("dwqeqw");
        users.setUsername("hhw");
        Boolean res = usersService.UserRegiest(users);
        System.out.println(res);
    }

}
