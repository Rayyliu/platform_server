package com.platform.controller.pagecontroller;

import com.alibaba.fastjson.JSON;
import com.platform.client.frontend.InterFaceService;
import com.platform.entity.ResponseResult;
import com.platform.entity.dto.InterFaceDTO;
import com.platform.response.ResultCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
