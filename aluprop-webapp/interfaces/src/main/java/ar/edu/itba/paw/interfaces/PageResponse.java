package ar.edu.itba.paw.interfaces;

import java.util.Collection;

public class PageResponse<T> {
    private final int pageNumber;
    private final int pageSize;
    private final Long totalItems;
    private final Collection<T> responseData;

    public PageResponse(PageRequest pageRequest, Long totalItems, Collection<T> responseData) {
        this.pageNumber = pageRequest.getPageNumber();
        this.pageSize = pageRequest.getPageSize();
        this.totalItems = totalItems;
        this.responseData = responseData;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public Collection<T> getResponseData() {
        return responseData;
    }

    public long getTotalPages() {
        return totalItems/pageSize;
    }
}
