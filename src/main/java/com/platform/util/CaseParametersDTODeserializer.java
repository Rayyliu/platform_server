package com.platform.util;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.platform.entity.dto.CaseParametersDTO;

import java.io.IOException;
import java.util.List;

public class CaseParametersDTODeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<List<CaseParametersDTO>> {

    @Override
    public List<CaseParametersDTO> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        //String 转换成 List<String >：List<T> list=JSONArray.parseArray("",T.class);
        List<CaseParametersDTO> caseParametersDTOList = JSONArray.parseArray(jsonParser.getText(),CaseParametersDTO.class);
        return caseParametersDTOList;
    }
}
