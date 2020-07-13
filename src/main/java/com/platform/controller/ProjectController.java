package com.platform.controller;

import com.platform.client.ProjectService;
import com.platform.entity.ResponseResult;
import com.platform.entity.dto.ProjectDTO;
import com.platform.response.ResultCode;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("project/")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PostMapping("save")
    @ApiOperation("新增项目")
    public ResponseResult saveProject(@RequestBody ProjectDTO projectDTO){
        projectService.addProject(projectDTO);
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"项目新增成功",projectDTO);
    }
}
