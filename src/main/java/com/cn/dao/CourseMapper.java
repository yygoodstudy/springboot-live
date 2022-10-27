package com.cn.dao;

import com.cn.pojo.vo.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author YaoJie
 * @Date 2021/10/20
 */

@Mapper
public interface CourseMapper {

    List<Course> findCourseByClassify(@Param("id") Integer id);

    Course findCourseDetailById(@Param("id") Integer id);


    Course findByCourseId(Integer id);
}
