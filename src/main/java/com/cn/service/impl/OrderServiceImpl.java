package com.cn.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cn.dao.OrderDetailMapper;
import com.cn.dao.OrderMapper;
import com.cn.pojo.req.OrderReq;
import com.cn.pojo.resp.BaseResp;
import com.cn.pojo.vo.Course;
import com.cn.pojo.vo.Order;
import com.cn.pojo.vo.OrderDetail;
import com.cn.pojo.vo.User;
import com.cn.service.OrderService;
import com.cn.utils.GenerateOrderNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author YaoJie
 * @Date 2021/10/22
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    OrderDetailMapper orderDetailMapper;


    private static final String SHOP_CART="SHOP_CART_";

    @Transactional
    @Override
    public BaseResp addOrder(OrderReq orderReq, HttpServletRequest request) {

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

        Double money=0.0;
        //获得该课程的id
        List<Integer> ids = orderReq.getIds();
        //从redis中获取商品的信息
        for(Integer id:ids){
            Object o1 = redisTemplate.opsForHash().get(SHOP_CART + user.getId(), id.toString());
            //把该单个商品转化成对象
            Course course = JSONObject.parseObject(JSONObject.toJSONString(o1), Course.class);
            money+=course.getPay();
        }
        //判断该商品的钱数与前端页面上的钱数是否一致
        if(!String.valueOf(money).equals(String.valueOf(orderReq.getMoney()))){
            return new BaseResp(1,"订单异常",null,null);
        }

        //生成订单
        //首先生成订单号
        String generate = GenerateOrderNum.generate("");
        Order order = new Order();
        order.setOrderId(generate);
        order.setOrderPay(money);
        order.setCreateTime(new Date());
        order.setOrderStatus(1);
        order.setUserId(user.getId());
        //添加订单的生成
        Integer integer = orderMapper.insertOrder(order);

        List list=new ArrayList();
        for(Integer id:ids){
            OrderDetail orderDetail = new OrderDetail();
            String generateid = GenerateOrderNum.generate("");
            orderDetail.setOrderId(generate);
            orderDetail.setOrderDetailId(generateid);
            orderDetail.setCreateTime(new Date());
            orderDetail.setCourseId(id.toString());
            list.add(orderDetail);
        }
        //批量添加订单
        orderDetailMapper.insertOrderDetail(list);
        //移除购物车中的商品
        for(Integer id:ids){
            redisTemplate.opsForHash().delete(SHOP_CART+user.getId(),id.toString());

        }
        return new BaseResp(0,"订单生成成功！",null,null);
    }


    @Override
    public BaseResp findOrderByUserId(HttpServletRequest request) {
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
        List<Order> orderByUserId = orderMapper.findOrderByUserId(user.getId());
        return new BaseResp(0,"查询成功",null,orderByUserId);
    }
}
