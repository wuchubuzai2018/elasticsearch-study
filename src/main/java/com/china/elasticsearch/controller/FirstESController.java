package com.china.elasticsearch.controller;

import com.china.elasticsearch.bean.FirstBook;
import com.china.elasticsearch.constant.FirstConstant;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * 使用ElasticSearchTemplate操作的控制器
 * @date 2019-08-10
 */
@Controller
@RequestMapping("es")
public class FirstESController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 增加保存操作
     */
    public void save(){
        //定义书籍1
//        FirstBook book = new FirstBook();
//        book.setId("1");
//        book.setAuthor("孙昊 王洋 赵帅 杜秀芳 曾凡太");
//        book.setTitle("物联网协议与物联网操作系统 ");

        FirstBook book2 = new FirstBook();
        book2.setId("4");
        book2.setAuthor("冯雷 姚延栋 高小明 杨瑜");
        book2.setTitle("Greenplum：从大数据战略到实现");

        IndexQuery indexQuery = new IndexQueryBuilder()
                .withIndexName(FirstConstant.FIRSTBOOK_INDEXNAME)
                .withType(FirstConstant.FIRSTBOOK_TYPE)
                .withId(book2.getId()+"")
                .withObject(book2) //对象或集合
                .build();
        String documentId = elasticsearchTemplate.index(indexQuery);
        System.out.println("insert success: " + documentId);
    }

    /**
     * 批量保存
     */
    public void saveBatch(){

        List<IndexQuery> list = new ArrayList<IndexQuery>();

        for(int i = 10;i < 15;i++){
            FirstBook book = new FirstBook();
            book.setId(i + "");
            book.setAuthor("冯雷 姚延栋 高小明 杨瑜 " + i);
            book.setTitle("Greenplum：从大数据战略到实现" + i);

            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withIndexName(FirstConstant.FIRSTBOOK_INDEXNAME)
                    .withType(FirstConstant.FIRSTBOOK_TYPE)
                    .withId(book.getId()+"")
                    .withObject(book) //对象或集合
                    .build();
            list.add(indexQuery);
        }

       elasticsearchTemplate.bulkIndex(list);
    }


    /**
     * 删除
     */
    public void delete(){
        elasticsearchTemplate.delete(FirstBook.class,"4");
    }

    /**
     * 更新操作
     */
    public void update() throws Exception{

        Map<String,Object> sMap = new HashMap<String,Object>();
        sMap.put("id","1");
        sMap.put("title","物联网协议与物联网操作系统-更新20192");


        UpdateRequest up = new UpdateRequest(FirstConstant.FIRSTBOOK_INDEXNAME,
                FirstConstant.FIRSTBOOK_TYPE,"1");
        up.doc(sMap);//暂时不清楚为什么只能写Map类型好使

        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withIndexName(FirstConstant.FIRSTBOOK_INDEXNAME)
                .withType(FirstConstant.FIRSTBOOK_TYPE)
                .withId(1+"")
                .withUpdateRequest(up)
                .withClass(FirstBook.class)
                .build();
        elasticsearchTemplate.update(updateQuery);
    }

    /**
     * 查询所有操作
     */
    public List<FirstBook>  queryForList(){
        SearchQuery getAllQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery()).build();
        List<FirstBook> bookList = elasticsearchTemplate.queryForList(getAllQuery, FirstBook.class);
        System.out.println(bookList);
        return bookList;
    }


    /**
     * 根据主键查询
     * @param bookId
     * @return
     */
    public FirstBook queryById(String bookId){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.matchQuery("id", bookId)).build();
        List<FirstBook> users = elasticsearchTemplate.queryForList(searchQuery, FirstBook.class);
        if(!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    /**
     * 分页查询
     * @return
     */
    public Page<FirstBook> queryForPage(int curPage,int pageSize){
        Pageable pageable = PageRequest.of(curPage,pageSize);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withPageable(pageable)
                .build();

        Page<FirstBook> page = elasticsearchTemplate.queryForPage(searchQuery, FirstBook.class);
        return page;
    }


    /**
     * 单条保存
     * @return
     */
    @RequestMapping("/saveBook")
    @ResponseBody
    public Map<String,Object> saveBook() {
        Map<String,Object> rMap = new HashMap<String,Object>();
        //保存操作
        this.save();
        rMap.put("success",true);
        return rMap;
    }

    /**
     * 批量保存
     * @return
     */
    @RequestMapping("/saveBatchBook")
    @ResponseBody
    public Map<String,Object> saveBatchBook() {
        Map<String,Object> rMap = new HashMap<String,Object>();
        //保存操作
        this.saveBatch();
        rMap.put("success",true);
        return rMap;
    }




    /**
     * 单条更新
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateBook")
    @ResponseBody
    public Map<String,Object> updateBook() throws Exception{
        Map<String,Object> rMap = new HashMap<String,Object>();
        //更新操作
        this.update();
        rMap.put("success",true);
        return rMap;
    }

    /**
     * 单条删除
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteBook")
    @ResponseBody
    public Map<String,Object> deleteBook() throws Exception{
        Map<String,Object> rMap = new HashMap<String,Object>();
        //更新操作
        this.delete();
        rMap.put("success",true);
        return rMap;
    }

    /**
     * 查询所有
     * @return
     * @throws Exception
     */
    @RequestMapping("/queryBookList")
    @ResponseBody
    public Map<String,Object> queryBookList() throws Exception{
        Map<String,Object> rMap = new HashMap<String,Object>();
        //更新操作
        rMap.put("list",this.queryForList());
        rMap.put("success",true);
        return rMap;
    }

    /**
     * 查询单条
     * @return
     * @throws Exception
     */
    @RequestMapping("/queryBookById")
    @ResponseBody
    public Map<String,Object> queryBookById() throws Exception{
        Map<String,Object> rMap = new HashMap<String,Object>();
        //更新操作
        rMap.put("data",this.queryById("2"));
        rMap.put("success",true);
        return rMap;
    }

    /**
     * 分页查询
     * @return
     * @throws Exception
     */
    @RequestMapping("/queryBookForPage")
    @ResponseBody
    public Map<String,Object> queryBookForPage() throws Exception{
        Map<String,Object> rMap = new HashMap<String,Object>();
        //更新操作

        Page<FirstBook> page = this.queryForPage(1,2);

        rMap.put("页数",page.getTotalPages());
        rMap.put("行数",page.getTotalElements());
        rMap.put("大小",page.getSize());
        rMap.put("当前第几页",page.getNumber());
        rMap.put("当前页的数量",page.getNumberOfElements());
        rMap.put("list",page.getContent());
        rMap.put("success",true);
        return rMap;
    }




}
