package com.platform.controller.pagecontroller;

import com.platform.client.frontend.EnvService;
import com.platform.entity.PageEntity;
import com.platform.entity.ResponseResult;
import com.platform.entity.dto.EnvDTO;
import com.platform.response.ResultCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("env/")
public class EnvController {

    @Autowired
    EnvService envService;

    @PostMapping("add")
    @ApiOperation("新增环境")
    public ResponseResult addEnv(@RequestBody  EnvDTO env){
        int result = envService.addEnv(env);
        if (result ==1) {
            return new ResponseResult().success(ResultCode.SUCCESS.getCode(), true, "新增环境成功", result);
        }else {
            return new ResponseResult().success(ResultCode.FAIL.getCode(), true, "新增环境失败", result);
        }
    }

    @GetMapping("queryPage")
    @ApiOperation("分页查询")
    public ResponseResult queryPage(int pageNum,int pageSize,@RequestParam(value = "envName",required = false) String envName){
        PageEntity pageEntity = new PageEntity(pageNum,pageSize,envService.queryPage(pageNum,pageSize,envName),envService.queryTotal());
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"查询列表成功",pageEntity);
    }

    @GetMapping("singleDelet")
    @ApiOperation("单挑删除环境记录")
    public ResponseResult singleDelet(String envId){
        int result = envService.singleDelet(envId);
        if(result == 1){
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"删除成功",envService.singleDelet(envId));
    }else {
            return new ResponseResult().success(ResultCode.FAIL.getCode(),true,"删除失败",envService.singleDelet(envId));
        }
    }

    @GetMapping("deletes")
    @ApiOperation("批量删除环境")
    public ResponseResult deletes(String[] ids){
        envService.deletes(ids);
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"批量删除成功",null);
    }

    @PostMapping("edit")
    @ApiOperation("修改环境配置")
    public ResponseResult edit(@RequestBody EnvDTO envDTO){
        envService.update(envDTO);
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"编辑环境成功",null);
    }
}

