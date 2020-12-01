package com.platform.serverimp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.platform.client.ExecuteService;
import com.platform.client.frontend.EnvService;
import com.platform.client.frontend.InterFaceService;
import com.platform.client.service.CaseExecuteService;
import com.platform.entity.AssertionEntity;
import com.platform.entity.ExecuteResultEntity;
import com.platform.entity.dto.CaseParametersDTO;
import com.platform.entity.dto.InterFaceDTO;
import com.platform.entity.dto.PlanDTO;
import com.platform.entity.dto.RequestParameterDTO;
import com.platform.util.AssertionUtil;
import com.platform.util.HttpRequestUntil;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional(rollbackFor = Exception.class)
public class CaseExecuteServiceImpl implements CaseExecuteService {

    @Autowired
    HttpRequestUntil httpRequestUntil;

    @Autowired
    ExecuteService executeService;

    @Autowired
    EnvService envService;

    @Autowired
    InterFaceService interFaceService;

    @Override
    public ExecuteResultEntity caseExecute(RequestParameterDTO requestParameterDTO) {

        for (CaseParametersDTO caseParametersDTO : requestParameterDTO.getValuesArr()) {
            return executeCore(caseParametersDTO);
        }
        return null;
    }

    @SneakyThrows
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ExecuteResultEntity planExecute(PlanDTO plan) {
        List<String> content= JSONArray.parseArray(plan.getPlanContent(),String.class);
        System.out.println("content==="+content);
        List<CaseParametersDTO> cases= content.stream().map(item->executeService.queryByCaseName(item)).collect(Collectors.toList());
        for(CaseParametersDTO caseParametersDTO:cases){
            log.info("执行用例："+caseParametersDTO.getCaseName());
            InterFaceDTO interFaceDTO= interFaceService.queryByInterfaceId(executeService.queryByCaseName(caseParametersDTO.getCaseName()).getInterfaceId());
            String requestURL=null;
            if(!getUrlPrefix(interFaceDTO.getPath())){
                URL url =new URL(interFaceDTO.getPath());
                String host = envService.getPathByEnvName(plan.getEnv());
                requestURL = interFaceDTO.getPath().replace(url.getHost(),host);
            }else {
                requestURL = interFaceDTO.getPath();
            }
            caseParametersDTO.setPath(requestURL);
            caseParametersDTO.setMethod(interFaceDTO.getMethod());
            caseParametersDTO.setInterFaceName(interFaceDTO.getInterfaceName());
            caseParametersDTO.setHeaderDetail(JSONObject.parseObject(interFaceDTO.getHeaderdetail()));
            caseParametersDTO.setHeader(interFaceDTO.isHeader());
            caseParametersDTO.setSign(interFaceDTO.isSign());
            caseParametersDTO.setSignAttribute(interFaceDTO.getSignAttribute());
            caseParametersDTO.setSignEntity(interFaceDTO.getSignEntity());
            executeCore(caseParametersDTO);
        }
//        cases.stream().forEach(item-> {
//            try {
//                item.setPath(interFaceService.queryByInterfaceId(executeService.queryByCaseName(item.getCaseName()).getInterfaceId()).getPath().replace(new URL(item.getPath()).getHost(),item.getPath()));
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//        };
//        cases.stream().forEach(item->item.setPath(envService.getPathByEnvName(plan.getEnv())));
//        cases.stream().forEach(item->item.setMethod(interFaceService.queryByInterfaceId(executeService.queryByCaseName(item.getCaseName()).getInterfaceId()).getMethod()));
//        return cases.stream().map(item->executeCore(item));
//        for(CaseParametersDTO caseParametersDTO:cases){
//            return executeCore(caseParametersDTO);
//        }
        return null;
    }


    public ExecuteResultEntity executeCore(CaseParametersDTO caseParametersDTO){
        String[] extractArr=null;
        Map<String,Object> extractMap=new HashMap<>();
        ExecuteResultEntity executeResultEntity = new ExecuteResultEntity();
        //判断是否是新增时运行用例
        if(caseParametersDTO.getExtract()!=null) {
            //获取提取参数的value
            //逗号分开提取参数
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
//        if (caseParametersDTO.isAdd() == false) {
//            caseParametersDTO.setMethod(caseParametersDTO.getMethod());
//            caseParametersDTO.setBody(caseParametersDTO.getBody());
//            caseParametersDTO.setHeader(caseParametersDTO.isHeader());
//            caseParametersDTO.setPath(caseParametersDTO.getPath());
//            caseParametersDTO.setHeaderDetail(caseParametersDTO.getHeaderDetail());
//            caseParametersDTO.setSign(caseParametersDTO.isSign());
//            caseParametersDTO.setSignEntity(caseParametersDTO.getSignEntity());
//            caseParametersDTO.setAssertionContent(caseParametersDTO.getAssertionContent());
//        }
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
        System.out.println("用例名称："+caseParametersDTO.getCaseName()+"的"+"断言结果===" + assertResult);
        System.out.println("用例名称："+caseParametersDTO.getCaseName()+"的"+"用例执行结果是否通过===" + caseExecuteResult);
        System.out.println("用例名称："+caseParametersDTO.getCaseName()+"的"+"用例执行结果===" + response);
        System.out.println("用例名称："+caseParametersDTO.getCaseName()+"的"+"断言和执行结果实体类===" + executeResultEntity);
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
        return executeResultEntity;
    }


    /***
     * 校验请求url是IP地址还是域名
     * @param url
     * @return boolean:true表示IP地址，反之为域名
     */
    public static boolean getUrlPrefix(String url){
        // 正则表达式规则
        String regEx = "((http|ftp|https)://)(([0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}))(([a-zA-Z]{2,6})|(:[0-9]{1,4})?)";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        Matcher m = pattern.matcher(url);
        // 查找字符串中是否有匹配正则表达式的字符/字符串
        while (m.find()) {
            return true;
        }
        log.info("请求地址为域名，稍后将解析该域名");
        return false;
    }
}
