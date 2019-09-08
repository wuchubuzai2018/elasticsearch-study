package com.china.elasticsearch.service.impl;

import com.china.elasticsearch.bean.MovieEntity;
import com.china.elasticsearch.bean.PageEntity;
import com.china.elasticsearch.bean.jest.JestMovieEntity;
import com.china.elasticsearch.dao.IJestMovieDao;
import com.china.elasticsearch.service.IJestMovieService;
import com.china.elasticsearch.util.MovieDownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @date 2019-08-29
 */
@Service
public class JestMovieService implements IJestMovieService {

    @Autowired
    private IJestMovieDao jestMovieDao;

    public static final String JEST_MOVIE_INDEXNAME = "jest_movie";

    public static final String JEST_MOVIE_TYPENAME = "_doc";

    @Override
    public void startDownloadMovie(){
        System.out.println("--------------准备开始爬取--------------");
        List<MovieEntity> list = MovieDownloadUtil.startGetMovies();
        System.out.println("--------------爬取完成--------------");
        try {
            jestMovieDao.saveBatch(list,JEST_MOVIE_INDEXNAME,JEST_MOVIE_TYPENAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------------保存完成--------------");
    }


    @Override
    public PageEntity getAllMovieForPage(int page, int pageSize) throws Exception{
        return jestMovieDao.queryForPage(page,pageSize,JEST_MOVIE_INDEXNAME
                ,JEST_MOVIE_TYPENAME, JestMovieEntity.class);
    }

    @Override
    public boolean deleteMovieById(String movieId) throws Exception{
        return jestMovieDao.deleteById(JEST_MOVIE_INDEXNAME,JEST_MOVIE_TYPENAME,movieId);
    }

    @Override
    public boolean deleteAllMovie() throws Exception {
        return jestMovieDao.deleteAll(JEST_MOVIE_INDEXNAME,JEST_MOVIE_TYPENAME);
    }


}
