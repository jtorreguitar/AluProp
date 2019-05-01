package ar.edu.itba.paw.persistence.mappings;

import ar.edu.itba.paw.model.Neighbourhood;
import org.springframework.jdbc.core.RowMapper;

public interface NeighbourhoodDatabaseMapping {
    RowMapper<Neighbourhood> ROW_MAPPER = (rs, rowNum)
            -> new Neighbourhood(rs.getLong("id"), rs.getString("name"));
    RowMapper<Neighbourhood> ROW_MAPPER_FOR_RELATED_ENTITIES = (rs, rowNum)
            -> new Neighbourhood(rs.getLong("neighbourhoodId"), rs.getString("neighbourhoodName"));
}
