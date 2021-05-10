package com.platform.entity;

import com.platform.entity.po.SysRole;
import lombok.Data;

import java.util.List;

@Data
public class UserEntity {

    /**
     * 注册手机号
     */
    private String mobile;
    /**
     * 账户密码
     */
    private String password;
    /**
     * 账号邮箱
     */
    private String email;
    /**
     * 账号状态
     */
    private String status;
    /**
     * 注册角色
     */
    private String role;

    /***
     * 角色
     */
    private List<SysRole> roles;
}
