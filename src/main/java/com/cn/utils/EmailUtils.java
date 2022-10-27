package com.cn.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author YaoJie
 * @Date 2021/10/19
 */

@Component
public class EmailUtils {

    @Value("1617135757@qq.com")
    private String from;

    @Autowired
    JavaMailSender javaMailSender;

    public Map send(String mail){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //发给谁
        simpleMailMessage.setTo(mail);
        simpleMailMessage.setSubject("测试邮件");
        simpleMailMessage.setFrom(from);

        //设置邮件内容
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<=4;i++){
            int anInt = new Random().nextInt(10);
            stringBuffer.append(anInt);
        }
        simpleMailMessage.setText(stringBuffer.toString());
        javaMailSender.send(simpleMailMessage);
        Map map = new HashMap<>();
        map.put("code",stringBuffer.toString());
        return map;
    }
}
