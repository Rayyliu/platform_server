package com.platform.controller.pagecontroller;

import com.platform.client.frontend.InterFaceService;
import com.platform.entity.dto.InterFaceDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("interface/")
public class InterFaceController {

    @Autowired
    InterFaceService interFaceService;

    @PostMapping("add")
    @ApiOperation("添加新接口")
    public void addInterFace(@RequestBody InterFaceDTO interFaceDTO){
        interFaceService.addInterFace(interFaceDTO);
    }
}
