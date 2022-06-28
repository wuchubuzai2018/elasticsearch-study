package com.china.elasticsearch.util;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * ElasticSearch工具类
 */
public class ESUtil {

    protected Document getDocument(Class<?> classes) throws Exception {
        Document annotation = classes.getAnnotation(Document.class);
        if (annotation == null) {
            throw new RuntimeException("Can't find annotion @Document");
        }
        return annotation;
    }
}
