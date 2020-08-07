package com.platform.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/***
 * 解决入参中有json对象，无法反序列化的问题，如：headerDetail":"{\"gfdsgfd\": \"fdsa\"}","body":"{\"gfdsgfd\": \"fdsa\"}"
 * Jackson解析含有转义字符的JSON到Java对象
 */
public class JsonDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<JSONObject> {

    private static final ObjectMapper mapper = new ObjectMapper();
    @Override
    public JSONObject deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return mapper.readValue(jsonParser.getText(),JSONObject.class);
    }
}
