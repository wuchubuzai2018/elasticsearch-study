package com.china.elasticsearch.controller;

import com.china.elasticsearch.bean.PageEntity;
import com.china.elasticsearch.dao.IJestMovieDao;
import com.china.elasticsearch.service.IJestMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 基于Jest操作的控制器,也是操作80s电影爬虫
 * @date 2019-08-29
 */
@Controller
@RequestMapping("jestmovie")
public class JestMovieController {

    @Autowired
    private IJestMovieService jestMovieService;

    /**
     * 爬虫下载所有电影
     * @date 2019-08-29
     * @return
     */
    @RequestMapping("/startDownloadMovie")
    @ResponseBody
    public Map<String,Object> startDownloadMovie() {
        Map<String,Object> rMap = new HashMap<String,Object>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                jestMovieService.startDownloadMovie();
            }
        }).start();
        rMap.put("success",true);
        return rMap;
    }

    /**
     * 分页查询所有电影
     * @date 2019-08-29
     * @return
     */
    @RequestMapping("/getAllMovieForPage")
    @ResponseBody
    public Map<String,Object> getAllMovieForPage(int page,int pageSize) throws Exception{
        Map<String,Object> rMap = new HashMap<String,Object>();
        PageEntity pageObj = jestMovieService.getAllMovieForPage(page,pageSize);
        rMap.put("page",pageObj);
        rMap.put("success",true);
        return rMap;
    }

    /**
     * 删除所有数据
     * @date 2019-09-07
     * @return
     */
    @RequestMapping("/deleteAllMovie")
    @ResponseBody
    public Map<String,Object> deleteAllMovie() throws Exception {
        Map<String,Object> rMap = new HashMap<String,Object>();
        boolean success = jestMovieService.deleteAllMovie();
        rMap.put("success",success);
        return rMap;
    }

    /**
     * 删除指定ID的数据
     * @date 2019-09-07
     * @return
     */
    @RequestMapping("/deleteMovieById")
    @ResponseBody
    public Map<String,Object> deleteMovieById(String movieId)throws Exception {
        Map<String,Object> rMap = new HashMap<String,Object>();
        boolean success = jestMovieService.deleteMovieById(movieId);
        rMap.put("success",success);
        return rMap;
    }


}
