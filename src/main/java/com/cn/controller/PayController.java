package com.cn.controller;

import com.alipay.api.AlipayApiException;
import com.cn.pojo.req.OrderReq;
import com.cn.pojo.resp.BaseResp;
import com.cn.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author YaoJie
 * @Date 2021/10/23
 */

@RestController
@RequestMapping("/livePay")
public class PayController {

    @Autowired
    PayService payService;


    /**
     * 支付
     * @param orderReq
     * @return
     */
    @RequestMapping("/pay")
    public BaseResp pay(@RequestBody OrderReq orderReq){
        System.out.println(orderReq);
        return payService.pay(orderReq.getOrderid());
    }

    /**
     * 支付宝
     * @param request
     * @return
     */
    @RequestMapping("/url")
    public BaseResp url(HttpServletRequest request)  {
        try {
            return payService.url(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
