package com.platform.entity.po;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.ManyToMany;
import java.util.List;

@Data
public class SysPermission {

    private Long id;

    /***
     * 权限名称，如user:select
     */
    private String name;

    /***
     * 权限描述，用于UI展示
     */
    private String description;

    /***
     * 权限地址
     */
    private String url;


    @JsonIgnoreProperties(value = {"permissions"})
    @ManyToMany
    private List<SysRole> roles;
}
