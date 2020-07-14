package com.platform.controller;

import com.platform.client.ProjectService;
import com.platform.entity.PageEntity;
import com.platform.entity.ResponseResult;
import com.platform.entity.dto.ProjectDTO;
import com.platform.response.ResultCode;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project/")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PostMapping("save")
    @ApiOperation("新增项目")
    public ResponseResult saveProject(@RequestBody Object projectDTO) {
        projectService.addProject(projectDTO);
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(), true, "项目新增成功", projectDTO);
    }

    @GetMapping("query")
    @ApiOperation("项目列表查询")
    public ResponseResult qure(int page,int pageSize) {
        List list = projectService.query(page,pageSize);
        int count = list.size();
        PageEntity pageEntity = new PageEntity(page,pageSize,new ProjectDTO(),count);
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"查询列表成功",pageEntity);
    }
}
