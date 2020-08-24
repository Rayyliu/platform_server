package com.platform.util;

import com.alibaba.fastjson.JSONObject;
import com.platform.entity.dto.CaseParametersDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HttpRequestUntil {


    public List<JSONObject> httpRequest(CaseParametersDTO caseParametersDTO) {
        List<JSONObject> result = null;
        switch (caseParametersDTO.getMethod()) {
            case "get":
                result = RequestMethodUntil.getMethod(caseParametersDTO);
                    break;
            case "post":
                result= RequestMethodUntil.postMethod(caseParametersDTO);
                    break;
        }
            return result;
    }
}

