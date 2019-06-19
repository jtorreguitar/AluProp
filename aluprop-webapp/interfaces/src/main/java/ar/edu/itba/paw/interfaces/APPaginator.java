package ar.edu.itba.paw.interfaces;

import javax.persistence.TypedQuery;

public class APPaginator implements Paginator {

    public <T> TypedQuery<T> makePagedQuery(TypedQuery<T> query, PageRequest pageRequest) {
        query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        return query;
    }
}
