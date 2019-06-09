package ar.edu.itba.paw.persistence.utilities;

import ar.edu.itba.paw.interfaces.PageRequest;

import javax.persistence.TypedQuery;

public class QueryUtility {

    public static <T> TypedQuery<T> makePagedQuery(TypedQuery<T> query, PageRequest pageRequest) {
        query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        return query;
    }
}
