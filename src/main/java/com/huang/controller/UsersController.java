package com.huang.controller;

import com.huang.pojo.Users;
import com.huang.service.UsersService;
import com.huang.service.impl.UsersServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    private static Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    /**
     * 测试用
     * @return
     */
    @ResponseBody
    @RequestMapping("/test1")
    public String PostTest(){
        return "hello,test succeed";
    }


    /**
     * 用户注册
     * 返回自定义状态码 200:成功 444:失败
     * @param users
     * @param
     */
    @PostMapping("/regiest")
    @ResponseBody
    public String UserRegiest(@RequestBody Users users){
        //返回true则注册成功
        Boolean res = usersService.UserRegiest(users);
        //根据结果返回自定义状态码
        if(res){
            logger.info("返回200状态码");
            return "200";
        }else {
            logger.info("返回444状态码");
            return "444";
        }
    }

    /**
     * 获取用户信息by mail
     * @param
     * @return
     */
    @GetMapping("/getUser")
    @ResponseBody
    public Users getUserByMail(Authentication authentication){
        //从spring security 获取用户信息
        String mail = authentication.getName();
        Users user = usersService.findUserByMail(mail);
        //密码置空不返回
        user.setPasswd("");
        return user;

    }

    /**
     * 更换用户头像
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    //形参接收MultipartFile类型
    public void uploadFile(@RequestParam(value = "file",required = false) MultipartFile file,
                           Authentication authentication,HttpServletResponse response,
                           Map<String,String> map){
        if(!file.isEmpty()){
            //获取图片原始名称
            String originalFilename = file.getOriginalFilename();
            //准备保存的路径（建议保存到单独的目录存放，不要保存在服务器目录）
            //这里建议不要写死，而是从配置文件读取路径
            File dest = new File("D://xiaodanBlog/photo/"+originalFilename);
            try {
                //将文件转移到给定的路径（保存）
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("保存异常");
            }
            logger.info("保存完成");
            //把新的图片url写入对应用户photo中
            map.put("mail",authentication.getName());
            map.put("photo","/photo/" + originalFilename);

            Boolean res = usersService.UpdataPhoto(map);
            if(res){
                logger.info("照片更改成功");
                response.setStatus(200);
            }else {
                logger.error("照片更改失败");
                response.setStatus(445);
            }
        }else {
            response.setStatus(446);
        }

    }

}
