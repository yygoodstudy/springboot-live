package com.cn.service;

import com.cn.pojo.req.OrderReq;
import com.cn.pojo.resp.BaseResp;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author YaoJie
 * @Date 2021/10/22
 */
public interface OrderService {

    BaseResp addOrder(OrderReq orderReq, HttpServletRequest request);

    BaseResp findOrderByUserId(HttpServletRequest request);
}
