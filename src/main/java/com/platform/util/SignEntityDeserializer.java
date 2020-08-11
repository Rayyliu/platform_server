package com.platform.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.entity.SignEntity;

import java.io.IOException;

public class SignEntityDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<SignEntity> {

    private static final ObjectMapper mapper = new ObjectMapper();
    @Override
    public SignEntity deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return mapper.readValue(jsonParser.getText(),SignEntity.class);
    }
}
