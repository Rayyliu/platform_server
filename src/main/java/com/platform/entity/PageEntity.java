package com.platform.entity;

import com.platform.entity.dto.ProjectDTO;
import lombok.Data;

@Data
public class PageEntity<T> {
    private int page;
    private int pageSize;
    private T entity;
    private int total;

    public PageEntity(int page, int pageSize, T entity, int total) {
        this.page = page;
        this.pageSize = pageSize;
        this.entity = entity;
        this.total = total;
    }
}
