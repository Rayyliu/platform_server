package com.platform.entity;


import lombok.Data;

@Data
public class TokenEntity {

    //用户名（邮箱）
    private String email;

    //用户手机号
    private String mobile;

    //通过email、mobile、role生成的token
    private String token;

    //用户角色
    private String role;

}
