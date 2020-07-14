package com.platform.client;

import com.platform.entity.dto.ProjectDTO;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "platform-data")
public interface ProjectService {

    @PostMapping("project/add")
    boolean addProject(@RequestBody Object projectDTO);

    @GetMapping(value = "project/query")
    Object query(@RequestParam("page") int page, @RequestParam("pageSize")int pageSize);

}
