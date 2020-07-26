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

import java.util.HashMap;
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
            return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"删除成功",null);
        }else {
            return new ResponseResult().success(ResultCode.FAIL.getCode(),true,"删除失败",null);
        }
    }

    @GetMapping("queryAll")
    @ApiOperation("查询所有接口")
    public ResponseResult queryAllInterFace(){
        Object result = interFaceService.queryAll();
            return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"删除成功",result);
    }

    @GetMapping("queryPage")
    @ApiOperation("分页查询")
    public ResponseResult queryPage(int pageNum,int pageSize,@RequestParam(value = "interfaceName",required = false) String interfaceName){
        PageEntity pageEntity = new PageEntity(pageNum,pageSize,interFaceService.queryPage(pageNum,pageSize,interfaceName),interFaceService.queryAll());
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(),true,"查询接口列表成功",pageEntity);
    }

}
