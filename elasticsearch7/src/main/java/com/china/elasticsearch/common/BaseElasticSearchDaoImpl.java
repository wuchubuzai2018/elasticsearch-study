package com.china.elasticsearch.common;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * 基础ElasticSearch操作类
 * @date 2019-08-17
 */
public class BaseElasticSearchDaoImpl<T> implements IBaseElasticSearchDao<T>{

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Override
    public void createIndex(Class<T> classes){

    }

    @Override
    public void putMapping(Class<T> classes){

    }

    @Override
    public void createIndexAndMapping(Class<T> classes){
        this.createIndex(classes);
        this.putMapping(classes);
    }

    /**
     * 保存数据,可以不需要显示调用withIndexName,withType,withId
     * @param object
     * @return
     */
    @Override
    public void save(T object){
        elasticsearchTemplate.save(object);
    }

    /**
     * 批量保存数据
     * @param objectList
     */
    @Override
    public void saveBatch(List<T> objectList,Class<T> classes){
        List<IndexQuery> list = new ArrayList<IndexQuery>();
        if(objectList != null && objectList.size() > 0){
            int len = objectList.size();
            for(int i = 0;i < len;i++){
                Object tempObject = objectList.get(i);
                IndexQuery indexQuery = new IndexQueryBuilder()
                        .withObject(tempObject)
                        .build();
                list.add(indexQuery);
                //这里待加入分批次保存
            }
            elasticsearchTemplate.bulkIndex(list,classes);
        }
    }

    /**
     * 查询所有数据信息
     * @param entityClass
     * @return
     */
    @Override
    public List<?> queryForList(Class<T> entityClass){
//        Query getAllQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
//
//        elasticsearchTemplate.
//
//        List<?> list = elasticsearchTemplate.(getAllQuery, entityClass);
////        return list;
        return null;
    }

    @Override
    public long queryCount(Query query,Class<T> entityClass,String indexName) {
        return elasticsearchTemplate.count(query,entityClass);
    }

    /**
     * 分页查询所有数据
     * @param curPage
     * @param pageSize
     * @param entityClass
     * @return
     */
    @Override
    public List<T> queryForPage(int curPage, int pageSize,Class<T> entityClass,String indexName){
//        //of的第一个参数应该从0开始
        curPage = curPage - 1;
        Pageable pageable = PageRequest.of(curPage,pageSize);

        IndexCoordinates index = IndexCoordinates.of(indexName);

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withPageable(pageable)
                .build();

        SearchScrollHits<T> scroll = elasticsearchTemplate.searchScrollStart(1000, searchQuery,entityClass,index);

        String scrollId = scroll.getScrollId();
        List<T> sampleEntities = new ArrayList<T>();
        while (scroll.hasSearchHits()) {
            List<SearchHit<T>> searchHits = scroll.getSearchHits();
            searchHits.forEach(personSearchHit -> {
                T content = personSearchHit.getContent();
                sampleEntities.add(content);
            });
            scrollId = scroll.getScrollId();
            scroll = elasticsearchTemplate.searchScrollContinue(scrollId, 1000, entityClass,index);
        }
        elasticsearchTemplate.searchScrollClear(Arrays.asList(new String[]{scrollId}));
        return sampleEntities;
    }


//    /**
//     * 根据查询条件进行查询
//     * @param queryBuilder
//     * @param curPage
//     * @param pageSize
//     * @param entityClass
//     * @return
//     */
//    @Override
//    public Page<T> queryForPage(QueryBuilder queryBuilder,int curPage, int pageSize,Class<T> entityClass){
////        //of的第一个参数应该从0开始
//        curPage = curPage - 1;
//        Pageable pageable = PageRequest.of(curPage,pageSize);
//        Query searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder)
//                .withPageable(pageable)
//                .build();
//        elasticsearchTemplate.
////        Page<?> page = elasticsearchTemplate..queryForPage(searchQuery, entityClass);
//        return page;
//    }



    /**
     * 删除所有数据
     * @param entityClass
     */
    @Override
    public void deleteAll(Class<T> entityClass){
        Query query = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
        elasticsearchTemplate.delete(query,entityClass);
    }

    @Override
    public void deleteById(Class<T> entityClass,String id){
        elasticsearchTemplate.delete(id,entityClass);
    }



}
