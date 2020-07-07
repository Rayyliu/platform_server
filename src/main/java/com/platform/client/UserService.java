package com.platform.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.platform.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "platform-data")
public interface UserService {

    @PostMapping("user/save")
    @HystrixCommand
    void save(@RequestBody UserEntity user);
}
