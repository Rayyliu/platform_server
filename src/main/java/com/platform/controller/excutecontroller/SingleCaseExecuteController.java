package com.platform.controller.excutecontroller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.platform.client.ExecuteService;
import com.platform.client.frontend.InterFaceService;
import com.platform.client.service.CaseExecuteService;
import com.platform.entity.AssertionEntity;
import com.platform.entity.ExecuteResultEntity;
import com.platform.entity.PageEntity;
import com.platform.entity.ResponseResult;
import com.platform.entity.dto.CaseParametersDTO;
import com.platform.entity.dto.RequestParameterDTO;
import com.platform.response.ResultCode;
import com.platform.tools.InterFaceDetail;
import com.platform.util.AssertionUtil;
import com.platform.util.HttpRequestUntil;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("single/case")
public class SingleCaseExecuteController {

    @Autowired
    ExecuteService executeService;


    @Autowired
    CaseExecuteService caseExecuteService;

    @SneakyThrows
    @PostMapping("execute")
    @ApiOperation("单用例新增及执行")
    public ResponseResult execute(@RequestBody RequestParameterDTO valuesArr) {
            return new ResponseResult().success(ResultCode.SUCCESS.getCode(), true, "用例执行成功",caseExecuteService.caseExecute(valuesArr));
    }

    @GetMapping("queryPage")
    @ApiOperation("分页查询和搜索")
    public ResponseResult queryPage(int pageNum, int pageSize, @RequestParam(value = "caseName",required = false)String caseName){
        PageEntity pageEntity = new PageEntity(pageNum,pageSize, executeService.queryPage(pageNum, pageSize, caseName),executeService.queryAll());
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(), true, "用例执行成功", pageEntity);
    }
}
