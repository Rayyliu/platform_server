package com.platform.entity.dto;

import lombok.Data;

@Data
public class EnvDTO extends PlatformBaseDTO{

    private int id;
    private String envName;
    private String url;
    private String project;
    private String envDescription;

}
