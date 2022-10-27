package com.cn.pojo.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author YaoJie
 * @Date 2021/10/19
 */

@Data
public class UserReq {


    private Integer id;


    private String loginName;

    private String password;


    private String realName;

    private String phone;

    private String address;

    private Integer sex;

    private String qq;

    private Integer status;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    private String email;

    private String pic;

    private String code;
}
