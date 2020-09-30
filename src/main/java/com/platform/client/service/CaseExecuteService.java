package com.platform.client.service;

import com.platform.entity.ExecuteResultEntity;
import com.platform.entity.dto.RequestParameterDTO;

public interface CaseExecuteService  {

    ExecuteResultEntity caseExecute(RequestParameterDTO requestParameterDTO);
}
