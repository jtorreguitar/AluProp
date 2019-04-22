package ar.edu.itba.paw.persistence;

import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import ar.edu.itba.paw.interfaces.Dao;

public abstract class APDao<T> implements Dao<T> {

    private JdbcTemplate jdbcTemplate;
    private final String GET_ALL_QUERY;
    private final String GET_QUERY;

    public APDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        GET_ALL_QUERY = "SELECT * FROM " + getTableName();
        GET_QUERY = "SELECT * FROM " + getTableName() + " WHERE id = ?";
    }

    public T get(long id) {
        final List<T> list = jdbcTemplate
                                .query(GET_QUERY, getRowMapper(), id);
        return list.isEmpty() ? null : list.get(0);
    }

    public Collection<T> getAll() {
        return jdbcTemplate.query(GET_ALL_QUERY, getRowMapper());
    }

    protected JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    protected abstract String getTableName();

    protected abstract RowMapper<T> getRowMapper();
}