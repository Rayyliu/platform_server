package com.platform.controller.pagecontroller;


import com.platform.client.ExecuteService;
import com.platform.entity.ResponseResult;
import com.platform.entity.dto.CaseParametersDTO;
import com.platform.response.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("execute/")
@RestController
public class CaseController {

    @Autowired
    ExecuteService executeService;

    @PostMapping("update")
    public ResponseResult updateExecuteRecord(@RequestBody Map<String,Object> record){

        return new ResponseResult().success(ResultCode.SUCCESS.getCode(), true, "用例执行成功", executeService.editExecuteRecord(record, (String) record.get("jsessionid")));
    }

    @GetMapping("deletes")
    public ResponseResult delSingleExecuteRecord(String[] recordIds ){
        executeService.deletes(recordIds);
        return new ResponseResult().success(ResultCode.SUCCESS.getCode(), true, "用例执行成功", null);
    }
}
