package com.cn.pojo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author YaoJie
 * @Date 2021/10/19
 */

@Data
@AllArgsConstructor
@NoArgsConstructor


public class BaseResp {

    private Integer code;


    private String msg;
    private Long total;



    private Object data;
}
