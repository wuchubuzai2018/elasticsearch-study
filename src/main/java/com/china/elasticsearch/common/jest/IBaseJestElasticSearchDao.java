package com.china.elasticsearch.common.jest;


import com.china.elasticsearch.bean.PageEntity;

import java.util.List;

/**
 * 基于Jest的操作
 */
public interface IBaseJestElasticSearchDao {

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










}
