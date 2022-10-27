package com.cn.controller;

import com.cn.pojo.resp.BaseResp;
import com.cn.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author YaoJie
 * @Date 2021/10/21
 */

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    ShopService shopService;


    /**
     * 添加购物车
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/addShopCard")
    public BaseResp addShopCard(@RequestBody Map map, HttpServletRequest request){

        return shopService.addShopCard(Integer.valueOf(map.get("courseid").toString()),request);
    }


    /**
     * 展示购物车信息
     * @return
     */
    @RequestMapping("/findShopCardByToken")
    public BaseResp findShopCardByToken(HttpServletRequest request){

        return shopService.findShopCardByToken(request);
    }


    /**
     * 删除购物车
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/delShop")
    public BaseResp delShop(@RequestBody Map map,HttpServletRequest request){

        return shopService.delShop(Integer.valueOf(map.get("courseid").toString()),request);
    }
}
