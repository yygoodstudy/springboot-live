package com.cn.controller;

import com.cn.pojo.resp.BaseResp;
import com.cn.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author YaoJie
 * @Date 2021/10/20
 */


@RestController
@RequestMapping(value = "/course")
public class CourseController {

    @Autowired
    CourseService courseService;


    /**
     * 课程分类
     * @param id
     * @return
     */
    @RequestMapping(value = "/findCourseByClassify/{id}")
    public BaseResp findCourseByClassify(@PathVariable Integer id){

        return courseService.findCourseByClassify(id);
    }


    /**
     * 根据课程id查询课程详细信息
     * @param id
     * @return
     */
    @RequestMapping("/findCourseDetailById/{id}")
    public BaseResp findCourseDetailById(@PathVariable("id") Integer id){

        return courseService.findCourseDetailById(id);
    }


    /**
     * 根据课程id查询评论列
     * @return
     */
    @RequestMapping("/findCourseCommentByCourseId/{id}/{page}/{size}")
    public BaseResp findCourseCommentByCourseId(@PathVariable("id") Integer id,
                                                @PathVariable("page") Integer page,
                                                @PathVariable("size") Integer size){

        return courseService.findCourseCommentByCourseId(id,page,size);
    }
}
