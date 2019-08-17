package com.china.elasticsearch.common;

import com.china.elasticsearch.bean.FirstBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

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
    public void saveBatch(List<Object> objectList){
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
    public List<?> queryForList(Class<?> entityClass){
        SearchQuery getAllQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
        List<?> list = elasticsearchTemplate.queryForList(getAllQuery, entityClass);
        return list;
    }



}
