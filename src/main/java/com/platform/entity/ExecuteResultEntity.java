package com.platform.entity;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ExecuteResultEntity {

        @ApiModelProperty("结果实际返回结果")
        private JSONObject response;

        @ApiModelProperty("断言结果")
        private List<String> assertionExecuteResult;
}
