package com.platform.controller.excutecontroller;


import com.platform.client.service.CaseExecuteService;
import com.platform.entity.ResponseResult;
import com.platform.entity.dto.PlanDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

@RestController
@RequestMapping("plan/")
@Transactional(rollbackFor = Exception.class)
public class ExecuteTestPlanController {

    @Autowired
    CaseExecuteService caseExecuteService;


    @PostMapping("execute")
    @ApiOperation("执行测试计划")
    public ResponseResult execute(@RequestBody PlanDTO plan){
//        plan.getPlanContent();
        System.out.println("plan==="+plan);
        caseExecuteService.planExecute(plan);
        return null;
    }
}
