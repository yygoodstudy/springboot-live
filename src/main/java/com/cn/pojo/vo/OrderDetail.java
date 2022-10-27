package com.cn.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author YaoJie
 * @Date 2021/10/22
 */


@Data
public class OrderDetail {

    private String orderDetailId;

    private String orderId;

    private String courseId;

    private Date createTime;

    private Course course;
}
