package com.cn.controller;

import com.cn.pojo.resp.BaseResp;
import com.cn.service.RationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author YaoJie
 * @Date 2021/10/20
 */

@RestController
@RequestMapping("/portal")
public class PortalController {

    @Autowired
    RationService rationService;


    @RequestMapping("/findRatation")
    public BaseResp findRatation(){

        return rationService.findAll();
    }
}
