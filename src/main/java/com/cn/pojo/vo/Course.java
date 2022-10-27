package com.cn.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private Integer id;

    private String  courseName;

    private String coursePic;

    private String courseDescription;

    private Integer courseTeacher;

    private String users;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    private Double pay;

    private Teacher teacher;

}
