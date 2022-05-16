/*
 * @(#) PagingVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2020/01/12
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import org.springframework.data.domain.Page;

public class PagingVo<T> {

    public PagingVo() {
        super();
    }

    /**
     * @param pages
     * @param start
     * @param last
     * @param totalPage
     * @param listNo
     */
    public PagingVo(Page<T> pages, Integer start, Integer last, Integer totalPage) {
        super();
        this.pages = pages;
        this.start = start;
        this.last = last;
        this.totalPage = totalPage;
    }

    private Page<T> pages;

    private Integer start;

    private Integer last;

    private Integer totalPage;

    public Page<T> getPages() {
        return pages;
    }

    public void setPages(Page<T> pages) {
        this.pages = pages;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLast() {
        return last;
    }

    public void setLast(Integer last) {
        this.last = last;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

}
