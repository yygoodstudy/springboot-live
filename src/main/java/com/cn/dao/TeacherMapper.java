package com.cn.dao;

import com.cn.pojo.vo.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author YaoJie
 * @Date 2021/10/20
 */


@Mapper
public interface TeacherMapper {

    List<Teacher> findAll();
}
