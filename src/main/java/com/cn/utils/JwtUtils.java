package com.cn.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


import java.util.HashMap;
import java.util.Map;

/**
 * @Author YaoJie
 * @Date 2021/10/19
 */


public class JwtUtils {

    public String createToken(Map user){
        //设置算法以及签名
        Algorithm algorithmHS = Algorithm.HMAC256("secret");
        //设置头部
        Map map = new HashMap<>();
        map.put("typ","JWT");
        map.put("alg","HS256");
        String token = JWT.create().withHeader(map)
                //签发人
                .withIssuer("auth0")
                //主题
                .withSubject("login")
                //受众
                .withAudience("users")
                //自定义载荷
                .withClaim("userid",Integer.valueOf(user.get("userid").toString()))
                .sign(algorithmHS);
        return token;
    }

    public Map verfiyToken(String token){
        Map map = new HashMap();


        Algorithm algorithmHS = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithmHS)
                .withIssuer("auth0")
                .build(); //Reusable verifier instance
        try {

            DecodedJWT jwt = verifier.verify(token);
            map.put("status",true);
            map.put("userid",jwt.getClaim("userid").asInt());
            return map;
        }catch (Exception e){
            map.put("status",false);
            return map;
        }
    }
}
