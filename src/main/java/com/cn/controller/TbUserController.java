package com.cn.controller;

import com.cn.pojo.resp.BaseResp;
import com.cn.pojo.vo.TbUser;
import com.cn.pojo.vo.User;
import com.cn.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author YaoJie
 * @Date 2022/3/7
 */

@RestController
@RequestMapping(value = "/tbUser")
public class TbUserController {

    @Autowired
    private TbUserService tbUserService;

    /**
     * 新增
     * @param tbUser
     * @return
     */
    @RequestMapping(value = "/insertTb/{user}",method = RequestMethod.POST)
    public BaseResp insertTb(@PathVariable TbUser tbUser){

        return tbUserService.insertTb(tbUser);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteTbUser/{id}",method = RequestMethod.GET)
    public BaseResp deleteTbUser(@PathVariable("id") Integer id){
        return tbUserService.deleteTbUser(id);
    }

    /**
     * 修改
     * @param user
     * @return
     */
    @RequestMapping(value = "/updateTbUser",method = RequestMethod.POST)
    public BaseResp tbUpdate(@RequestBody TbUser user){
        return tbUserService.tbUpdate(user);
    }

    /**
     * 查询
     * @return
     */
    @RequestMapping(value = "/seleteTbUser",method = RequestMethod.POST)
    public BaseResp selectTb(@RequestParam("id") Integer id){
        return tbUserService.selectTb(id);
    }
}
