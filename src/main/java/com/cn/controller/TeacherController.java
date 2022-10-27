package com.cn.controller;

import com.cn.pojo.resp.BaseResp;
import com.cn.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author YaoJie
 * @Date 2021/10/20
 */


@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @RequestMapping("/findAll")
    public BaseResp findAll(){

        return teacherService.findAll();
    }
}
