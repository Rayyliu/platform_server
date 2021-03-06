package com.platform.util;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;
import java.util.List;

public class StringDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<List<String>> {

@Override
public List<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        //String 转换成 List<String >：List<T> list=JSONArray.parseArray("",T.class);
        return JSONArray.parseArray(jsonParser.getText(),String.class);
        }
}
