package com.platform.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.platform.entity.RequestBodyEntity;
import com.platform.entity.dto.CaseParametersDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestMethodUntil {

    static RestTemplate restTemplate = new RestTemplate();

    public static List<JSONObject> getMethod(CaseParametersDTO caseParametersDTO) {
        Map<Object, Object> map = new HashMap<>();
        List<String> dataJSON = resolveBody(caseParametersDTO);
        if (caseParametersDTO.isHeader() == true) {
            return entity(caseParametersDTO);
//            return JSONObject.parseObject(String.valueOf(restTemplate.exchange(caseParametersDTO.getPath(), HttpMethod.GET, entity(caseParametersDTO), String.class)));
        } else {
            if (dataJSON.size()!=0) {
               List mapData = dataJSON.stream().map(item -> JSONObject.parse(item)).collect(Collectors.toList());
                return (List<JSONObject>) mapData.stream().map(item->restTemplate.getForObject(caseParametersDTO.getPath(), JSONObject.class, item));
//                return restTemplate.getForObject(caseParametersDTO.getPath(), JSONObject.class, map);
            } else {
                return (List<JSONObject>)restTemplate.getForObject(caseParametersDTO.getPath(), JSONObject.class);
            }
        }
    }


    public static List<JSONObject> postMethod(CaseParametersDTO caseParametersDTO) {

        return entity(caseParametersDTO);
    }

    /***
     * 组装请求map及header
     * @param caseParametersDTO
     * @return
     */
    public static List<JSONObject> entity(CaseParametersDTO caseParametersDTO) {
        List<String> dataJSON = resolveBody(caseParametersDTO);
        List dataMap = dataJSON.stream().map(item ->JSONObject.parse(item)).collect(Collectors.toList());
        HttpHeaders headers = resolveHeaders(caseParametersDTO);
        if(headers!=null){
            return (List<JSONObject>)dataMap.stream().map(data -> doInvokeInterface(caseParametersDTO, (Map<Object, Object>) data, headers)).collect(Collectors.toList());
//            return new HttpEntity<>(map, header);
        } else {
            return (List<JSONObject>)dataMap.stream().map(data -> doInvokeInterface(caseParametersDTO, (Map<Object, Object>) data, null)).collect(Collectors.toList());
        }
    }


    /***
     * 接口签名
     * @param caseParametersDTO
     * @return
     */
    public static String sign(CaseParametersDTO caseParametersDTO) {
        String signResult = null;
        switch (caseParametersDTO.getProject()) {
            case "supplyChain":
                signResult = Sign.signSc(caseParametersDTO.getSignEntity().getMobile(), caseParametersDTO.getSignEntity().getPassword());
                break;
        }
        return signResult;
    }

    public static JSONObject doInvokeInterface(CaseParametersDTO caseParametersDTO, Map<Object, Object> map, HttpHeaders headers) {
        JSONObject result = null;
        switch (caseParametersDTO.getMethod()) {
            case "post":
                return postInvoke(caseParametersDTO,map,headers);
            case "get":
                return  getInvoke(caseParametersDTO,requestEntity(map,headers));
        }
        return result;
    }

    /***
     * post请求：重新封装post
     * @param caseParametersDTO
     * @param map
     * @param headers
     * @return
     */
    public static JSONObject postInvoke(CaseParametersDTO caseParametersDTO, Map<Object, Object> map, HttpHeaders headers){

        HttpEntity<Map<Object, Object>> entity;
        if (headers != null) {
            entity = new HttpEntity<>(map, headers);
        } else {
            entity = new HttpEntity<>(map);
        }
        return restTemplate.postForObject(caseParametersDTO.getPath(), entity, JSONObject.class);
    }

    /***
     * get请求：重新封装get请求
     * @param caseParametersDTO
     * @param requestEntity
     * @return
     */
    public static JSONObject getInvoke(CaseParametersDTO caseParametersDTO,HttpEntity<?> requestEntity){
        return JSONObject.parseObject(String.valueOf(restTemplate.exchange(caseParametersDTO.getPath(), HttpMethod.GET, (HttpEntity<?>) requestEntity, String.class)));
    }


    /***
     * 解析请求参数
     * @param map<Object, Object> map, HttpHeaders headers
     * @return
     */
    public static HttpEntity<?> requestEntity(Map<Object, Object> map, HttpHeaders headers){
        HttpEntity<Map<Object, Object>> entity;
        if(headers.size()!=0){
             entity = new HttpEntity<>(map, headers);
            return entity;
        }else{
             entity = new HttpEntity<>(map);
            return entity;
        }
    }


    /***
     * 解析请求body：body->JSONObject
     * @param caseParametersDTO
     * @return
     */
    public static List<String> resolveBody(CaseParametersDTO caseParametersDTO){

        List<RequestBodyEntity> requestParameters = caseParametersDTO.getBody();
        List caseData = requestParameters.stream().map(item -> item.getCaseData()).collect(Collectors.toList());
//        List<JSONObject> caseJson = (List<JSONObject>) caseData.stream().map(item->JSONObject.parseObject((String) item)).collect(Collectors.toList());
        return caseData;
    }


    /***
     * 解析请求headers
     * @param caseParametersDTO
     * @return
     */
    public static HttpHeaders resolveHeaders(CaseParametersDTO caseParametersDTO){
        HttpHeaders headers = new HttpHeaders();
        JSONObject requestHeader = caseParametersDTO.getHeaderDetail();
        if (caseParametersDTO.isHeader() == true) {
            if (requestHeader != null) {
                for (String key : requestHeader.keySet()) {
                    headers.add(key, (String) requestHeader.get(key));
                }
            }
            if (caseParametersDTO.isSign() == true) {
                headers.add("", sign(caseParametersDTO));
            }
            return headers;
        }else {
            return null;
        }

}
}
