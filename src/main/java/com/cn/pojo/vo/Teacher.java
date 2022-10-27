package com.cn.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author YaoJie
 * @Date 2021/10/20
 */


@Data
public class Teacher {


    private Integer id;

    private String teachName;

    private String teachDescription;

    private String teachImage;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
}
