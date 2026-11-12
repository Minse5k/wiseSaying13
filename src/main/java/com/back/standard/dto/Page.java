package com.back.standard.dto;

import java.util.List;

public class Page<T> {
    private final List<T> content;
    private final int totalCount;
    private final int page;
    private final int pageSize;

    public Page(List<T> content, int totalCount, int page, int pageSize) {
        this.content = content;
        this.totalCount = totalCount;
        this.page = page;
        this.pageSize = pageSize;
    }

    public List<T> getContent() {
        return content;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return Math.max(1, (int) Math.ceil((double) totalCount / pageSize));
    }
}
