package com.platform.util;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.platform.entity.AssertionEntity;
import com.platform.entity.RequestBodyEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RequestBodyEntityDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<List<RequestBodyEntity>> {

    @Override
    public List<RequestBodyEntity> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        //String 转换成 List<String >：List<T> list=JSONArray.parseArray("",T.class);
        return JSONArray.parseArray(jsonParser.getText(),RequestBodyEntity.class);
    }

}
