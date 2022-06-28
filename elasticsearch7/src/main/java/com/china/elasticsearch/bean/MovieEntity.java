package com.china.elasticsearch.bean;

import com.china.elasticsearch.constant.MovieConstant;
import io.searchbox.annotations.JestId;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

import java.io.Serializable;
import java.util.Date;

/**
 * 基于Spring Data ElasticSearchTemplate
 * 计划从80s上爬取电影信息
 */
@Document(indexName = MovieConstant.MOVIE_INDEX_NAME)
@Mapping(mappingPath="/mapping/moviemapping.json")
public class MovieEntity implements Serializable {


    /**电影ID*/
    @Id
    @JestId
    private String movieId;

    /**电影名称*/
    private String movieName;

    /**演员*/
    //@Field(type = FieldType.Text,analyzer="whitespace",searchAnalyzer="ik_smart")
    private String actors;

    /**类型:战争*/
    private String type;

    /**地区：大陆*/
    private String area;

    /**导演*/
    private String director;

    /**上映日期,暂时无法获取*/
    private String releaseDate;

    /**豆瓣评分*/
    private double score;

    /**语言*/
    private String language;

    /**年份*/
    private int year;

    /**提示*/
    private String tip;

    /**片长*/
    private int minute;

    private Date createDate;

    private Date updateDate;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
