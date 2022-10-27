package com.cn.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author YaoJie
 * @Date 2021/10/19
 */

@Data
@Entity
@Table(name = "live_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "login_name")
    private String loginName;

    private String password;

    @Column(name = "real_name")
    private String realName;

    private String phone;

    private String address;

    private Integer sex;

    private String qq;

    private Integer status;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    private String email;

    private String pic;
}
