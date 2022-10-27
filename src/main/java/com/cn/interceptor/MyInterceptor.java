package com.cn.interceptor;

import com.alibaba.fastjson.JSON;
import com.cn.pojo.resp.BaseResp;
import com.cn.utils.JwtUtils;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String method = request.getMethod();

        if (token==null||token==""){
            //拦截请求
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            BaseResp baseResp = new BaseResp(1,"未登录",null,null);
            writer.write(JSON.toJSONString(baseResp));
            return false;
        }
        JwtUtils jwtUtils = new JwtUtils();
        Map map = jwtUtils.verfiyToken(token);
        if (!(Boolean) map.get("status")){
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            BaseResp baseResp = new BaseResp(1,"token解析异常",null,null);
            writer.write(JSON.toJSONString(baseResp));
            return false;
        }
        return true;

    }
}
