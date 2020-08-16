package com.platform.controller.excutecontroller;


import com.alibaba.fastjson.JSONObject;
import com.platform.client.ExecuteService;
import com.platform.client.frontend.InterFaceService;
import com.platform.entity.AssertionEntity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("single/case")
public class SingleCaseExecuteController {

    @Autowired
    HttpRequestUntil httpRequestUntil;

    @Autowired
    ExecuteService executeService;

    @Autowired
    InterFaceService interFaceService;

    @SneakyThrows
    @PostMapping("execute")
    @ApiOperation("单用例执行")
    public ResponseResult execute(@RequestBody CaseParametersDTO caseParametersDTO) {
        //封装get/post请求方法
        //调用接口实际返回结果/用例执行结果
        JSONObject response = httpRequestUntil.httpRequest(caseParametersDTO);
        //断言
        List<String> assertResult = caseParametersDTO.getAssertionEntity().stream().map(
                item -> AssertionUtil.assertUtil(response.getString(item.getParameter()), item.getExcept(), item.getRule(),item.getKey()))
                .collect(Collectors.toList());
        System.out.println(assertResult);
        System.out.println(response);

        ExecuteResultEntity executeResultEntity =new ExecuteResultEntity();

        //接口实际返回结果
        executeResultEntity.setResponse(response);

        //断言结果
        executeResultEntity.setAssertionExecuteResult(assertResult);

        //断言内容
        List<AssertionEntity> assertionContent = caseParametersDTO.getAssertionEntity();

        //用例执行结果：true/false
        boolean caseExecuteResult = assertResult.stream().allMatch(caseResult->caseResult.contains("断言成功"));
        System.out.println("assertResult==="+assertResult);
        System.out.println("caseExecuteResult==="+caseExecuteResult);
        System.out.println("executeResultEntity==="+executeResultEntity);

        //查询interface_id
        int interfaceId = interFaceService.queryByName(caseParametersDTO.getInterFaceName()).getId();
        System.out.println(interfaceId);


        Map<String,Object> record = new HashMap<>();
        record.put("caseName",caseParametersDTO.getCaseName());
        record.put("interfaceId",interfaceId);
        record.put("project",caseParametersDTO.getProject());
        record.put("body",caseParametersDTO.getBody());
        record.put("response",response);
        record.put("caseExecuteResult",caseExecuteResult);
        record.put("assertionContent",assertionContent);
        record.put("assertResult",assertResult);
        record.put("last_execute_user",caseParametersDTO.getLastExecuteUser());
//        record.put("creat_at", System.currentTimeMillis());
//        record.put("update_at",System.currentTimeMillis());
        executeService.insertAndRun(record);
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(), true, "用例执行成功", executeResultEntity);
    }
}
