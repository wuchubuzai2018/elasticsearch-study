package com.china.elasticsearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 单元测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={StartMainApplication.class})// 指定启动类
public class ElasticSearchTest {

    @Test
    public void test1(){
        System.out.println("hello world");
    }



}
