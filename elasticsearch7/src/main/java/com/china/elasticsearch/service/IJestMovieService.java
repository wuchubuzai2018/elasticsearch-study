package com.china.elasticsearch.service;

import com.china.elasticsearch.bean.PageEntity;

/**
 *
 * @date 2019-08-29
 */
public interface IJestMovieService {

    public void startDownloadMovie();

    public PageEntity getAllMovieForPage(int page, int pageSize)throws Exception;

    public boolean deleteMovieById(String movieId)throws Exception;

    public boolean deleteAllMovie()throws Exception;
}
