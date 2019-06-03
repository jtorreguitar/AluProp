package ar.edu.itba.paw.interfaces;

public class PageRequest {
    private int pageNumber;
    private int pageSize;

    public PageRequest(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }
}
