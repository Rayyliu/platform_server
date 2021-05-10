package com.platform.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platform.entity.UserEntity;
import lombok.Data;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
public class SysRole {

    private Long id;
    private String name;
    private String description;


    /***
     * 角色--权限  关系；多对多
     */
    @ManyToMany
    @JsonIgnoreProperties(value = {"roles"})
    @JoinTable
    private List<SysPermission> permissions;


    /***
     * 角色--用户  关系；多对多
     */
    @ManyToMany
    @JsonIgnoreProperties(value = {"roles"})
    private List<UserEntity> userEntity;
}
