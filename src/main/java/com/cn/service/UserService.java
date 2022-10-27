package com.cn.service;

import com.cn.pojo.req.UserReq;
import com.cn.pojo.resp.BaseResp;

/**
 * @Author YaoJie
 * @Date 2021/10/19
 */
public interface UserService {
    BaseResp validataEmail(String email);

    BaseResp validataloginName(UserReq userReq);

    BaseResp getCode(String email);

    BaseResp registry(UserReq userReq);

    BaseResp login(UserReq userReq);

    BaseResp findUserByToken(String token);
}
