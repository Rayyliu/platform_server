package com.platform.controller.excutecontroller;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.entity.ResponseResult;
import com.platform.entity.dto.CaseParametersDTO;
import com.platform.response.ResultCode;
import com.platform.util.HttpRequestUntil;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("single/case")
public class SingleCaseExecuteController {

    @Autowired
    HttpRequestUntil httpRequestUntil;

    @SneakyThrows
    @PostMapping("execute")
    @ApiOperation("单用例执行")
    public ResponseResult execute(@RequestBody CaseParametersDTO caseParametersDTO) {
        //封装get/post请求方法
        JSONObject result = httpRequestUntil.httpRequest(caseParametersDTO);
        System.out.println(result);
        return new ResponseResult().success(ResultCode.FAIL.getCode(),true,"用例执行",result);
    }
}
