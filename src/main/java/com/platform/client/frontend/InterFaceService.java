package com.platform.client.frontend;

import com.platform.entity.dto.InterFaceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@FeignClient(name = "platform-data")
public interface InterFaceService {

    @PostMapping("interface/add")
    int addInterFace(Map<String,Object> interfaceData);

    @GetMapping("interface/queryAll")
    int queryAll();

    @GetMapping("interface/queryPage")
    List queryPage(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize")int pageSize, @RequestParam("interfaceName") String interfaceName);

    @PostMapping("interface/edit")
    int edit(Map<String,Object> interfaceData);

    @GetMapping("interface/queryInterFace")
    String[] queryInterFace();

    @GetMapping("interface/queryByName")
    InterFaceDTO queryByName(@RequestParam("interfaceName") String interfaceName);

    @GetMapping("interface/queryByInterfaceId")
    InterFaceDTO queryByInterfaceId(@RequestParam("interfaceId") int interfaceId);
}
