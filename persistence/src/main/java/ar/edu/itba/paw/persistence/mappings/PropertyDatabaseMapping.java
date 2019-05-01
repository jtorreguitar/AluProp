package ar.edu.itba.paw.persistence.mappings;

import ar.edu.itba.paw.model.Neighbourhood;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.enums.PropertyType;
import ar.edu.itba.paw.persistence.exceptions.IllegalResultSetException;
import org.springframework.jdbc.core.RowMapper;

public interface PropertyDatabaseMapping {

    RowMapper<Property> ROW_MAPPER = (rs, rowNum)
            -> new Property.Builder()
            .withId(rs.getLong("id"))
            .withCapacity(rs.getInt("capacity"))
            .withCaption(rs.getString("caption"))
            .withDescription(rs.getString("description"))
            .withNeighbourhoodId(rs.getLong("neighbourhoodId"))
            .withPrice(rs.getFloat("price"))
            .withPrivacyLevel(rs.getBoolean("privacyLevel"))
            .withPropertyType(PropertyType.valueOf(rs.getString("propertyType")))
            .build();

    RowMapper<Property> ROW_MAPPER_FOR_RELATED_ENTITIES = (rs, rowNum)
            -> new Property.Builder()
            .withId(rs.getLong("propertyId"))
            .withCapacity(rs.getInt("propertyCapacity"))
            .withCaption(rs.getString("propertyCaption"))
            .withDescription(rs.getString("propertyDescription"))
            .withNeighbourhoodId(rs.getLong("propertyNeighbourhoodId"))
            .withPrice(rs.getFloat("propertyPrice"))
            .withPrivacyLevel(rs.getBoolean("propertyPrivacyLevel"))
            .withPropertyType(PropertyType.valueOf(rs.getString("propertyType")))
            .build();

    RowMapper<Property> ROW_MAPPER_WITH_RELATED_ENTITIES_FOR_SINGLE_PROPERTY_QUERIES = (rs, rowNum)
            -> {
                Property currentProperty = null;
                long id = 0;
                while(rs.next()) {
                    if(id == 0){
                        currentProperty = new Property.Builder()
                                .fromProperty(ROW_MAPPER.mapRow(rs, rowNum))
                                .withNeighbourhood(NeighbourhoodDatabaseMapping
                                        .ROW_MAPPER_FOR_RELATED_ENTITIES.mapRow(rs, rowNum))
                                .withUniversity(UniversityDatabaseMapping
                                        .ROW_MAPPER_FOR_RELATED_ENTITIES.mapRow(rs, rowNum))
                                .build();
                        id = rs.getLong("id");
                    }
                    else if(rs.getLong("id") != id)
                        throw new IllegalResultSetException("The result set must contain only one user");
                    currentProperty.getInterestedProperties()
                            .add(PropertyDatabaseMapping.ROW_MAPPER_FOR_RELATED_ENTITIES.mapRow(rs, rowNum));
                }
                return currentProperty;
            };
}
