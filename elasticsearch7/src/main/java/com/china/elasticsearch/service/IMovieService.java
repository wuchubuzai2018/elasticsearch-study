package com.china.elasticsearch.service;

import com.china.elasticsearch.bean.MovieEntity;
import com.china.elasticsearch.bean.PageEntity;

import java.util.List;

/**
 * @date 2019-08-17
 */
public interface IMovieService {

    public void saveMovie();

    public void saveBatchMovie();

    public List<MovieEntity> getAllMovie();

    public void startDownloadMovie();

    public PageEntity getAllMovieForPage(String searchActors,int page, int pageSize);

    public void deleteAllMovie();

    public void deleteMovieById(String movieId);
}
