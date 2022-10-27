package com.cn.service;

import com.cn.pojo.resp.BaseResp;
import com.cn.pojo.vo.TbUser;


/**
 * @Author YaoJie
 * @Date 2022/3/7
 */
public interface TbUserService {
    /**
     * 新增
     * @return
     */
     BaseResp insertTb(TbUser user);

    /**
     * 删除
     * @param id
     * @return
     */
    BaseResp deleteTbUser(Integer id);

    /**
     * 修改
     * @param user
     * @return
     */
    BaseResp tbUpdate(TbUser user);

    /**
     * 查询
     * @return
     */
    BaseResp selectTb(Integer id);
}
