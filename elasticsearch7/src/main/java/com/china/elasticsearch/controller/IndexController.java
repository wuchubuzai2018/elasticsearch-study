package com.china.elasticsearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 默认首页的控制器
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "forward:/index.html";
    }


}
