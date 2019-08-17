package com.china.elasticsearch.common;

import java.util.List;

/**
 * @date 2019-08-17
 */
public interface IBaseElasticSearchDao {

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
    public void saveBatch(List<Object> objectList);


    public List<?> queryForList(Class<?> entityClass);
}
