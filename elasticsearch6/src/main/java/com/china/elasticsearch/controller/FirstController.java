package com.china.elasticsearch.controller;

import com.china.elasticsearch.service.IFirstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于测试Spring Boot的控制器
 * @date 2019-08-10
 */
@Controller
@RequestMapping("first")
public class FirstController {

    @Autowired
    private IFirstService firstService;

    @RequestMapping("/test1")
    @ResponseBody
    public Map<String,Object> hello() {
        Map<String,Object> rMap = new HashMap<String,Object>();
        rMap.put("success",true);
        rMap.put("message","这是一个控制器的方法");
        return rMap;
    }


}
