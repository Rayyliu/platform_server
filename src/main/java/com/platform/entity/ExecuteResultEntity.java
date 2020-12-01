package com.platform.entity;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ExecuteResultEntity {

        @ApiModelProperty("结果实际返回结果")
        private List<JSONObject> response;

        @ApiModelProperty("断言结果")
        private List<String> assertionExecuteResult;

}
