package com.cn.pojo.req;

import lombok.Data;

import java.util.List;

/**
 * @Author YaoJie
 * @Date 2021/10/22
 */

@Data
public class OrderReq {

    private List<Integer> ids;

    private Double money;

    private String orderid;
}
