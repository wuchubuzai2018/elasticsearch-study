package com.china.elasticsearch.common.jest;

import com.china.elasticsearch.bean.PageEntity;
import com.google.gson.Gson;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.*;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.mapping.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于Jest操作的基础类
 * @date 2019-08-29
 */
public class BaseJestElasticSearchDaoImpl implements IBaseJestElasticSearchDao{

    @Autowired
    private JestClient jestClient;

    /**
     * 创建索引
     * @param indexName
     * @return
     * @throws Exception
     */
    @Override
    public boolean createIndex(String indexName)throws Exception{
        JestResult jestResult = jestClient.execute(new CreateIndex.Builder(indexName).build());
        return jestResult.isSucceeded();
    }

    /**
     * 删除索引
     * @param indexName
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteIndex(String indexName)throws Exception{
        JestResult jestResult = jestClient.execute(new DeleteIndex.Builder(indexName).build());
        return jestResult.isSucceeded();
    }

    /**
     * 设置索引的mapping
     * @param indexName
     * @param type
     * @param mappingString
     * @throws Exception
     */
    @Override
    public void createIndexMapping(String indexName, String type, String mappingString)throws Exception{
        //mappingString为拼接好的json格式的mapping串
        PutMapping.Builder builder = new PutMapping.Builder(indexName, type, mappingString);
        JestResult jestResult = jestClient.execute(builder.build());
        System.out.println("createIndexMapping result:{}" + jestResult.isSucceeded());
        if (!jestResult.isSucceeded()) {
            System.err.println("settingIndexMapping error:{}" + jestResult.getErrorMessage());
        }
    }


    /**
     * 单条保存
     * @param object
     * @param indexName
     * @param typeName
     * @throws Exception
     */
    public void save(Object object,String indexName,String typeName)throws Exception{
        Index index = new Index.Builder(object).index(indexName).type(typeName).build();
        JestResult jestResult = jestClient.execute(index);
        boolean isSuccess = jestResult.isSucceeded();
    }


    /**
     * 批量保存接口
     * @param objectList
     * @param indexName
     * @param typeName
     * @throws Exception
     */
    @Override
    public void saveBatch(List<?> objectList,String indexName,String typeName) throws Exception {
        Bulk.Builder bulk = new Bulk.Builder();
        for(Object entity : objectList) {
            Index index = new Index.Builder(entity).index(indexName).type(typeName).build();
            bulk.addAction(index);
        }
        JestResult jestResult = jestClient.execute(bulk.build());
        boolean isSuccess = jestResult.isSucceeded();
        System.out.println(isSuccess);
    }

    /**
     * 分页查询数据
     * @return
     */
    public PageEntity queryForPage(int curPage, int pageSize,
                                   String indexName, String typeName, Class<?> entityClass)throws Exception{
        Map<String,Object> queryMap = new HashMap<String,Object>();
        queryMap.put("from",(curPage - 1) * pageSize);
        queryMap.put("size",pageSize);
        String query = new Gson().toJson(queryMap);
        Search search = new Search.Builder(query)
                .addIndex(indexName).addType(typeName).build();
        SearchResult result = jestClient.execute(search);
        List<?> list = result.getSourceAsObjectList(entityClass,false);
        PageEntity page = new PageEntity();
        page.setTotalCount(result.getTotal());
        page.setDataList(list);
        return page;
    }

    /**
     * 输入条件进行查询
     * @param searchBody
     * @param indexName
     * @param typeName
     * @return
     * @throws Exception
     */
    @Override
    public String queryForString(String searchBody,String indexName,String typeName)throws Exception{
        Search search = new Search.Builder(searchBody)
                .addIndex(indexName).addType(typeName).build();
        SearchResult result = jestClient.execute(search);
        return result.getSourceAsString();
    }

    /**
     * 删除全部数据
     * @param indexName
     * @param typeName
     * @return
     * @throws Exception
     */
    public boolean deleteAll(String indexName,String typeName)throws Exception{
        String query = "{\"query\":{\"match_all\":{}}}";
        DeleteByQuery deleteByQuery = new DeleteByQuery.Builder(query).addIndex(indexName).addType(typeName).refresh(true).build();
        JestResult jestResult = jestClient.execute(deleteByQuery);
        System.out.println(jestResult.getErrorMessage());
        boolean isSuccess = jestResult.isSucceeded();
        return isSuccess;
    }


    /**
     * 单条删除
     * @param indexName
     * @param typeName
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteById(String indexName,String typeName,String id)throws Exception{
        //注意refresh方法
        Delete index = new Delete.Builder(id).index(indexName).type(typeName).refresh(true).build();
        JestResult jestResult = jestClient.execute(index);
        System.out.println(jestResult.getErrorMessage());
        boolean isSuccess = jestResult.isSucceeded();
        return isSuccess;
    }





}
