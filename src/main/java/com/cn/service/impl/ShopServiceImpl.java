package com.cn.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cn.dao.CourseMapper;
import com.cn.dao.ShopMapper;
import com.cn.pojo.resp.BaseResp;
import com.cn.pojo.vo.Course;
import com.cn.pojo.vo.User;
import com.cn.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author YaoJie
 * @Date 2021/10/21
 */

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopMapper shopMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    CourseMapper courseMapper;

    private static final String SHOP_CART="SHOP_CART_";

    @Override
    public BaseResp addShopCard(Integer id, HttpServletRequest request) {

        //首先从header中获取token
        String token = request.getHeader("token");
        //判断在redis中是否存在token信息
        Boolean aBoolean = redisTemplate.hasKey(token);
        if(!aBoolean){
            return new BaseResp(1,"用户登录已过期",null,null);
        }
        //从redis中获取个人信息
        Object o = redisTemplate.opsForValue().get(token);
        //把获取的信息转换成对象
        User user = JSONObject.parseObject(JSONObject.toJSONString(o), User.class);
        //去redis中查询该课程是否已存在在购物车中
        Boolean cart = redisTemplate.hasKey(SHOP_CART + user.getId());
        if(!cart){
            //去数据库查并存储在redis中
            Course course = courseMapper.findByCourseId(id);
            //存储到redis中
            redisTemplate.opsForHash().put(SHOP_CART + user.getId(),course.getId().toString(),course);
        }else{
            //如果该用户已经拥有了购物车，该课程是否存在购物车中
            Object o1 = redisTemplate.opsForHash().get(SHOP_CART + user.getId(), id.toString());
            if(o1!=null){
                return new BaseResp(1,"该课程已存在购物车中",null,null);
            }
            //去数据库查并存储在redis中
            Course course = courseMapper.findByCourseId(id);
            //存储到redis中
            redisTemplate.opsForHash().put(SHOP_CART + user.getId(),course.getId().toString(),course);
        }
        return new BaseResp(0,"添加购物车成功",null,null);
    }



    @Override
    public BaseResp findShopCardByToken(HttpServletRequest request) {
        //获取header中token
        String token = request.getHeader("token");
        //从redis中查看
        Boolean aBoolean = redisTemplate.hasKey(token);
        if(!aBoolean){
            return new BaseResp(1,"信息已失效",null,null);
        }
        //从redis中获取个人信息
        Object o = redisTemplate.opsForValue().get(token);
        //转换成对象
        User user = JSONObject.parseObject(JSONObject.toJSONString(o), User.class);
        //通过用户id获取购物车中课程
        Boolean aBoolean1 = redisTemplate.hasKey(SHOP_CART + user.getId());
        if(!aBoolean1){
            return new BaseResp(1,"购物车没有课程",null,null);
        }
        //获取购物车中课程
        Map map = redisTemplate.opsForHash().entries(SHOP_CART + user.getId());
        return new BaseResp(0,"查询成功",null,map);
    }



    @Override
    public BaseResp delShop(Integer courseid, HttpServletRequest request) {
        //从header中获取token
        String token = request.getHeader("token");
        //查看redis中是否存在token
        Boolean redisToken = redisTemplate.hasKey(token);
        if(!redisToken){
            return new BaseResp(1,"登录信息已过期",null,null);
        }
        //存在获取token
        Object o = redisTemplate.opsForValue().get(token);
        //把o转换成对象
        User user = JSONObject.parseObject(JSONObject.toJSONString(o), User.class);

        //通过在key在redis中获取课程
        Object o1 = redisTemplate.opsForHash().get(SHOP_CART + user.getId(), courseid.toString());
        if(o1==null){
            return new BaseResp(1,"该课程已被删除",null,null);
        }
        redisTemplate.opsForHash().delete(SHOP_CART + user.getId(), courseid.toString());
        //假设购物车中就只有最后的一个商品了,再次获取购物车中所有的商品
        Map entries = redisTemplate.opsForHash().entries(SHOP_CART + user.getId());

        if(entries==null){
            //将该用户的购物车删除
            redisTemplate.delete(SHOP_CART + user.getId());
        }
        return new BaseResp(0,"删除成功",null,null);
    }
}
