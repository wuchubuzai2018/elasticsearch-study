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
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        //系统启动时,创建索引并设置mapping,如果未明显设置mapping,field注解配置不会生效
        movieDao.createIndexAndMapping(MovieEntity.class);
        System.out.println("--------------初始化时创建索引" + MovieConstant.MOVIE_INDEX_NAME);
    }


    /**
     * 保存方法
     */
    @Override
    public void saveMovie() {
        MovieEntity entity = new MovieEntity();
        entity.setMovieId("27941");
        entity.setMovieName("沉默的证人");
        entity.setActors("张家辉  任贤齐  郭羡妮  郭晋安  吴卓羲  欧阳靖  杨紫  马书良  陈家乐  冯嘉怡  李成敏  明鹏");
        entity.setType("动作");
        entity.setArea("大陆");
        entity.setLanguage("国语");
        entity.setDirector("雷尼·哈林");
        entity.setReleaseDate("2019-08-17");
        entity.setScore(6.1);
        entity.setYear(2019);
        String documentId = movieDao.save(entity);
        System.out.println("----------保存成功------------------");
    }

    /**
     * 保存方法
     */
    @Override
    public void saveBatchMovie() {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0;i < 5;i++){
            MovieEntity entity = new MovieEntity();
            entity.setMovieId("27941" + (i + 1));
            entity.setMovieName("沉默的证人" + i);
            entity.setActors("张家辉  任贤齐  郭羡妮  郭晋安  吴卓羲  欧阳靖  杨紫  马书良  陈家乐  冯嘉怡  李成敏  明鹏" + i);
            entity.setType("动作");
            entity.setArea("大陆");
            entity.setLanguage("国语");
            entity.setDirector("雷尼·哈林");
            entity.setReleaseDate("2019-08-17");
            entity.setScore(6.1);
            entity.setYear(2019);
            list.add(entity);
        }
        movieDao.saveBatch(list);
    }

    @Override
    public void startDownloadMovie(){
        System.out.println("--------------准备开始爬取--------------");
        List<MovieEntity> list = MovieDownloadUtil.startGetMovies();
        System.out.println("--------------爬取完成--------------");
        movieDao.saveBatch(list);
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
        System.out.println(queryBuilder.toString());
        Page pageInfo = movieDao.queryForPage(queryBuilder,page,pageSize,MovieEntity.class);
        PageEntity entity = new PageEntity();
        entity.setDataList(pageInfo.getContent());
        entity.setTotalCount(pageInfo.getTotalElements());
        entity.setTotalPage(pageInfo.getTotalPages());
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
