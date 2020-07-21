package com.platform.client.frontend;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "platform-data")
public interface InterFaceService {

    @PostMapping("interface/add")
    int addInterFace(Object interFaceDTO);
}
