package com.platform.tools;

import com.platform.client.ExecuteService;
import com.platform.client.frontend.InterFaceService;
import com.platform.entity.dto.CaseParametersDTO;
import com.platform.entity.dto.InterFaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InterFaceDetail {

    @Autowired
    InterFaceService interFaceService;

//    @Autowired
//    ExecuteService executeService;

    public InterFaceDTO queryInterFaceInfo(CaseParametersDTO caseParametersDTO){
//        int interfaceId = executeService.queryByCaseName(caseParametersDTO.getCaseName()).getInterfaceId();
        InterFaceDTO interfaceDetail =  interFaceService.queryByInterfaceId(caseParametersDTO.getInterfaceId());
        System.out.println("interfaceDetail==="+interfaceDetail);
        return interfaceDetail;
    }
}
