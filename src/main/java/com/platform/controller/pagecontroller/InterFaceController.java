package com.platform.controller.pagecontroller;

import com.alibaba.fastjson.JSON;
import com.platform.client.frontend.InterFaceService;
import com.platform.entity.PageEntity;
import com.platform.entity.ResponseResult;
import com.platform.entity.dto.InterFaceDTO;
import com.platform.response.ResultCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("interface/")
public class InterFaceController {

    @Autowired
    InterFaceService interFaceService;

    @PostMapping("add")
    @ApiOperation("添加新接口")
    public ResponseResult addInterFace(@RequestBody Map<String,Object> interfaceData){
        int result = interFaceService.addInterFace(interfaceData);
        if(result == 1){
            return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"添加成功",null);
        }else {
            return new ResponseResult().success(ResultCode.FAIL.getCode(),true,"添加失败",null);
        }
    }

    @GetMapping("queryAll")
    @ApiOperation("查询所有接口")
    public ResponseResult queryAllInterFace(){
        Object result = interFaceService.queryAll();
            return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"删除成功",result);
    }

    @GetMapping("queryDistInterFace")
    @ApiOperation("查询接口列表")
    public ResponseResult queryDistInterFace(){
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"查询所有接口名成功",interFaceService.queryInterFace());
    }


    @GetMapping(value = "queryPage",produces = "application/json")
    @ApiOperation("分页查询")
    public ResponseResult queryPage(int pageNum,int pageSize,@RequestParam(value = "interfaceName",required = false) String interfaceName){
        PageEntity pageEntity = new PageEntity(pageNum,pageSize,interFaceService.queryPage(pageNum,pageSize,interfaceName),interFaceService.queryAll());
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"查询接口列表成功",pageEntity);
    }

    @PostMapping("edit")
    @ApiOperation("编辑接口详情")
    public ResponseResult edit(@RequestBody Map<String,Object> interfaceData){
        int result = interFaceService.edit(interfaceData);
        if(result == 1){
            return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"添加成功",null);
        }else {
            return new ResponseResult().success(ResultCode.FAIL.getCode(),true,"添加失败",null);
        }
    }

    @GetMapping(value = "findByName",produces = "application/json")
    @ApiOperation("根据接口名查询接口信息")
    public ResponseResult findByName(String interfaceName){
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"查询接口详情成功",interFaceService.queryByName(interfaceName));
    }
}
