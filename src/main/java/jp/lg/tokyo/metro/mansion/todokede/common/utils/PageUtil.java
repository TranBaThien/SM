/*
 * @(#) PageUtil.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2020/01/12
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;

public class PageUtil {

    public static final String SQL_PAGING_CONDITION = "LIMIT :limit OFFSET :offset";
    public static final Integer DEFAULT_RECORDS_PER_PAGE = 50;
    public static final Integer FIRST_PAGE = 1;

    public static Pageable getPageable(Integer page, Integer recordsPerpage) {
        if (page == null) {
            page = FIRST_PAGE;
        }
        int pageNumber = page == 0 ? 0 : page - 1;
        return PageRequest.of(pageNumber, recordsPerpage);
    }

    public static Integer getStartIndex(Page<?> pages) {
        return Math.max(1, pages.getNumber() - 2);
    }

    public static Integer getLastIndex(Page<?> pages) {
        return Math.min(getStartIndex(pages) + 5, pages.getTotalPages());
    }

    public static Integer getLimit(Pageable pageable) {
        return pageable.getPageSize();
    }

    public static Integer getOffset(Pageable pageable) {
        return pageable.getPageNumber() * getLimit(pageable);
    }

    public static Integer getTotalPage(Page<?> pages) {
        return pages.getTotalPages();
    }

    public static List<Integer> getListNumber(Integer page, Integer size, Long totalElements) {

        if (page == null) page = FIRST_PAGE;
        if (size == null) size = DEFAULT_RECORDS_PER_PAGE;

        Integer startNo;
        Integer endNo = page * size;
        List<Integer> listNo = new ArrayList<>();

        if (page == 1) {
            startNo = page;
        } else {
            startNo = endNo - size + 1;
        }
        if (endNo > totalElements) {
            endNo = Integer.parseInt(totalElements.toString());
        }
        for (int i = startNo; i <= endNo; i++) {
            listNo.add(i);
        }

        return listNo;
    }

    public static String getQueryGetTotal(StringBuilder queryString) {

        int selectIndex = StringUtils.indexOfIgnoreCase(queryString, "SELECT");
        int fromIndex = StringUtils.indexOfIgnoreCase(queryString, "FROM");

        queryString.replace(selectIndex + 6, fromIndex, " COUNT(1) ");

        int orderIndex = StringUtils.indexOfIgnoreCase(queryString, "ORDER");
        int limitIndex = StringUtils.indexOfIgnoreCase(queryString, "LIMIT");

        if (orderIndex != -1 && limitIndex != -1) {
            queryString.replace(orderIndex, limitIndex, CommonConstants.BLANK);
        }

        return StringUtils.replace(queryString.toString(), SQL_PAGING_CONDITION, CommonConstants.BLANK);
    }

}
