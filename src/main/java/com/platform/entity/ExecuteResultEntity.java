package com.platform.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

@Data
public class ExecuteResultEntity {

        private JSONObject interFaceResult;
        private List<String> executeResult;
}
