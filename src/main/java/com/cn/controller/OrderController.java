package com.cn.controller;

import com.cn.pojo.req.OrderReq;
import com.cn.pojo.resp.BaseResp;
import com.cn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author YaoJie
 * @Date 2021/10/22
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;


    /**
     * 添加订单
     * @param orderReq
     * @return
     */
    @RequestMapping("/addOrder")
    public BaseResp addOrder(@RequestBody OrderReq orderReq, HttpServletRequest request){

        return orderService.addOrder(orderReq,request);
    }

    /**
     * 展示订单
     * @param request
     * @return
     */
    @RequestMapping("/findOrderByUserId")
    public BaseResp findOrderByUserId(HttpServletRequest request){

        return orderService.findOrderByUserId(request);
    }
}
