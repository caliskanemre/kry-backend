package com.cry.core.web.rest.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
public class PaginationData {
    private Boolean paginated = Boolean.FALSE;
    private Long totalElements;
    private Integer pageSize;
    private Integer self;
    private Integer first;
    private Integer last;
    private Integer prev;
    private Integer next;

    public PaginationData(Page<?> page) {
        this.paginated = Boolean.TRUE;
        this.totalElements = page.getTotalElements();
        this.pageSize = page.getSize();
        final Pageable pageable = page.getPageable();
        this.self = pageable.getPageNumber() + 1;
        this.first = pageable.first().getPageNumber() + 1;
        this.last = page.getTotalPages() > 0
                ? page.getTotalPages()
                : 1;
        this.prev = page.hasPrevious()
                ? pageable.previousOrFirst().getPageNumber() + 1
                : null;
        this.next = page.hasNext()
                ? pageable.next().getPageNumber() + 1
                : null;
    }

    public void setData(Page<?> page) {
        this.paginated = Boolean.TRUE;
        this.totalElements = page.getTotalElements();
        this.pageSize = page.getSize();
        final Pageable pageable = page.getPageable();
        this.self = pageable.getPageNumber() + 1;
        this.first = pageable.first().getPageNumber() + 1;
        this.last = page.getTotalPages() > 0
                ? page.getTotalPages()
                : 1;
        this.prev = page.hasPrevious()
                ? (pageable.previousOrFirst().getPageNumber() + 1)
                : null;
        this.next = page.hasNext()
                ? (pageable.next().getPageNumber() + 1)
                : null;
    }
}
