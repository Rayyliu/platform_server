package com.platform.client;


import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Map;

@FeignClient(name = "platform-data")
@Component
public interface ExecuteService {

    @PostMapping("execute/add")
    @ApiOperation("调用添加用例表执行记录接口")
    int insertAndRun(Map<String,Object> record);


}
