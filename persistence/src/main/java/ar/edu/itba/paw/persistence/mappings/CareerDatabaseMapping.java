package ar.edu.itba.paw.persistence.mappings;

import ar.edu.itba.paw.model.Career;
import org.springframework.jdbc.core.RowMapper;

public interface CareerDatabaseMapping {
    RowMapper<Career> ROW_MAPPER = (rs, rowNum)
            -> new Career(rs.getLong("id"), rs.getString("name"));
    RowMapper<Career> ROW_MAPPER_FOR_RELATED_ENTITIES = (rs, rowNum)
            -> new Career(rs.getLong("careerId"), rs.getString("careerName"));
}
