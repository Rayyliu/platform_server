package com.platform.controller;

import com.platform.client.UserService;
import com.platform.entity.UserEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("user/")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("register")
    @ApiOperation("注册")
    public void register(@RequestBody UserEntity user){
        userService.save(user);
    }
}
