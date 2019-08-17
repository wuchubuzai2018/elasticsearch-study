package com.china.elasticsearch.controller;

import com.china.elasticsearch.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 基于ElasticSearch操作的电影的控制器,底层使用统一代码
 * @date 2019-08-17
 */
@Controller
@RequestMapping("movie")
public class MovieController {

    @Autowired
    private IMovieService movieService;

    /**
     * 保存一条电影的信息
     * @return
     */
    @RequestMapping("/saveMovie")
    @ResponseBody
    public Map<String,Object> saveMovie() {
        Map<String,Object> rMap = new HashMap<String,Object>();
        movieService.saveMovie();
        rMap.put("success",true);
        return rMap;
    }

    /**
     * 保存多条电影的信息
     * @return
     */
    @RequestMapping("/saveBatchMovie")
    @ResponseBody
    public Map<String,Object> saveBatchMovie() {
        Map<String,Object> rMap = new HashMap<String,Object>();
        movieService.saveBatchMovie();
        rMap.put("success",true);
        return rMap;
    }





}
