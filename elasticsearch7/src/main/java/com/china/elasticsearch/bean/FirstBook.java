package com.china.elasticsearch.bean;

import com.china.elasticsearch.constant.FirstConstant;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Document(indexName="firstbook",type="_doc")：
 * indexName：索引的名称（必填项）
 * type：索引的类型
 * Id：主键的唯一标识
 * Field(index=true,analyzer="ik_smart",store=true,searchAnalyzer="ik_smart",type = FieldType.text)
 * index：是否设置分词
 * analyzer：存储时使用的分词器
 * searchAnalyze：搜索时使用的分词器
 * store：是否存储
 * type: 数据类型
 * @date 2019-08-10
 */
@Document(indexName = FirstConstant.FIRSTBOOK_INDEXNAME)
public class FirstBook {

    @Id
    private String id;

    private String title;

    private String author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
