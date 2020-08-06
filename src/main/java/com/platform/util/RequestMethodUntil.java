package com.platform.util;

import com.alibaba.fastjson.JSONObject;
import com.platform.entity.dto.CaseParametersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestMethodUntil {

    static RestTemplate restTemplate = new RestTemplate();

    public static JSONObject getMethod(CaseParametersDTO caseParametersDTO) {
        Map<Object, Object> map = new HashMap<>();
        JSONObject requestParameters = caseParametersDTO.getBody();
        if (requestParameters!=null) {
//            for (String key : requestParameters.keySet()) {
//                map.put(key, requestParameters.get(key));
//            }
            requestParameters.keySet().stream().map(key->map.put(key,requestParameters.get(key)));
            return restTemplate.getForObject(caseParametersDTO.getUrl(), JSONObject.class, map);
        }else {
            return restTemplate.getForObject(caseParametersDTO.getUrl(), JSONObject.class);
        }
    }

    public static JSONObject postMothod(CaseParametersDTO caseParametersDTO){

        HttpEntity<Map<Object, Object>> entity = null;

        Map<Object, Object> map = new HashMap<>();
        JSONObject requestParameters = caseParametersDTO.getBody();
        requestParameters.keySet().stream().map(key->map.put(key,requestParameters.get(key))).collect(Collectors.toList());

        JSONObject requestHeader = caseParametersDTO.getHeaderDetail();
        if(requestHeader.size()!=0) {
            HttpHeaders header = new HttpHeaders();
            for (String key : requestHeader.keySet()) {
                header.add(key, (String) requestParameters.get(key));
                entity = new HttpEntity<>(map, header);
            }
        }else {
            entity = new HttpEntity<>(map);
        }
        return restTemplate.postForObject(caseParametersDTO.getUrl(),entity, JSONObject.class);
    }
}
