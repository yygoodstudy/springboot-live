package com.cn.service.impl;

import com.cn.dao.TeacherMapper;
import com.cn.pojo.resp.BaseResp;

import com.cn.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author YaoJie
 * @Date 2021/10/20
 */

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    TeacherMapper teacherMapper;


    @Override
    public BaseResp findAll() {

        List list=new ArrayList();
        //先从redis中获取数据
        Boolean teacher_list = redisTemplate.hasKey("teacher_list");
        if(!teacher_list){
            list = teacherMapper.findAll();
            //存入redis中
            redisTemplate.opsForList().leftPushAll("teacher_list",list);
        }else{
            list = redisTemplate.opsForList().range("teacher_list", 0, -1);
        }
        return new BaseResp(0,"查询成功",null,list);
    }
}
