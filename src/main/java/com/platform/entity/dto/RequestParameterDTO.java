package com.platform.entity.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.platform.util.CaseParametersDTODeserializer;
import lombok.Data;

import java.util.List;

@Data
public class RequestParameterDTO extends PlatformBaseDTO{

//    @JsonDeserialize(using = CaseParametersDTODeserializer.class)
    private List<CaseParametersDTO> valuesArr;


}
