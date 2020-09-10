package com.platform.controller.excutecontroller;


import com.alibaba.fastjson.JSONObject;
import com.platform.client.ExecuteService;
import com.platform.client.frontend.InterFaceService;
import com.platform.entity.AssertionEntity;
import com.platform.entity.ExecuteResultEntity;
import com.platform.entity.PageEntity;
import com.platform.entity.ResponseResult;
import com.platform.entity.dto.CaseParametersDTO;
import com.platform.entity.dto.InterFaceDTO;
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
    HttpRequestUntil httpRequestUntil;

    @Autowired
    ExecuteService executeService;

    @Autowired
    InterFaceService interFaceService;

    @Autowired
    InterFaceDetail interFaceDetail;

    @SneakyThrows
    @PostMapping("execute")
    @ApiOperation("单用例新增及执行")
    public ResponseResult execute(@RequestBody List<CaseParametersDTO> caseParametersDTOList) {

        ExecuteResultEntity executeResultEntity = new ExecuteResultEntity();
        //判断是否是新增时运行用例
        for (CaseParametersDTO caseParametersDTO : caseParametersDTOList) {
            if (caseParametersDTO.isAdd() == false) {
                caseParametersDTO.setMethod(caseParametersDTO.getMethod());
                caseParametersDTO.setBody(caseParametersDTO.getBody());
                caseParametersDTO.setHeader(caseParametersDTO.isHeader());
                caseParametersDTO.setPath(caseParametersDTO.getPath());
                caseParametersDTO.setHeaderDetail(caseParametersDTO.getHeaderDetail());
                caseParametersDTO.setSign(caseParametersDTO.isSign());
                caseParametersDTO.setSignEntity(caseParametersDTO.getSignEntity());
                caseParametersDTO.setAssertionContent(caseParametersDTO.getAssertionContent());
            }
            //调用接口实际返回结果/用例执行结果
            List<JSONObject> response = httpRequestUntil.httpRequest(caseParametersDTO);
            //断言
            List<String> assertResult = new ArrayList<>();
            List<String> assertResults = new ArrayList<>();
            int i;
            for (i = 0; i < response.size(); i++) {
                JSONObject responseData = response.get(i);
                int finalI = i;
                assertResult = caseParametersDTO.getAssertionContent().stream().map(
                        item -> AssertionUtil.assertUtil(caseParametersDTO.getBody().get(finalI).getKey(), responseData.getString(item.getParameter()), item.getExcept(), item.getRule(), item.getKey()))
                        .collect(Collectors.toList());
                assertResults.addAll(assertResult);
                System.out.println(assertResult);
                System.out.println(assertResults);
                System.out.println(response);
            }



            //接口实际返回结果
            executeResultEntity.setResponse(response);

            //断言结果
            executeResultEntity.setAssertionExecuteResult(assertResults);

            //断言内容
            List<AssertionEntity> assertionContents = caseParametersDTO.getAssertionContent();

            //用例执行结果：true/false
            boolean caseExecuteResult = assertResult.stream().allMatch(caseResult -> caseResult.contains("断言成功"));
            System.out.println("assertResult===" + assertResult);
            System.out.println("caseExecuteResult===" + caseExecuteResult);
            System.out.println("executeResultEntity===" + executeResultEntity);

            //添加用例且执行用例
            if (caseParametersDTO.isAdd() == true) {
                Map<String, Object> record = new HashMap<>();
                record.put("caseName", caseParametersDTO.getCaseName());
                record.put("interfaceId", caseParametersDTO.getInterfaceId());
                record.put("project", caseParametersDTO.getProject());
                record.put("body", caseParametersDTO.getBody());
                record.put("response", response);
                record.put("caseExecuteResult", caseExecuteResult);
                record.put("assertionContent", assertionContents);
                record.put("assertResult", assertResults);
                record.put("lastExecuteTime", new Date());
                record.put("last_execute_user", caseParametersDTO.getLastExecuteUser());
                record.put("valid", caseParametersDTO.isValid());
                record.put("description", caseParametersDTO.getDescription());
//        record.put("creat_at", System.currentTimeMillis());
//        record.put("update_at",System.currentTimeMillis());
                executeService.insertAndRun(record);
            } else {
                //调试用例，只修改调试时间、调试结果、调试人员、接口实际返回值等
                Map<String, Object> record = new HashMap<>();
                record.put("id", caseParametersDTO.getId());
                record.put("lastExecuteUser", caseParametersDTO.getLastExecuteUser());
                record.put("lastExecuteTime", new Date());
                record.put("response", response);
                record.put("caseExecuteResult", caseExecuteResult);
                record.put("assertResult", assertResult);
                executeService.editExecuteRecord(record);
            }
        }
            return new ResponseResult().success(ResultCode.SUCCESS.getCode(), true, "用例执行成功", executeResultEntity);

    }

    @GetMapping("queryPage")
    @ApiOperation("分页查询和搜索")
    public ResponseResult queryPage(int pageNum, int pageSize, @RequestParam(value = "caseName",required = false)String caseName){
        PageEntity pageEntity = new PageEntity(pageNum,pageSize, executeService.queryPage(pageNum, pageSize, caseName),executeService.queryAll());
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(), true, "用例执行成功", pageEntity);
    }
}
