package com.china.elasticsearch.controller;

import com.china.elasticsearch.bean.MovieEntity;
import com.china.elasticsearch.bean.PageEntity;
import com.china.elasticsearch.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
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
     * @date 2019-08-17
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
     * @date 2019-08-17
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

    /**
     * 查询所有电影
     * @date 2019-08-17
     * @return
     */
    @RequestMapping("/getAllMovie")
    @ResponseBody
    public Map<String,Object> getAllMovie() {
        Map<String,Object> rMap = new HashMap<String,Object>();
        List<MovieEntity> list = movieService.getAllMovie();
        rMap.put("list",list);
        rMap.put("success",true);
        return rMap;
    }

    /**
     * 分页查询所有电影
     * @date 2019-08-18
     * @return
     */
    @RequestMapping("/getAllMovieForPage")
    @ResponseBody
    public Map<String,Object> getAllMovieForPage(int page,int pageSize) {
        Map<String,Object> rMap = new HashMap<String,Object>();
        PageEntity pageObj = movieService.getAllMovieForPage(page,pageSize);
        rMap.put("page",pageObj);
        rMap.put("success",true);
        return rMap;
    }


    /**
     * 爬虫下载所有电影
     * @date 2019-08-18
     * @return
     */
    @RequestMapping("/startDownloadMovie")
    @ResponseBody
    public Map<String,Object> startDownloadMovie() {
        Map<String,Object> rMap = new HashMap<String,Object>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                movieService.startDownloadMovie();
            }
        }).start();
        rMap.put("success",true);
        return rMap;
    }

    /**
     * 删除所有数据
     * @date 2019-08-18
     * @return
     */
    @RequestMapping("/deleteAllMovie")
    @ResponseBody
    public Map<String,Object> deleteAllMovie() {
        Map<String,Object> rMap = new HashMap<String,Object>();
        movieService.deleteAllMovie();
        rMap.put("success",true);
        return rMap;
    }

    /**
     * 删除指定ID的数据
     * @date 2019-09-07
     * @return
     */
    @RequestMapping("/deleteMovieById")
    @ResponseBody
    public Map<String,Object> deleteMovieById(String movieId) {
        Map<String,Object> rMap = new HashMap<String,Object>();
        movieService.deleteMovieById(movieId);
        rMap.put("success",true);
        return rMap;
    }



}
