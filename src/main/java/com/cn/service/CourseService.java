package com.cn.service;

import com.cn.pojo.resp.BaseResp;

/**
 * @Author YaoJie
 * @Date 2021/10/20
 */
public interface CourseService {
    BaseResp findCourseByClassify(Integer id);

    BaseResp findCourseDetailById(Integer id);

    BaseResp findCourseCommentByCourseId(Integer id, Integer page, Integer size);
}
