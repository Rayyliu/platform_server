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

    public static JSONObject getMethod(CaseParametersDTO caseParametersDTO) {
        Map<Object, Object> map = new HashMap<>();
        List<RequestBodyEntity> requestParameters = caseParametersDTO.getBody();
        List caseData = requestParameters.stream().map(item->item.getCaseData()).collect(Collectors.toList());
        JSONObject  dataJSON = (JSONObject) JSONObject.toJSON(caseData);
        if(caseParametersDTO.isHeader()==true){
           return JSONObject.parseObject(String.valueOf(restTemplate.exchange(caseParametersDTO.getPath(),HttpMethod.GET,entity(caseParametersDTO),String.class)));
        }else {
            if (requestParameters != null) {
                dataJSON.keySet().stream().map(key -> map.put(key, dataJSON.get(key))).collect(Collectors.toList());
                return restTemplate.getForObject(caseParametersDTO.getPath(), JSONObject.class, map);
            } else {
                return restTemplate.getForObject(caseParametersDTO.getPath(), JSONObject.class);
            }
        }
    }


    public static JSONObject postMethod(CaseParametersDTO caseParametersDTO) {

        HttpEntity<Map<Object, Object>> entity = entity(caseParametersDTO);
        return restTemplate.postForObject(caseParametersDTO.getPath(), entity, JSONObject.class);
    }

    /***
     * 组装请求map及header
     * @param caseParametersDTO
     * @return
     */
    public static HttpEntity<Map<Object, Object>> entity(CaseParametersDTO caseParametersDTO) {
        Map<Object, Object> map = new HashMap<>();
        HttpHeaders header = new HttpHeaders();
        List<RequestBodyEntity> requestParameters = caseParametersDTO.getBody();
        List caseData = requestParameters.stream().map(item->item.getCaseData()).collect(Collectors.toList());
        JSONObject  dataJSON = (JSONObject) JSONObject.toJSON(caseData);
        dataJSON.keySet().stream().map(key -> map.put(key, dataJSON.get(key))).collect(Collectors.toList());
        JSONObject requestHeader = caseParametersDTO.getHeaderDetail();
        if (caseParametersDTO.isHeader() == true) {
            if (requestHeader!=null) {
                for (String key : requestHeader.keySet()) {
                    header.add(key, (String) dataJSON.get(key));
                }
            }
            if (caseParametersDTO.isSign() == true) {
                header.add("", sign(caseParametersDTO));
            }
            return new HttpEntity<>(map, header);
        } else {
            return new HttpEntity<>(map);
        }
    }


    public static String sign(CaseParametersDTO caseParametersDTO) {
        String signResult=null;
        switch (caseParametersDTO.getProject()) {
            case "supplyChain":
                signResult=Sign.signSc(caseParametersDTO.getSignEntity().getMobile(), caseParametersDTO.getSignEntity().getPassword());
            break;
        }
        return signResult;
    }
}
