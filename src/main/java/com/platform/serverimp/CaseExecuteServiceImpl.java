package com.platform.serverimp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.platform.client.ExecuteService;
import com.platform.client.service.CaseExecuteService;
import com.platform.entity.AssertionEntity;
import com.platform.entity.ExecuteResultEntity;
import com.platform.entity.dto.CaseParametersDTO;
import com.platform.entity.dto.RequestParameterDTO;
import com.platform.util.AssertionUtil;
import com.platform.util.HttpRequestUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CaseExecuteServiceImpl implements CaseExecuteService {

    @Autowired
    HttpRequestUntil httpRequestUntil;

    @Autowired
    ExecuteService executeService;

    @Override
    public ExecuteResultEntity caseExecute(RequestParameterDTO requestParameterDTO) {
        String[] extractArr=null;
        Map<String,Object> extractMap=new HashMap<>();
        ExecuteResultEntity executeResultEntity = new ExecuteResultEntity();
        //判断是否是新增时运行用例
        for (CaseParametersDTO caseParametersDTO : requestParameterDTO.getValuesArr()) {
            //获取提取参数的value
            //逗号分开提取参数
            if(caseParametersDTO.getExtract()!=null) {
                extractArr = caseParametersDTO.getExtract().split(",");
                //冒号分开该接口需要的参数和上一个接口提供的参数值
                List<String[]> extractParameter = Arrays.stream(extractArr).map(item -> item.split(":")).collect(Collectors.toList());
                extractParameter.stream().forEach(item -> {
                    Object extractValue = extractMap.get(item[item.length - 1]);
                    if (extractValue != null) {
                        //body请求体
                        Map caseDataMap = JSONObject.parseObject(caseParametersDTO.getBody().get(0).getCaseData());
                        //header请求头
                        Map headerDataMap = caseParametersDTO.getHeaderDetail();
                        if (caseDataMap.containsKey(item[0])) {
                            caseDataMap.put(item[0], extractValue);
                            String caseData = JSON.toJSONString(caseDataMap);
                            caseParametersDTO.getBody().get(0).setCaseData(caseData);
                        } else if (headerDataMap.containsKey(item[0])) {
                            headerDataMap.put(item[0], extractValue);
                            caseParametersDTO.setHeaderDetail(JSONObject.parseObject(JSON.toJSONString(headerDataMap)));
                        }
                    }
                });
            }
//            }
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
            if(extractArr!=null) {
                Arrays.stream(extractArr).map(item -> extractMap.put(item, response.get(0).getString(item.substring(item.lastIndexOf(".") + 1)))).collect(Collectors.toList());
            }
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
        return executeResultEntity;
    }
}
