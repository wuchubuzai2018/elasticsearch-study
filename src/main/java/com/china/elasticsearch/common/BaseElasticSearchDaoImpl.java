package com.china.elasticsearch.common;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * 基础ElasticSearch操作类
 * @date 2019-08-17
 */
public class BaseElasticSearchDaoImpl implements IBaseElasticSearchDao{

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void createIndex(Class<?> classes){
        elasticsearchTemplate.createIndex(classes);
    }

    @Override
    public void putMapping(Class<?> classes){
        try{
            elasticsearchTemplate.putMapping(classes);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void createIndexAndMapping(Class<?> classes){
        this.createIndex(classes);
        this.putMapping(classes);
    }

    /**
     * 保存数据,可以不需要显示调用withIndexName,withType,withId
     * @param object
     * @return
     */
    @Override
    public String save(Object object){
        IndexQuery indexQuery = new IndexQueryBuilder()
//                .withIndexName("movie")
//                .withType("_doc")
//                .withId("id")
                .withObject(object) //对象或集合
                .build();
        String documentId = elasticsearchTemplate.index(indexQuery);
        return documentId;
    }

    /**
     * 批量保存数据
     * @param objectList
     */
    public void saveBatch(List<?> objectList){
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
            elasticsearchTemplate.bulkIndex(list);
        }
    }

    /**
     * 查询所有数据信息
     * @param entityClass
     * @return
     */
    @Override
    public List<?> queryForList(Class<?> entityClass){
        SearchQuery getAllQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
        List<?> list = elasticsearchTemplate.queryForList(getAllQuery, entityClass);
        return list;
    }

    /**
     * 分页查询所有数据
     * @param curPage
     * @param pageSize
     * @param entityClass
     * @return
     */
    @Override
    public Page<?> queryForPage(int curPage, int pageSize,Class<?> entityClass){
        //of的第一个参数应该从0开始
        curPage = curPage - 1;
        Pageable pageable = PageRequest.of(curPage,pageSize);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                .withPageable(pageable)
                .build();
        Page<?> page = elasticsearchTemplate.queryForPage(searchQuery, entityClass);
        return page;
    }


    /**
     * 根据查询条件进行查询
     * @param queryBuilder
     * @param curPage
     * @param pageSize
     * @param entityClass
     * @return
     */
    @Override
    public Page<?> queryForPage(QueryBuilder queryBuilder,int curPage, int pageSize,Class<?> entityClass){
        //of的第一个参数应该从0开始
        curPage = curPage - 1;
        Pageable pageable = PageRequest.of(curPage,pageSize);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder)
                .withPageable(pageable)
                .build();
        Page<?> page = elasticsearchTemplate.queryForPage(searchQuery, entityClass);
        return page;
    }


//    public String getQueryJson(SearchQuery query){
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        String json = searchSourceBuilder.query(query.getQuery()).toString();
//        System.out.println(json);
//        return json;
//    }



    /**
     * 删除所有数据
     * @param entityClass
     */
    @Override
    public void deleteAll(Class<?> entityClass){
        DeleteQuery query = new DeleteQuery();
        query.setQuery(matchAllQuery());
        elasticsearchTemplate.delete(query,entityClass);
    }

    @Override
    public void deleteById(Class<?> entityClass,String id){
        elasticsearchTemplate.delete(entityClass,id);
    }



}
