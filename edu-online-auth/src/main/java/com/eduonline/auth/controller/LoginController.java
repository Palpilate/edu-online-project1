package com.eduonline.auth.controller;

import com.eduonline.ucenter.mapper.EduUserMapper;
import com.eduonline.ucenter.model.po.EduUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anesthesia
 * @version 1.0
 * @description 测试controller
 * @date 2023/3/31 17:25
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    EduUserMapper userMapper;


    @RequestMapping("/login-success")
    public String loginSuccess() {

        return "登录成功";
    }


    @RequestMapping("/user/{id}")
    public EduUser getuser(@PathVariable("id") String id) {
        EduUser eduUser = userMapper.selectById(id);
        return eduUser;
    }

    @RequestMapping("/r/r1")
    public String r1() {
        return "访问r1资源";
    }

    @RequestMapping("/r/r2")
    public String r2() {
        return "访问r2资源";
    }



}
