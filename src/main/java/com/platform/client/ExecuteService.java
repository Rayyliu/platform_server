package com.platform.client;


import com.platform.entity.dto.CaseParametersDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "platform-data")
@Component
public interface ExecuteService {

    @PostMapping("execute/add")
    @ApiOperation("调用添加用例表执行记录接口")
    int insertAndRun(Map<String,Object> record);

    @GetMapping("execute/deletes")
    @ApiOperation("删除单个测试用例执行记录")
    void deletes(@RequestParam("recordIds")String[] recordIds);

    @GetMapping("execute/queryPage")
    @ApiOperation("用例记录分页查询")
    List queryPage(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize")int pageSize, @RequestParam("caseName")String caseName);

    @GetMapping("execute/queryAll")
    @ApiOperation("查询用例总数")
    int queryAll();

    @PostMapping("execute/update")
    @ApiOperation("编辑用例")
    Object editExecuteRecord(Map<String,Object> record,@RequestParam("jsessionid") String jsessionid);

    @GetMapping("execute/queryByCaseName")
    @ApiOperation("通过用例名查询用例")
    CaseParametersDTO queryByCaseName(@RequestParam("caseName") String caseName);

    @GetMapping("execute/queryById")
    @ApiOperation("通过用例名查询用例")
    CaseParametersDTO queryById(@RequestParam("id")int id);
}
