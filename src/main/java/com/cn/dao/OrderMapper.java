package com.cn.dao;

import com.cn.pojo.vo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author YaoJie
 * @Date 2021/10/22
 */
@Mapper
public interface OrderMapper {

    Integer insertOrder(Order order);


    List<Order> findOrderByUserId(@Param("id") Integer id);

    Order findById(@Param("orderid") String orderid);

    void updateOrderStatus(Order order);
}
