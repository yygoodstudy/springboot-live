package com.cn.controller;

import com.cn.pojo.resp.BaseResp;
import com.cn.service.SearchKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author YaoJie
 * @Date 2021/11/5
 */

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchKeyService searchKeyService;

    /**
     * 搜索
     * @param key
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/searchKey/{key}/{page}/{size}")
    public BaseResp searchKey(@PathVariable("key")String key,
                                @PathVariable("page")Integer page,
                                        @PathVariable("size")Integer size){

        return searchKeyService.searchKey(key,page,size);

    }
}
