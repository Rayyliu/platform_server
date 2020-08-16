package com.platform.client;


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

    @GetMapping("execute/queryPage")
    @ApiOperation("用例记录分页查询")
    List queryPage(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize")int pageSize, @RequestParam("caseName")String caseName);

    @GetMapping("execute/queryAll")
    @ApiOperation("查询用例总数")
    int queryAll();
}
