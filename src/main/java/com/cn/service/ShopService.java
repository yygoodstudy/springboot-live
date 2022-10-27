package com.cn.service;

import com.cn.pojo.resp.BaseResp;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author YaoJie
 * @Date 2021/10/21
 */
public interface ShopService {
    BaseResp addShopCard(Integer id, HttpServletRequest request);

    BaseResp findShopCardByToken(HttpServletRequest request);

    BaseResp delShop(Integer courseid,HttpServletRequest request);
}
