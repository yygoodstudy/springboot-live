package com.cn.dao;

import com.cn.pojo.vo.Comment;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author YaoJie
 * @Date 2021/10/21
 */

@Mapper
public interface CommentMapper {

    List<Comment> findCourseCommentByCourseId(@Param("id") Integer id);
}
