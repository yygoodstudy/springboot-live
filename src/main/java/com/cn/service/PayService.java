package com.cn.service;

import com.alipay.api.AlipayApiException;
import com.cn.pojo.req.OrderReq;
import com.cn.pojo.resp.BaseResp;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author YaoJie
 * @Date 2021/10/23
 */
public interface PayService {
    BaseResp pay(String orderid);

    BaseResp url(HttpServletRequest request) throws AlipayApiException;
}
