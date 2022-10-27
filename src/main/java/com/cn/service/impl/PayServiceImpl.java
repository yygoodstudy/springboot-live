package com.cn.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.cn.dao.OrderMapper;

import com.cn.pojo.resp.BaseResp;
import com.cn.pojo.vo.Order;
import com.cn.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author YaoJie
 * @Date 2021/10/23
 */

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    OrderMapper orderMapper;

    protected static Logger logger= LoggerFactory.getLogger(PayServiceImpl.class);

    @Override
    public BaseResp pay(String orderid) {

        logger.info("订单号"+orderid+"开始请求支付");
        //通过订单id来支付
        Order order = orderMapper.findById(orderid);
        //通过订单id修改状态
        //正在支付
        order.setOrderStatus(3);
        orderMapper.updateOrderStatus(order);

        //System.out.println(order);
        //请求支付宝
        AlipayClient alipayClient =  new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", "2021000118625538", "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC2MFBODSjNYmE9WlaVy9huFQeszmS0/XbrBY3owKl3pqBdNiW3/z1gcv/Z9s0TWKwr4hXrq/jMtphkRNI0ABZo6PWxqMaN6FgHxt566mTRZKSYRyx7kb36XDUF1GF6dwXfzDQ4hkXGhOXe0Xi5bdyT7xfdXxgcN+y3cM7nTNo0/nebHhyW7oWISAP6edZM4TgY7/JuFsyrV6yWu1Hi8MFS6VM7Ui6yaQeTfcqjVO37LWVEua9Owzy4hHMtuznrxxpZuIp1XXkGrDjLcDcs0h1VwQt7m27ii2R1/Xvow7oBAx7sVav2zdyjFubbC7BqlgFvn+HBd/jduHHK0bhwzozDAgMBAAECggEAbTW23aKrw7nmWjWt02t3GD3I9QBfg9KwFx+kOlJLA6tBU76ii5IXw5oL3idANQmhY0jKJRRYNrZ50Rkj/AHVbRvAM7fdFbGiGz9Nm9anipFOzO1VL9lkTRA6xHzT1epE/itVXlC2wNJnTNK6EEVXxOG8p3laZYfsgOXBUQtTfhHJmtuEweuulXFoEwlhefu3+7F00g6HigMGFQR/3bmyklj3JkuC9e+Vbq3o1KOlKpyixHvjFZRWTPLoUzREPkOSNNf9/l2C2gFsekLbatBNC7znxtFPOPIxEBpZVJTAeX6yDfYnO9gEJfkF6O2ZYIJbGZ8xGVN1cY9+KiSl9N6cyQKBgQD8piD5uquK6xOb37E57OPT/ByiBXP3fP/4oyadO2txx76NhC5iWhALvKLs4+Fx2OnQ2fiD6RfAb+GQcK21KgDgo5tNVDJyaxe+kd+XxiwBtGzhYMcWNQFJhQ1Z9ZXp1spM+G8P4hyGOnjH6vQ/1NPJ3aOMMKyzUDwC4Eqo+Jy4DwKBgQC4mu/OU2L3D9EQ55mn/3xnbu4lR4EFdvIQk5Ux9wFPmHAKDv2hKE+hVIQ9QaC15pmxTcWBe70KqsDrGtw37kAh+cRcjmTgsq+GMpwS/74Okib2ef02A9qmHkbqksRenRY9dsHG+pYIlRdutNjO0bLx6mzr274zfSZQL8/hp7+MDQKBgCY2GharHPbNxxF0K6HPKkYNg2cd3AQ6a5x3UIfHTODQjQlqTHmjlXmw9hNOA/LsE7D6xpNs1aLNSRhHcn5L9AYFuJ9hdJ/JgrXX6jFST6WSXEVaFcJOEfvEm9hXRG0xhzdM+78zO40xmsLhzj85OS72zp2NXxFfuTPWKHSV5V4RAoGAGzL40jWHB0/VReBZy17hPXwiqmt2nw9/auHTwaDbGJclKtY6mFwQH30MXtoki6iGTJFg4EsS91dhRUKX38XCvT7Hp7tzMYgiRGuAg2wa3Jq8EuS2Yhb/SCp92GH3fNk4RaYRtveE047j/PO4XgCXlP0tPzMJOxzdYEMrJdxJZKECgYBiC+3IAdBpHcWKE7ccHvaFjMI07eXZbeqMXUykvRZDvZAKHBLkNfgCjlV5jyd3+P3l0UrXmBCUIUBzcGkdLEd5WKmYS2HVsF/K5l7DM5DLfDK1YWOkiFx6rUG4VFwPqGdtl1c4APgshjNsExVt8EezpcMDHu8udF6C8KCwoZ1UlA==", "json", "utf-8", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkfCkr2XWVVlyenKy0/KADrul98qHHd8/0aJ1bh8h7eopZwkBRsO16sroGSlGNk/974baRJCc91WusGIJYAav4s+UCxgep7i8nBZ4bAEKPqLIqOTkzW82ofVQghSo4SKSH+a/tN8j7pxxp+9x/vcaWfhJtWYsRLGwLBBkqD/DGUBDyzSHO9ouaryPl+RVPnO3K4LkWMFBJKK9f3M0/+hDrs8NxHCsmGGGAgPb0DvpeteO9tLbVBVzP48VnAafChfI5o6DbvB+7kw6yg6tsoWuifhi0HXKNIEUbrcHjrc5T+BYK8x+XzqBwdaGjbiGBHoALSOSIWedVWgNEuFL72r82wIDAQAB", "RSA2");  //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest =  new AlipayTradePagePayRequest(); //创建API对应的request
        //同步返回的调用结果 ---》当用户支付成功后，通过跳转到是商户支付成功页面
        //https://www.domain.com/CallBack/return_url
        //内网穿透：1.将自己本地的ip 发布到公网上
        alipayRequest.setReturnUrl( "http://440k1806x9.qicp.vip/#/paySuccess" );
        //异步返回的结果 ---》当用户支付成功后，支付宝通过post请求到了你的接口的地址，将支付的参数携带，并且用户进行验签
        alipayRequest.setNotifyUrl( "http://w440180m69.zicp.fun/livePay/url" ); //在公共参数中设置回跳和通知地址
        //使用FastJson进行转换
        Map map = new HashMap<>();
        // Map sysMap = new HashMap<>();
        map.put("out_trade_no",order.getOrderId());
        map.put("product_code","FAST_INSTANT_TRADE_PAY");
        map.put("total_amount",order.getOrderPay());
        map.put("subject","教育平台");
        map.put("body","IT教育");

        //把map转成json
        String s = JSONObject.toJSONString(map);
        alipayRequest.setBizContent(s);
        String form= "" ;
        try  {
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
        }  catch  (AlipayApiException e) {
            e.printStackTrace();
        }
        return new BaseResp(0,"请求支付宝成功",null,form);
    }


    @Override
    public BaseResp url(HttpServletRequest request)  {
        logger.info("进入回调方法");
        Map<String, String> stringStringMap = convertRequestParamsToMap(request);
        try {
            boolean  signVerified = AlipaySignature.rsaCheckV1(stringStringMap, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkfCkr2XWVVlyenKy0/KADrul98qHHd8/0aJ1bh8h7eopZwkBRsO16sroGSlGNk/974baRJCc91WusGIJYAav4s+UCxgep7i8nBZ4bAEKPqLIqOTkzW82ofVQghSo4SKSH+a/tN8j7pxxp+9x/vcaWfhJtWYsRLGwLBBkqD/DGUBDyzSHO9ouaryPl+RVPnO3K4LkWMFBJKK9f3M0/+hDrs8NxHCsmGGGAgPb0DvpeteO9tLbVBVzP48VnAafChfI5o6DbvB+7kw6yg6tsoWuifhi0HXKNIEUbrcHjrc5T+BYK8x+XzqBwdaGjbiGBHoALSOSIWedVWgNEuFL72r82wIDAQAB", "utf-8", "RSA2");  //调用SDK验证签名
            if(signVerified){
                logger.info("延签成功参数为"+stringStringMap);
                //延签成功
                if(stringStringMap.get("trade_status").equals("TRADE_SUCCESS")){

                    String out_trade_no = stringStringMap.get("out_trade_no");

                    //通过订单id修改状态
                    //支付成功
                    Order order = orderMapper.findById(out_trade_no);
                    order.setOrderStatus(2);
                    orderMapper.updateOrderStatus(order);
                }

            }else{
                logger.info("延签失败");
                //延签失败
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 将request中的参数转换成Map
    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>();

        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;

            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }

        return retMap;
    }
}
