package com.china.elasticsearch;

import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
 * 单元测试类
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes={StartMainApplication.class})// 指定启动类
public class ElasticSearchTest {


    public void test1(){
        System.out.println("hello world");
    }


    public static void main(String[] args) {
        //1.查询全部的语法
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        System.out.println(queryBuilder.toString());
        //2.查询指定字段的语法
        QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("actors","value").operator(Operator.AND);
        System.out.println(queryBuilder2.toString());
        //3.term指定字段的语法
        QueryBuilder queryBuilder3 = QueryBuilders.termQuery("actors",24.44);
        System.out.println(queryBuilder3.toString());
        //4.多字段的查询
        QueryBuilder queryBuilder4 = QueryBuilders.multiMatchQuery("Hello World", "name","age");
        System.out.println(queryBuilder4.toString());
        //5.match phrase查询
        QueryBuilder queryBuilder5 = QueryBuilders.matchPhraseQuery("title", "我是标题");
        System.out.println(queryBuilder5.toString());
        //6.match phrase prefix查询
        QueryBuilder queryBuilder6 = QueryBuilders.matchPhrasePrefixQuery("title", "我是标题");
        System.out.println(queryBuilder6.toString());
        //7.ids查询
        QueryBuilder queryBuilder7 = QueryBuilders.idsQuery().addIds("11","12");
        System.out.println(queryBuilder7.toString());
        //8.range query
        QueryBuilder queryBuilder8 = QueryBuilders.rangeQuery("age").gt(3).lt(10);
        System.out.println(queryBuilder8.toString());
        //9.bool query
        QueryBuilder queryBuilder9 = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("user", "kimchy"))
                .mustNot(QueryBuilders.termQuery("message", "nihao"))
                .should(QueryBuilders.termQuery("gender", "male"));
        System.out.println(queryBuilder9.toString());
        //10.wildcard query
        QueryBuilder queryBuilder10 = QueryBuilders.wildcardQuery("name",
                "*jack*");
        System.out.println(queryBuilder10.toString());
        //11.termsQuery
        QueryBuilder queryBuilder11 = QueryBuilders.termsQuery("user", "jnkins","long");
        System.out.println(queryBuilder11.toString());
    }


}
