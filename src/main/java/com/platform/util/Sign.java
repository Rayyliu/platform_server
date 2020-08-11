package com.platform.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Sign {
    static RestTemplate restTemplate = new RestTemplate();

    public static String signSc(String mobile,String password){
        String pwd = Encryption.Md5Util(password);
        System.out.println("pwd==="+pwd);
        String url = "http://sc-pre-loan.simplecredit.lotest/sc/borrower/auth/!/login/bypwd";
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        dataMap.put("mobile",mobile);
        dataMap.put("password", pwd);
        JSONObject response = restTemplate.postForObject(url,dataMap, JSONObject.class);
        System.out.println("accessToken==="+(String) response.get("accessToken"));
        return (String) response.get("accessToken");
    }


}
