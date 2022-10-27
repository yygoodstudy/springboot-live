package com.cn.config;

import com.cn.interceptor.MyInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Component
public class MyWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置哪些请求拦截，哪些请求放行
       registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**")
               .excludePathPatterns("/user/**").excludePathPatterns("/livePay/**");
    }

}
