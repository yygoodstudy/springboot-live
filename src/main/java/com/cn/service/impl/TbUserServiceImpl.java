package com.cn.service.impl;

import com.cn.dao.TbUserMapper;
import com.cn.pojo.resp.BaseResp;
import com.cn.pojo.vo.TbUser;
import com.cn.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author YaoJie
 * @Date 2022/3/7
 */

@Service
public class TbUserServiceImpl implements TbUserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    /**
     * 新增
     * @param user
     * @return
     */
    @Override
    public BaseResp insertTb(TbUser user) {
        int insert = tbUserMapper.insert(user);
        return new BaseResp(0,"新增成功",null,null);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public BaseResp deleteTbUser(Integer id) {
        TbUser tbUser = tbUserMapper.selectById(id);
        return new BaseResp(0,"删除成功",null,tbUser);
    }

    /**
     * 修改
     * @param user
     * @return
     */
    @Override
    public BaseResp tbUpdate(TbUser user) {
        int i = tbUserMapper.updateById(user);
        return new BaseResp(0,"修改成功",null,null);
    }

    /**
     * 查询
     * @return
     */
    @Override
    public BaseResp selectTb(Integer id) {
        TbUser tbUser = tbUserMapper.selectById(id);
        return new BaseResp(0,"查询成功",null,tbUser);
    }
}
