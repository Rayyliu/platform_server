package com.platform.client.frontend;

import com.platform.entity.dto.EnvDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "platform-data")
public interface EnvService {

    @PostMapping("env/add")
    int addEnv(EnvDTO env);

    @GetMapping("env/queryPage")
    List queryPage(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize")int pageSize,@RequestParam("envName") String envName);

    @GetMapping(value = "env/queryTotal")
    int queryTotal();

    @GetMapping("env/singleDelet")
    int singleDelet(@RequestParam("envId") String envId);

    @GetMapping("env/deletes")
    void deletes(@RequestParam("ids") String[] ids);
}
