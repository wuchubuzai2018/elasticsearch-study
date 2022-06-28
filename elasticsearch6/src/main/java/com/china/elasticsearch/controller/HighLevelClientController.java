package com.china.elasticsearch.controller;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用高级别客户端的控制器
 * 2019-08-11
 */
@Controller
@RequestMapping("highlevel")
public class HighLevelClientController {

    @Autowired
    public RestHighLevelClient client;

//    @PostConstruct
//    public void init(){
//        client = new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost("127.0.0.1", 9200, "http")
//                ));
//        System.out.println("init success--------------------");
//    }

//    @PreDestroy
//    public void close(){
//        try{
//            if(client != null){
//                client.close();
//                System.out.println("destory--------------------");
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }

    /**
     * 保存操作
     */
    public void save(){
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("userId", "002");
        jsonMap.put("user", "的实打实的方式");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        try {
            IndexRequest indexRequest = new IndexRequest("usershigh","_doc","002").source(jsonMap);
            IndexResponse indexResponse = client.index(indexRequest);
            if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                System.out.println("created");
            } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                System.out.println("updated");
            }
        } catch(Exception e) {
            e.printStackTrace();
            if(e instanceof  ElasticsearchException){
                ElasticsearchException ee = (ElasticsearchException)e;
                if (ee.status() == RestStatus.CONFLICT) {
                    System.out.println("conflict");
                }
            }

        }
    }

    @RequestMapping("/save")
    @ResponseBody
    public Map<String,Object> test() throws Exception {     Map<String,Object> rMap = new HashMap<String,Object>();

       this.save();
        rMap.put("success",true);
        return rMap;
    }



}
