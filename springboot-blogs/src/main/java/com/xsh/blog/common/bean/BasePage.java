package com.xsh.blog.common.bean;

import java.util.Collections;
import java.util.List;

/**
 * @program: StudentHeart
 * @description: 分页
 *
 **/


public class BasePage<T> {
    protected List<T> records;
    protected long total;
    protected long size;
    protected long current;
    protected long pages;

    public BasePage(List<T> records, long total, long size, long current, long pages) {
        this.records = records;
        this.total = total;
        this.size = size;
        this.current = current;
        this.pages = pages;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public BasePage() {
        this.records = Collections.emptyList();
        this.total = 0L;
        this.size = 10L;
        this.current = 1L;
    }


    public BasePage(List<T> records) {
        this();
        this.records = records;
    }

    public BasePage(List<T> records, long current, long total) {
        this();
        this.records = records;
        this.current = current;
        this.total = total;
    }

    public BasePage(List<T> records, long current, long size, long total) {
        this();
        this.records = records;
        this.current = current;
        this.total = total;
        this.size = size;
    }

    public BasePage(List<T> records, long total) {
        this();
        this.records = records;
        this.total = total;
    }

    public void initPages(long total) {
        this.total = total;
        this.initPages();
    }

    public void initPages(long total, long size) {
        this.total = total;
        this.size = size;
        this.initPages();
    }

    public void initPages() {
        long pages = this.total / this.size;
        if (this.total % this.size == 0) {
            this.pages = pages;
        } else {
            this.pages = pages + 1;
        }
    }


}
