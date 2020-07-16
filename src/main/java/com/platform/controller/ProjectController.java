package com.platform.controller;

import com.platform.client.ProjectService;
import com.platform.entity.PageEntity;
import com.platform.entity.ResponseResult;
import com.platform.entity.dto.ProjectDTO;
import com.platform.response.ResultCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
        PageEntity pageEntity = new PageEntity(page,pageSize,projectService.query(page,pageSize),projectService.queryTotal());
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"查询列表成功",pageEntity);
    }

    @PostMapping("edit")
    @ApiOperation("项目编辑")
    public ResponseResult editProject(@RequestBody ProjectDTO projectDTO){
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"查询列表成功",projectService.updateProject(projectDTO));
    }

    @GetMapping("queryById")
    @ApiOperation("通过id查询项目详情")
    public ResponseResult queryById(int projectId){
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"查询项目成功",projectService.queryById(projectId));
    }

    @GetMapping("delById")
    @ApiOperation("单挑删除项目")
    public ResponseResult singleDelete(String projectId){
        projectService.singleDelete(projectId);
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"项目删除成功",null);
    }

    @GetMapping("updateValid")
    @ApiOperation("更新项目可用状态")
    public ResponseResult updateValid(String projectId,boolean valid){
        projectService.updateValid(projectId,valid);
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"项目可用状态已更新成功",null);
    }
}
