package ar.edu.itba.paw.interfaces;

import javax.persistence.TypedQuery;

public interface Paginator {
    <T> TypedQuery<T> makePagedQuery(TypedQuery<T> query, PageRequest pageRequest);
}
