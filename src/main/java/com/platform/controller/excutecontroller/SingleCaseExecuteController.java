package com.platform.controller.excutecontroller;


import com.alibaba.fastjson.JSONObject;
import com.platform.entity.ExecuteResultEntity;
import com.platform.entity.ResponseResult;
import com.platform.entity.dto.CaseParametersDTO;
import com.platform.response.ResultCode;
import com.platform.util.AssertionUtil;
import com.platform.util.HttpRequestUntil;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
        //断言
        List<String> assertResult = caseParametersDTO.getAssertionEntity().stream().map(
                item -> AssertionUtil.assertUtil(result.getString(item.getParameter()), item.getExcept(), item.getRule()))
                .collect(Collectors.toList());
        System.out.println(assertResult);
        System.out.println(result);
        ExecuteResultEntity executeResultEntity =new ExecuteResultEntity();
        executeResultEntity.setInterFaceResult(result);
        executeResultEntity.setExecuteResult(assertResult);
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(), true, "用例执行成功", executeResultEntity);
    }
}
