package com.china.elasticsearch.common.jest;


import com.china.elasticsearch.bean.PageEntity;

import java.util.List;

/**
 * 基于Jest的操作
 */
public interface IBaseJestElasticSearchDao {

    /**
     * 创建索引
     * @param indexName
     * @return
     * @throws Exception
     */
    public boolean createIndex(String indexName)throws Exception;


    /**
     * 删除所有
     * @param indexName
     * @return
     * @throws Exception
     */
    public boolean deleteIndex(String indexName)throws Exception;

    /**
     * 设置索引的mapping
     * @param indexName
     * @param type
     * @param mappingString
     * @throws Exception
     */
    public void createIndexMapping(String indexName, String type, String mappingString)throws Exception;

    /**
     * 单条保存
     * @param object
     * @param indexName
     * @param typeName
     * @throws Exception
     */
    public void save(Object object,String indexName,String typeName)throws Exception;

    /**
     * 批量保存
     * @param objectList
     */
    public void saveBatch(List<?> objectList,String indexName,String typeName) throws Exception;


    /**
     * 分页查询数据
     * @return
     */
    public PageEntity queryForPage(int curPage, int pageSize,
                                   String indexName, String typeName, Class<?> entityClass)throws Exception;


    /**
     * 输入条件进行查询
     * @param searchBody
     * @param indexName
     * @param typeName
     * @return
     * @throws Exception
     */
    public String queryForString(String searchBody,String indexName,String typeName)throws Exception;







}
