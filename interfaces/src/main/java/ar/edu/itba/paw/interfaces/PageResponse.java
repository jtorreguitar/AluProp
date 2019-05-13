package ar.edu.itba.paw.interfaces;

import java.util.Collection;

public class PageResponse<T> {
    private final int pageNumber;
    private final int pageSize;
    private final int totalPages;
    private final Collection<T> responseData;

    public PageResponse(int pageNumber, int pageSize, int totalPages, Collection<T> responseData) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.responseData = responseData;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public Collection<T> getResponseData() {
        return responseData;
    }
}
