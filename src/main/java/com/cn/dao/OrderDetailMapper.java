package com.cn.dao;

import com.cn.pojo.vo.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author YaoJie
 * @Date 2021/10/22
 */

@Mapper
public interface OrderDetailMapper {

    Integer insertOrderDetail(List list);
}
