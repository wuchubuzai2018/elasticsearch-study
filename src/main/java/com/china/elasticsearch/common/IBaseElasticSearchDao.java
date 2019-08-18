package com.china.elasticsearch.common;

import org.springframework.data.domain.Page;

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
     * 删除索引中的数据
     * @param entityClass
     */
    public void deleteAll(Class<?> entityClass);
}
