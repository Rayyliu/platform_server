package com.platform.entity.dto;

import lombok.Data;

@Data
public class ProjectDTO {

    private String projectName;
    private String description;
    private String projectModule;
    private String tester;
    private boolean isValid;
}
