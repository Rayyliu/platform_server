package com.platform.controller;

import com.platform.client.UserService;
import com.platform.entity.ResponseResult;
import com.platform.entity.TokenEntity;
import com.platform.entity.UserEntity;
import com.platform.model.Audience;
import com.platform.response.ResultCode;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

import static com.platform.util.JwtTokenUtil.creatToken;

@RestController
@RequestMapping("user/")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    Audience audience;

    @Autowired
    ResponseResult responseResult;

    @PostMapping("register")
    @ApiOperation("注册")
    public ResponseResult register(@RequestBody UserEntity user){
        userService.save(user);
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setEmail(user.getEmail());
        tokenEntity.setMobile(user.getMobile());
        tokenEntity.setRole(user.getRole());
        String token = creatToken(user.getEmail(), user.getRole(), audience);
        tokenEntity.setToken(token);
        return responseResult.success(ResultCode.SUCCESS.getCode(),true,"注册成功",tokenEntity);
    }

    @GetMapping("findByUsername")
    @ApiOperation("查询用户")
    public ResponseResult queryUserInformation(String userName) {
        Object user = userService.findUser(userName);
        if (user == null) {
            return responseResult.success(ResultCode.FAIL.getCode(), true, "查询失败", user);
        } else {
            return responseResult.success(ResultCode.SUCCESS.getCode(), true, "查询成功", user);
        }
    }

    @PostMapping("login")
    @ApiOperation("登录操作获取用户信息")
    public ResponseResult login(@RequestBody UserEntity userEntity){
        System.out.println("登录账号："+userEntity.getEmail());
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();

        //2.封装用户数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userEntity.getEmail(),userEntity.getPassword());

        //3.执行登录方法
        subject.login(usernamePasswordToken);

        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setEmail(userEntity.getEmail());
        tokenEntity.setRole(userEntity.getRole());
        String token = creatToken(userEntity.getEmail(), userEntity.getRole(), audience);
        tokenEntity.setToken(token);
        return responseResult.success(ResultCode.SUCCESS.getCode(),true,"登录成功",tokenEntity);
    }
}
