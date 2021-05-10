package com.platform.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.platform.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Component
@FeignClient(name = "platform-data")
public interface UserService {

    @PostMapping("user/save")
//    @HystrixCommand
    Object save(@RequestBody UserEntity user);

    @GetMapping(value = "user/username/{userName}")
      Object findUser(@PathVariable("userName")String userName);
//    @GetMapping(value = "user/username/")
//    Object findUser(String userName);

    @PostMapping("user/login")
    Map<String,String> login(@RequestBody Map<String,String> loginMap);

}
