package com.china.elasticsearch.common.jest;

import com.china.elasticsearch.bean.PageEntity;
import com.google.gson.Gson;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
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
}
