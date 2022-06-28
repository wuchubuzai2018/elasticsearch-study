package com.china.elasticsearch.bean;

import java.util.List;

/**
 * 存储分页信息的类
 * @date 2019-08-18
 */
public class PageEntity {

    /**每页多少条*/
    private int pageSize;

    /**当前页面*/
    private int page;

    /**总页数*/
    private long totalPage;

    private long totalCount;

    private List<?> dataList;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }
}
