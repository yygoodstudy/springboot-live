package com.cn.pojo.vo;

import lombok.Data;

/**
 * @Author YaoJie
 * @Date 2021/10/21
 */

@Data
public class Comment {

    private Integer id;

    private String comment;

    private Integer userId;

    private Integer courseId;

    private Integer score;

    private User user;
}
