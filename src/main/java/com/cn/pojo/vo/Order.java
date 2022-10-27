package com.cn.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author YaoJie
 * @Date 2021/10/22
 */

@Data
public class Order {

    private String orderId;

    private Double orderPay;

    private Integer userId;

    private Integer orderStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private List<OrderDetail> orderDetail;

}
