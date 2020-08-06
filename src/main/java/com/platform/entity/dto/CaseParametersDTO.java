package com.platform.entity.dto;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CaseParametersDTO {

    @ApiModelProperty(value = "用例名称",name = "caseName",required=true)
    private String caseName;

    @ApiModelProperty(value = "所属项目",name = "project",required=true)
    private String project;

    @ApiModelProperty(value = "用例描述",name = "caseDescription",required=true)
    private String caseDescription;

    @ApiModelProperty(value = "用例调用接口名称",name = "interFaceName",required=true)
    private String interFaceName;

    @ApiModelProperty(value = "运行结果",name = "executeResult",required=true)
    private String executeResult;

    @ApiModelProperty(value = "请求url",name = "url",required=true)
    private String url;

    @ApiModelProperty(value = "请求体",name = "body",required=true)
    private JSONObject body;

    @ApiModelProperty(value = "请求头",name = "headerDetail",required=true)
    private JSONObject headerDetail;

    @ApiModelProperty(value = "请求方式",name = "mode",required=true)
    private String method;
}