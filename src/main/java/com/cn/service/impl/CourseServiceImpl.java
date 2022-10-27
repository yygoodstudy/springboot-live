package com.cn.service.impl;

import com.cn.dao.CommentMapper;
import com.cn.dao.CourseMapper;
import com.cn.pojo.resp.BaseResp;
import com.cn.pojo.vo.Comment;
import com.cn.pojo.vo.Course;
import com.cn.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author YaoJie
 * @Date 2021/10/20
 */

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    CommentMapper commentMapper;

    protected Logger logger = LoggerFactory.getLogger(RationServiceImpl.class);

    @Override
    public BaseResp findCourseByClassify(Integer id) {


        //先从redis中获取数据
        Object course_list = redisTemplate.opsForHash().get("course_list", id.toString());
        Object course_list1=null;
        if(course_list==null){
            //从数据库中查询
            List<Course> courseByClassify = courseMapper.findCourseByClassify(id);

            logger.info("从数据库查询的课程名称："+courseByClassify);
            //存入到redis中
            Map map=new HashMap();
            map.put(id.toString(),courseByClassify);
            redisTemplate.opsForHash().putAll("course_list",map);

            //获取数据
            course_list1 = redisTemplate.opsForHash().get("course_list",id.toString());
        }else{
            logger.info("从redis查询的课程名称："+course_list1);
            course_list1 = redisTemplate.opsForHash().get("course_list",id.toString());
        }
        return new BaseResp(0,"查询成功",null,course_list1);
    }



    @Override
    public BaseResp findCourseDetailById(Integer id) {

        //先从rides中获取数据
        Object courseDetail = redisTemplate.opsForHash().get("CourseDetail", id.toString());
        Object detail =null;
        if(courseDetail ==null){
            Course course = courseMapper.findCourseDetailById(id);
            //存入redis中
            redisTemplate.opsForHash().put("CourseDetail",id.toString(),course);
            detail=redisTemplate.opsForHash().get("CourseDetail", id.toString());
        }else{
            detail=redisTemplate.opsForHash().get("CourseDetail", id.toString());
        }

        return new BaseResp(0,"查询成功",null,detail);
    }


    @Override
    public BaseResp findCourseCommentByCourseId(Integer id, Integer page, Integer size) {

        //首先去redis中查询数据
        Boolean aBoolean = redisTemplate.hasKey("comment" + id.toString());
        //定义总页数
        Long total=null;
        //计算起始页数
        int start=(page-1)*size;

        //定义一个集合
        List list=null;
        if(!aBoolean){
            //如果没有去数据库查
            List<Comment> courseCommentByCourseId = commentMapper.findCourseCommentByCourseId(id);
            if (courseCommentByCourseId!=null&&courseCommentByCourseId.size()>0) {
                //然后存到redis中
                redisTemplate.opsForList().leftPushAll("comment" + id.toString(), courseCommentByCourseId);
                //然后进行分页

                total = redisTemplate.opsForList().size("comment" + id.toString());
                list = redisTemplate.opsForList().range("comment" + id.toString(), start, total + size);
            }
        }else{
            total = redisTemplate.opsForList().size("comment" + id.toString());
            list = redisTemplate.opsForList().range("comment" + id.toString(), start, total + size);
        }
        return new BaseResp(0,"查询成功",total,list);
    }
}
