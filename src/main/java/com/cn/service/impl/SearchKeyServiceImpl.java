package com.cn.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.cn.pojo.resp.BaseResp;
import com.cn.pojo.vo.Course;
import com.cn.service.SearchKeyService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author YaoJie
 * @Date 2021/11/5
 */

@Service
public class SearchKeyServiceImpl implements SearchKeyService {

    //导入客户端
    @Autowired
    RestHighLevelClient client;

    @Override
    public BaseResp searchKey(String key, Integer page, Integer size) {

        BaseResp baseResp = new BaseResp();

        List list=new ArrayList<>();
        //创建搜索对象
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("course");
        searchRequest.types("doc");
        //创建搜索规则
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //判断用户输入的key
        if(key.isEmpty()){
            //如果用户没有搜索，查询全部
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        }else{
            //进行一次可以匹配多个字段查询
            searchSourceBuilder.query(QueryBuilders.multiMatchQuery(key,"courseName","courseDescription"));
        }

        //设置分页
        searchSourceBuilder.size(size);
        searchSourceBuilder.from((page-1)*size);

        //设置高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font style='color:red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("courseName");
        highlightBuilder.field("courseDescription");
        searchSourceBuilder.highlighter(highlightBuilder);

        //搜索规则放入搜索对象中
        searchRequest.source(searchSourceBuilder);
        //将搜索对象放入客户端中进行搜索
        try {
            SearchResponse search = client.search(searchRequest);
            SearchHits hits = search.getHits();
            //获取总条数
            baseResp.setTotal(hits.getTotalHits());
            SearchHit[] hits1 = hits.getHits();
            if(hits1 !=null && hits1.length>0){
                for (SearchHit hit:hits1){
                    //声明变量
                    String course="";
                    String courseDescription="";
                    //获取hits1中的高亮字段名称
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    if(highlightFields !=null){
                        HighlightField courseName = highlightFields.get("courseName");
                        HighlightField description = highlightFields.get("courseDescription");
                        if(courseName !=null){
                            Text[] fragments = courseName.getFragments();
                            StringBuffer stringBuffer = new StringBuffer();
                            if(courseName !=null){
                                for(Text text:fragments){
                                    course=stringBuffer.append(text).toString();
                                }
                            }
                        }
                        if(description !=null){
                            Text[] fragments = description.getFragments();
                            StringBuffer stringBuffer = new StringBuffer();
                            if(courseName !=null){
                                for(Text text:fragments){
                                    courseDescription=stringBuffer.append(text).toString();
                                }
                            }
                        }
                    }
                    //从索引库获取数据
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    if(course !=null){
                        sourceAsMap.put("course",course);
                    }
                    if(courseDescription !=null){
                        sourceAsMap.put("courseDescription",courseDescription);
                    }

                    String s = JSONObject.toJSONString(sourceAsMap);
                    Course course1 = JSONObject.parseObject(s, Course.class);
                    list.add(course1);
                }
                baseResp.setCode(0);
                baseResp.setMsg("查询成功");
                baseResp.setData(list);
                return baseResp;
            }
            baseResp.setCode(1);
            baseResp.setMsg("查询失败");
            return baseResp;

        } catch (IOException e) {
            e.printStackTrace();
        }
        baseResp.setCode(1);
        baseResp.setMsg("查询失败");
        return baseResp;
    }
}
