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
        List<JSONObject> dataJSON = resolveBody(caseParametersDTO);
        if (caseParametersDTO.isHeader() == true) {
            List<HttpEntity<?>> httpEntities = requestEntity(caseParametersDTO);
            return getInvoke(caseParametersDTO,httpEntities);
//            return JSONObject.parseObject(String.valueOf(restTemplate.exchange(caseParametersDTO.getPath(), HttpMethod.GET, entity(caseParametersDTO), String.class)));
        } else {
            if (dataJSON.size()!=0) {
               List mapData = dataJSON.stream().map(item -> map.put(item.keySet(), item.get(item.keySet()))).collect(Collectors.toList());
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
        Map<Object, Object> map = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        List<JSONObject> dataJSON = resolveBody(caseParametersDTO);
        List dataMap = dataJSON.stream().map(item -> map.put(item.keySet(), item.get(item.keySet()))).collect(Collectors.toList());
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

            return (List<JSONObject>)dataMap.stream().map(data -> doInvokeInterface(caseParametersDTO, (List<Map<Object, Object>>) data, headers)).collect(Collectors.toList());
//            return new HttpEntity<>(map, header);
        } else {
            return (List<JSONObject>)dataMap.stream().map(data -> doInvokeInterface(caseParametersDTO, (List<Map<Object, Object>>)data, null)).collect(Collectors.toList());
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

    public static List<JSONObject> doInvokeInterface(CaseParametersDTO caseParametersDTO, List<Map<Object, Object>> map, HttpHeaders headers) {
        List<JSONObject> result = null;
        switch (caseParametersDTO.getMethod()) {
            case "POST":
                return postInvoke(caseParametersDTO,map,headers);
            case "get":
                return  getInvoke(caseParametersDTO,requestEntity(caseParametersDTO));
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
    public static List<JSONObject> postInvoke(CaseParametersDTO caseParametersDTO, List<Map<Object, Object>> map, HttpHeaders headers){

        List<HttpEntity<Map<Object, Object>>> entity;
        if (headers != null) {
            entity = map.stream().map(item->new HttpEntity<>(item, headers)).collect(Collectors.toList());
        } else {
            entity = map.stream().map(item->new HttpEntity<>(item)).collect(Collectors.toList());
        }
        return entity.stream().map(item->restTemplate.postForObject(caseParametersDTO.getPath(), item, JSONObject.class)).collect(Collectors.toList());
    }

    /***
     * get请求：重新封装get请求
     * @param caseParametersDTO
     * @param requestEntity
     * @return
     */
    public static List<JSONObject> getInvoke(CaseParametersDTO caseParametersDTO,List<HttpEntity<?>> requestEntity){
        return requestEntity.stream().map(item->JSONObject.parseObject(String.valueOf(restTemplate.exchange(caseParametersDTO.getPath(), HttpMethod.GET, item, String.class)))).collect(Collectors.toList());
    }


    /***
     * 解析请求参数
     * @param caseParametersDTO
     * @return
     */
    public static List<HttpEntity<?>> requestEntity(CaseParametersDTO caseParametersDTO){
        HttpEntity<Map<Object, Object>> entity;
        Map<Object, Object> map = new HashMap<>();
        List<JSONObject> jsonBody = resolveBody(caseParametersDTO);
        HttpHeaders headers = resolveHeaders(caseParametersDTO);
        if(headers.size()!=0){
            return jsonBody.stream().map(item->new HttpEntity<>(jsonBody, headers)).collect(Collectors.toList());
        }else{
            return jsonBody.stream().map(item->new HttpEntity<>(jsonBody)).collect(Collectors.toList());
        }
    }


    /***
     * 解析请求body：body->JSONObject
     * @param caseParametersDTO
     * @return
     */
    public static List<JSONObject> resolveBody(CaseParametersDTO caseParametersDTO){

        List<RequestBodyEntity> requestParameters = caseParametersDTO.getBody();
        List caseData = requestParameters.stream().map(item -> item.getCaseData()).collect(Collectors.toList());
        List<JSONObject> caseJson = (List<JSONObject>) caseData.stream().map(item->JSONObject.toJSON(item)).collect(Collectors.toList());
        return caseJson;
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
