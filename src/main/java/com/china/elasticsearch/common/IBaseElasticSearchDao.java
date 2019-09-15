package com.china.elasticsearch.common;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @date 2019-08-17
 */
public interface IBaseElasticSearchDao {

    /**
     * 创建索引
     * @param classes
     */
    public void createIndex(Class<?> classes);


    /**
     * 设置mapping
     * @param classes
     */
    public void putMapping(Class<?> classes);


    /**
     * 创建索引并设置mapping
     * @param classes
     */
    public void createIndexAndMapping(Class<?> classes);

    /**
     * 单条保存
     * @param object
     * @return
     */
    public String save(Object object);

    /**
     * 批量保存
     * @param objectList
     */
    public void saveBatch(List<?> objectList);

    /**
     * 查询所有数据
     * @param entityClass
     * @return
     */
    public List<?> queryForList(Class<?> entityClass);


    /**
     * 分页查询数据
     * @param entityClass
     * @return
     */
    public Page<?> queryForPage(int curPage, int pageSize, Class<?> entityClass);

    /**
     * 根据查询条件进行查询
     * @param queryBuilder
     * @param curPage
     * @param pageSize
     * @param entityClass
     * @return
     */
    public Page<?> queryForPage(QueryBuilder queryBuilder, int curPage, int pageSize, Class<?> entityClass);

        /**
         * 删除索引中的数据
         * @param entityClass
         */
    public void deleteAll(Class<?> entityClass);


    /**
     * 根据ID进行删除
     * @param entityClass
     * @param id
     */
    public void deleteById(Class<?> entityClass,String id);
}
