package com.platform.client;

import com.platform.entity.dto.ProjectDTO;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@Component
@FeignClient(name = "platform-data")
public interface ProjectService {

    @PostMapping("project/add")
    boolean addProject(@RequestBody Object projectDTO);

    @GetMapping(value = "project/queryTotal")
    int queryTotal();

    @GetMapping(value = "project/query")
    List query(@RequestParam("page") int page, @RequestParam("pageSize")int pageSize);

    @PostMapping("project/updateProject")
    int updateProject(ProjectDTO projectDTO);

    @GetMapping(value = "project/queryById")
    ProjectDTO queryById(@RequestParam("projectId")int projectId);

    @GetMapping("project/delById")
    void singleDelete(@RequestParam("projectId") String projectId);

    @GetMapping("project/updateValid")
    void updateValid(@RequestParam("projectId") String projectId,@RequestParam("valid") boolean valid);

    @GetMapping("project/deletes")
    void deletes(@RequestParam("arry") String[] arry);

}
