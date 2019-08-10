package com.china.elasticsearch.controller;

import com.china.elasticsearch.bean.FirstBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("es")
public class FirstESController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @RequestMapping("/test1")
    @ResponseBody
    public String hello() {

        //创建索引
        boolean result =  elasticsearchTemplate.createIndex(FirstBook.class);
        System.out.println(result);

        System.out.println("elasticsearch hello");
        return "Hello elasticsearch";
    }

}
