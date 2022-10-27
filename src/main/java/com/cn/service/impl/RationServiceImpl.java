package com.cn.service.impl;

import com.cn.dao.RationRepository;
import com.cn.pojo.resp.BaseResp;
import com.cn.pojo.vo.Ration;
import com.cn.service.RationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @Author YaoJie
 * @Date 2021/10/20
 */

@Service
public class RationServiceImpl implements RationService {

    @Autowired
    RationRepository rationRepository;

    @Autowired
    RedisTemplate redisTemplate;

    protected Logger logger = LoggerFactory.getLogger(RationServiceImpl.class);

    @Override
    public BaseResp findAll() {
        //从redis中获取数据
        Boolean ration_list = redisTemplate.hasKey("ration_list");
        Set ration_list1=null;
        if(!ration_list){
            //去数据库查
            List<Ration> status = rationRepository.findByStatus("start");
            //查询之后放置到redis中，使用zset结构
            logger.info("从数据库查询轮播图："+status);
            for(Ration ra:status){
                redisTemplate.opsForZSet().add("ration_list",ra,ra.getImageIndex());
            }
            //再从redis中取出该值
            ration_list1= redisTemplate.opsForZSet().reverseRange("ration_list", 0, -1);
        }else{
            ration_list1= redisTemplate.opsForZSet().reverseRange("ration_list", 0, -1);
            logger.info("从redis查询轮播图："+ration_list1);
        }

        return new BaseResp(0,"查询成功",null,ration_list1);
    }
}
