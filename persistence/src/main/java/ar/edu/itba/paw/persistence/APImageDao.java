package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Repository
public class APImageDao implements ImageDao {

    private RowMapper<Image> ROW_MAPPER = (rs, rowNum)
        -> new Image(rs.getLong("id"), rs.getLong("propertyId"), rs.getBinaryStream("image"));
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public APImageDao(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Image get(long id) {
        List<Image> list = jdbcTemplate.query("SELECT * FROM images WHERE id = ?", ROW_MAPPER, id);
        if(!list.isEmpty())
            return list.get(0);
        return null;
    }

    @Override
    public Collection<Image> getByProperty(long propertyId) {
        return jdbcTemplate.query("SELECT * FROM images WHERE propertyId = ?", ROW_MAPPER, propertyId);
    }

    @Override
    public void addProperty(long id, long propertyId) {
        jdbcTemplate.update("UPDATE images SET propertyId = ? WHERE id = ?", propertyId, id);
    }
}
