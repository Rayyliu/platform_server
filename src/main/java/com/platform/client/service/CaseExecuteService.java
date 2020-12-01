package com.platform.client.service;

import com.platform.entity.ExecuteResultEntity;
import com.platform.entity.dto.PlanDTO;
import com.platform.entity.dto.RequestParameterDTO;

public interface CaseExecuteService  {

    /***
     * 单/多个测试用例执行
     * @param requestParameterDTO
     * @return
     */
    ExecuteResultEntity caseExecute(RequestParameterDTO requestParameterDTO);

    /***
     * 执行测试计划
     * @param plan
     * @return
     */
    ExecuteResultEntity planExecute(PlanDTO plan);
}
