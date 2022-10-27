package com.cn.controller;

import com.cn.pojo.req.UserReq;
import com.cn.pojo.resp.BaseResp;
import com.cn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;



/**
 * @Author YaoJie
 * @Date 2021/10/19
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 验证邮箱（是否可用）
     * @param email
     * @return
     */
    @RequestMapping("/validataEmail")
    public BaseResp validataEmail(@RequestParam("email") String email){

        return userService.validataEmail(email);
    }

    /**
     * 校验用户名是否可用
     * @param userReq
     * @return
     */
    @RequestMapping("/validataloginName")
    public BaseResp validataloginName(@RequestBody UserReq userReq){

        return userService.validataloginName(userReq);
    }



    /**
     * 发送验证码
     * @param email
     * @return
     */
    @RequestMapping("/getCode")
    public BaseResp getCode(@RequestParam("email") String email){

        return userService.getCode(email);

    }


    /**
     * 注册
     * @param userReq
     * @return
     */
    @RequestMapping("/registry")
    public BaseResp registry(@RequestBody UserReq userReq){

        return userService.registry(userReq);
    }


    /**
     * 登录
     * @param userReq
     * @return
     */
    @RequestMapping("/login")
    public BaseResp login(@RequestBody UserReq userReq){

        return userService.login(userReq);
    }


    /**
     * 用户信息展示
     * @param request
     * @return
     */
    @RequestMapping("/findUserByToken")
    public BaseResp findUserByToken(HttpServletRequest request){
        return userService.findUserByToken(request.getHeader("token").toString());
    }
}
