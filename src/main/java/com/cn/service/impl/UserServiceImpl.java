package com.cn.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cn.dao.UserRepository;
import com.cn.pojo.req.UserReq;
import com.cn.pojo.resp.BaseResp;
import com.cn.pojo.vo.User;
import com.cn.service.UserService;
import com.cn.utils.EmailUtils;
import com.cn.utils.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author YaoJie
 * @Date 2021/10/19
 */


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    EmailUtils emailUtils;

    @Autowired
    RedisTemplate redisTemplate;

    //private static final  Map codeMap =new HashMap();


    @Override
    public BaseResp validataEmail(String email) {
        User userEmail = userRepository.findByEmail(email);
        //判断邮箱是否已存在
        if (userEmail !=null){
            return new BaseResp(1,"邮箱已被注册",null,null);
        }else{
            return new BaseResp(0,"邮箱未被注册",null,null);
        }
    }

    @Override
    public BaseResp validataloginName(UserReq userReq) {
        User userName = userRepository.findByLoginName(userReq.getLoginName());
        //判断用户名是否重复
        if(userName != null){
            return new BaseResp(1,"用户名已被人注册，不可使用",null,null);
        }
        return new BaseResp(0,"用户名未被人注册，可使用",null,null);
    }

    @Override
    public BaseResp getCode(String email) {
        //发送验证码
        Map send = emailUtils.send(email);
        //把验证码存放到redis中
        redisTemplate.opsForValue().set(email,send.get("code"));
        //设置验证码的时长
        redisTemplate.expire(email,300,TimeUnit.SECONDS);
        return new BaseResp(0,"发送成功",null,null);
    }

    @Override
    public BaseResp registry(UserReq userReq) {

        User userEmail = userRepository.findByEmail(userReq.getEmail());
        if(userEmail !=null){
            return new BaseResp(1,"邮箱已被注册！",null,null);
        }
        User userName = userRepository.findByLoginName(userReq.getLoginName());
        if(userName !=null){
            return new BaseResp(1,"用户名已被注册！",null,null);
        }

        //判断两次验证码输入是否一样
        String code = userReq.getCode();
        //判断Redis中是否存在验证码
        Boolean aBoolean = redisTemplate.hasKey(userReq.getEmail());
        if(!aBoolean){
            return new BaseResp(1,"验证码超时，未发送验证码！",null,null);
        }
        //比较前端获取的验证码和Redis中的验证码是否一样
        Object o = redisTemplate.opsForValue().get(userReq.getEmail());
        if(!o.equals(code)){
            return new BaseResp(1,"验证码错误！",null,null);
        }
        //注册、存入数据库中
        User user = new User();
        BeanUtils.copyProperties(userReq,user);
        User user1 = userRepository.saveAndFlush(user);
        if(user1 !=null){
            return new BaseResp(0,"注册成功",null,null);
        }
        return new BaseResp(1,"注册失败",null,null);
    }

    @Override
    public BaseResp login(UserReq userReq) {
        //登录
        User byLoginName = userRepository.findByLoginName(userReq.getLoginName());
        //判断用户名
        if(byLoginName ==null){
            return new BaseResp(1,"用户信息为空",null,null);
        }
        //判断密码
        if(!byLoginName.getPassword().equals(userReq.getPassword())){
            return new BaseResp(1,"密码错误",null,null);
        }
        //登录
        //生成jwt
        JwtUtils jwtUtils = new JwtUtils();
        Map map=new HashMap();
        map.put("userid",byLoginName.getId());
        String token = jwtUtils.createToken(map);
        //把token存入到redis中
        redisTemplate.opsForValue().set(token,byLoginName);
        return new BaseResp(0,"登录成功",null,token);
    }

    @Override
    public BaseResp findUserByToken(String token) {

        Boolean aBoolean = redisTemplate.hasKey(token);

        if(!aBoolean){
            return new BaseResp(1,"用户未登录",null,null);
        }

        Object o = redisTemplate.opsForValue().get(token);
        //使用json转换成对象进行返回
        String s = JSONObject.toJSONString(o);
        User user = JSONObject.parseObject(s, User.class);

        return new BaseResp(0,"查询成功",null,user);
    }
}
