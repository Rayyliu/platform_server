package com.platform.client;

import com.platform.entity.dto.ProjectDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "platform-data")
public interface ProjectService {

    @PostMapping("project/add")
    boolean addProject(@RequestBody ProjectDTO projectDTO);

}
