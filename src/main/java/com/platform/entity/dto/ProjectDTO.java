package com.platform.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProjectDTO extends PlatformBaseDTO{

    private int id;
    private String projectName;
    private String projectDescription;
    private String projectModule;
    private String tester;
    private boolean valid;
}
