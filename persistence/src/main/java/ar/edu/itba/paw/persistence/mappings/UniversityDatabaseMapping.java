package ar.edu.itba.paw.persistence.mappings;

import ar.edu.itba.paw.model.University;
import org.springframework.jdbc.core.RowMapper;

public interface UniversityDatabaseMapping {
    RowMapper<University> ROW_MAPPER = (rs, rowNum)
            -> new University(rs.getLong("id"), rs.getString("name"));
    RowMapper<University> ROW_MAPPER_FOR_RELATED_ENTITIES = (rs, rowNum)
            -> new University(rs.getLong("universityId"), rs.getString("universityName"));
}
