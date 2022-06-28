package com.china.elasticsearch.service.impl;

import com.china.elasticsearch.bean.MovieEntity;
import com.china.elasticsearch.bean.PageEntity;
import com.china.elasticsearch.constant.MovieConstant;
import com.china.elasticsearch.dao.IMovieDao;
import com.china.elasticsearch.service.IMovieService;
import com.china.elasticsearch.util.MovieDownloadUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * @date 2019-08-17
 */
@Service
public class MovieService implements IMovieService {

    @Autowired
    private IMovieDao movieDao;

    /**
     * 初始化执行的方法
     */
    @PostConstruct
    public void init(){

    }


    /**
     * 保存方法
     */
    @Override
    public void saveMovie() {

    }

    /**
     * 保存方法
     */
    @Override
    public void saveBatchMovie() {

    }

    @Override
    public void startDownloadMovie(){
        System.out.println("--------------准备开始爬取--------------");
        List<MovieEntity> list = MovieDownloadUtil.startGetMovies();
        if(list != null && list.size() > 0){
            for(MovieEntity entity : list){
                entity.setCreateDate(new Date());
            }
        }
        System.out.println("--------------爬取完成--------------");
        movieDao.saveBatch(list,MovieEntity.class);
        System.out.println("--------------保存完成--------------");
    }

    @Override
    public PageEntity getAllMovieForPage(String searchActors,int page, int pageSize) {
        QueryBuilder  queryBuilder = QueryBuilders.matchAllQuery();
        if(searchActors != null && searchActors.trim().length() > 0){
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(QueryBuilders.matchQuery("actors",searchActors).operator(Operator.AND));
            queryBuilder = boolQueryBuilder;
        }
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder).build();

        List<MovieEntity> movieList = movieDao.queryForPage(page,pageSize,MovieEntity.class, MovieConstant.MOVIE_INDEX_NAME);
        long total = movieDao.queryCount(searchQuery,MovieEntity.class, MovieConstant.MOVIE_INDEX_NAME);
        PageEntity entity = new PageEntity();
        entity.setDataList(movieList);
        entity.setTotalCount(total);
        entity.setTotalPage(30);
        return entity;
    }


    @Override
    public List<MovieEntity> getAllMovie(){
        return (List<MovieEntity>)movieDao.queryForList(MovieEntity.class);
    }


    @Override
    public void deleteAllMovie(){
        movieDao.deleteAll(MovieEntity.class);
    }


    @Override
    public void deleteMovieById(String movieId){
        movieDao.deleteById(MovieEntity.class,movieId);
    }


}
