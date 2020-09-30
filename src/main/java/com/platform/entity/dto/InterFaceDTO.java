package com.platform.entity.dto;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.platform.entity.SignEntity;
import com.platform.util.SignEntityDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InterFaceDTO {

    @ApiModelProperty(value = "接口id",name = "id",required=true)
    private int id;

    @ApiModelProperty(value = "接口名称",name = "interfaceName",required=true)
    private String interfaceName;

    @ApiModelProperty(value = "所属项目",name = "project",required=true)
    private String project;

    @ApiModelProperty(value = "接口请求路径",name = "path",required=true)
    private String path;

    @ApiModelProperty(value = "接口请求方式",name = "method",required=true)
    private String method;

    @ApiModelProperty(value = "数据传输方式",name = "mode",required=true)
    private String mode;

    @ApiModelProperty(value = "是否签名",name = "sign",required=true)
    private boolean sign;

    @ApiModelProperty(value = "签名11属性",name = "signAttribute",required=true)
    private String signAttribute;


    @ApiModelProperty(value = "是否设置header",name = "header",required=true)
    private boolean header;

    @ApiModelProperty(value = "是否mock",name = "mock",required=true)
    private boolean mock;

    @ApiModelProperty(value = "接口描述",name = "description",required=true)
    private String description;

    @ApiModelProperty(value = "header详情",name = "headerdetail")
    private String  headerdetail;

    @ApiModelProperty(value = "接口参数",name = "body",required=true)
    private String body;

    @ApiModelProperty(value = "断言内容",name = "assertDataSource",required=true)
    private String assertDataSource;

    @ApiModelProperty(value = "提取参数",name = "extract",required=true)
    private String extract;

    @ApiModelProperty(value = "生成sign签名所属字段",name = "signParameter")
    @JsonDeserialize(using = SignEntityDeserializer.class)
    private SignEntity signEntity;


}
