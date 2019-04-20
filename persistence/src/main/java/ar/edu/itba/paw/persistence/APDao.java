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

    public APDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    public T get(int id) {
        final List<T> list = jdbcTemplate.query("SELECT * FROM " + getTableName() + " WHERE id = ?", getRowMapper(),
                id);
        return list.isEmpty() ? null : list.get(0);
    }

    public Collection<T> getAll() {
        return jdbcTemplate.query("SELECT * FROM " + getTableName(), getRowMapper());
    }

    protected JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    protected abstract String getTableName();

    protected abstract RowMapper<T> getRowMapper();

    protected abstract SimpleJdbcInsert getJdbcInsert();
}