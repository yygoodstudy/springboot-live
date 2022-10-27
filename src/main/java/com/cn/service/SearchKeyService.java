package com.cn.service;

import com.cn.pojo.resp.BaseResp;

/**
 * @Author YaoJie
 * @Date 2021/11/5
 */

public interface SearchKeyService {
    BaseResp searchKey(String key, Integer page, Integer size);
}
