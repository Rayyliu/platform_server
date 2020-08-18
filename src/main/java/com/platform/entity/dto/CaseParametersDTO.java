package com.platform.entity.dto;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.platform.entity.AssertionEntity;
import com.platform.entity.SignEntity;
import com.platform.util.AssertionEntityDeserializer;
import com.platform.util.JsonDeserializer;
import com.platform.util.SignEntityDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CaseParametersDTO {


    @ApiModelProperty(value = "用例id",name = "id",required=true)
    private int id;

    @ApiModelProperty(value = "用例名称",name = "caseName",required=true)
    private String caseName;

    @ApiModelProperty(value = "所属项目",name = "project",required=true)
    private String project;

    @ApiModelProperty(value = "用例描述",name = "caseDescription",required=true)
    private String description;

    @ApiModelProperty(value = "用例调用接口名称",name = "interFaceName",required=true)
    private String interFaceName;

    @ApiModelProperty(value = "运行结果",name = "executeResult",required=true)
    private String executeResult;

    @ApiModelProperty(value = "请求url",name = "path",required=true)
    private String path;

    @ApiModelProperty(value = "请求体",name = "body",required=true)
    @JsonDeserialize(using = JsonDeserializer.class)
    private JSONObject body;

    @ApiModelProperty(value = "请求头",name = "headerDetail",required=true)
    @JsonDeserialize(using = JsonDeserializer.class)
    private JSONObject headerDetail;

    @ApiModelProperty(value = "请求方式",name = "method",required=true)
    private String method;

    @ApiModelProperty(value = "接口是否签名",name = "sign",required = true)
    private boolean sign;

    @ApiModelProperty(value = "是否设置header",name = "header",required = true)
    private boolean header;

    @ApiModelProperty(value = "sign签名",name = "signValue",required = true)
    private String signValue;

    @ApiModelProperty(value = "生成sign签名所属字段",name = "signParameter",required = true)
    @JsonDeserialize(using = SignEntityDeserializer.class)
    private SignEntity signEntity;

    @ApiModelProperty(value = "断言内容",name = "assertionContent",required = true)
//    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonDeserialize(using = AssertionEntityDeserializer.class)
    private List<AssertionEntity> assertionContent;

    @ApiModelProperty(value = "最近执行人",name = "lastExecuteUser",required = true)
    private String lastExecuteUser;

    @ApiModelProperty(value = "是否新增用例",name = "add",required = true)
    private boolean add;

    @ApiModelProperty(value = "用例是否有效",name = "valid",required = true)
    private boolean valid;

    @ApiModelProperty(value = "用例对应接口名称id",name = "interfaceId")
    private int interfaceId;

//    @ApiModelProperty(value = "用例最后执行时间",name = "lastExecuteTime",required = true)
//    private Data lastExecuteTime;
}
