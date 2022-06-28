package com.china.elasticsearch.common;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.List;

/**
 * @date 2019-08-17
 */
public interface IBaseElasticSearchDao<T> {

    /**
     * 创建索引
     * @param classes
     */
    public void createIndex(Class<T> classes);


    /**
     * 设置mapping
     * @param classes
     */
    public void putMapping(Class<T> classes);


    /**
     * 创建索引并设置mapping
     * @param classes
     */
    public void createIndexAndMapping(Class<T> classes);

    /**
     * 单条保存
     * @param object
     * @return
     */
    public void save(T object);

    /**
     * 批量保存
     * @param objectList
     */
    public void saveBatch(List<T> objectList,Class<T> classz);

    /**
     * 查询所有数据
     * @param entityClass
     * @return
     */
    public List<?> queryForList(Class<T> entityClass);

    /**
     * 查询数量
     * @param query
     * @param entityClass
     * @param indexName
     * @return
     */
    public long queryCount(Query query, Class<T> entityClass, String indexName);

    /**
     * 分页查询数据
     * @param entityClass
     * @return
     */
    public List<T> queryForPage(int curPage, int pageSize, Class<T> entityClass,String indexName);

//    /**
//     * 根据查询条件进行查询
//     * @param queryBuilder
//     * @param curPage
//     * @param pageSize
//     * @param entityClass
//     * @return
//     */
//    public Page<?> queryForPage(QueryBuilder queryBuilder, int curPage, int pageSize, Class<T> entityClass);

        /**
         * 删除索引中的数据
         * @param entityClass
         */
    public void deleteAll(Class<T> entityClass);


    /**
     * 根据ID进行删除
     * @param entityClass
     * @param id
     */
    public void deleteById(Class<T> entityClass,String id);
}
