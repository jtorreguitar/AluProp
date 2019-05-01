package ar.edu.itba.paw.persistence.mappings;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Gender;
import ar.edu.itba.paw.model.enums.Role;
import ar.edu.itba.paw.persistence.exceptions.IllegalResultSetException;
import org.springframework.jdbc.core.RowMapper;

public interface UserDatabaseMapping {
    String USER_GET_BY_ID_WITH_RELATED_ENTITIES_QUERY =
            "select u.\"id\" as id,\n" +
                    "\tu.\"email\" as email,\n" +
                    "\tu.\"username\" as username,\n" +
                    "\tu.\"name\" as name,\n" +
                    "\tu.\"lastname\" as lastName,\n" +
                    "\tu.\"birthdate\" as birthDate,\n" +
                    "\tu.\"gender\" as gender,\n" +
                    "\tu.\"passwordhash\" as passwordHash,\n" +
                    "\tu.\"universityid\" as universityId,\n" +
                    "\tu.\"careerid\" as careerId,\n" +
                    "\tu.\"bio\" as bio,\n" +
                    "\tu.\"contactnumber\" as contactNumber,\n" +
                    "\tu.\"role\" as role,\n" +
                    "\tun.\"name\" as universityName,\n" +
                    "\tun.\"id\" as universityId,\n" +
                    "\tc.\"name\" as careerName,\n" +
                    "\tc.\"id\" as careerId,\n" +
                    "\tp.\"id\" as propertyId,\n" +
                    "\tp.\"caption\" as propertyCaption,\n" +
                    "\tp.\"description\" as propertyDescription,\n" +
                    "\tp.\"image\" as propertyImage,\n" +
                    "\tp.\"propertytype\" as propertyType,\n" +
                    "\tp.\"neighbourhoodid\" as propertyNeighbourhoodId,\n" +
                    "\tp.\"privacylevel\" as propertyPrivacyLevel,\n" +
                    "\tp.\"capacity\" as propertyCapacity,\n" +
                    "\tp.\"price\" as propertyPrice\n" +
                    "from\n" +
                    "\t\"users\" u \n" +
                    "\tleft outer join \"careers\" c on u.\"careerid\" = c.\"id\"\n" +
                    "\tleft outer join \"universities\" un on u.\"universityid\" = un.\"id\"\n" +
                    "\tleft outer join \"interests\" i on u.\"id\" = i.\"userid\"\n" +
                    "\tleft outer join \"properties\" p on i.\"propertyid\" = p.\"id\"\n" +
                    "where u.\"id\" = ?";

    RowMapper<User> ROW_MAPPER = (rs, rowNum)
            -> new User.Builder()
                .withId(rs.getLong("id"))
                .withBio(rs.getString("bio"))
                .withBirthDate(rs.getDate("birthDate"))
                .withCareerId(rs.getLong("careerId"))
                .withContactNumber(rs.getString("contactNumber"))
                .withEmail(rs.getString("email"))
                .withGender(Gender.valueOf(rs.getString("gender")))
                .withLastName(rs.getString("lastName"))
                .withName(rs.getString("name"))
                .withPasswordHash(rs.getString("passwordHash"))
                .withUniversityId(rs.getLong("universityId"))
                .withRole(Role.valueOf(rs.getString("role")))
                .build();

    RowMapper<User> ROW_MAPPER_FOR_RELATED_ENTITIES = (rs, rowNum)
            -> new User.Builder()
            .withId(rs.getLong("userId"))
            .withBio(rs.getString("userBio"))
            .withBirthDate(rs.getDate("userBirthDate"))
            .withCareerId(rs.getLong("userCareerId"))
            .withContactNumber(rs.getString("userContactNumber"))
            .withEmail(rs.getString("userEmail"))
            .withGender(Gender.valueOf(rs.getString("userGender")))
            .withLastName(rs.getString("userLastName"))
            .withName(rs.getString("userName"))
            .withPasswordHash(rs.getString("userPasswordHash"))
            .withUniversityId(rs.getLong("userUniversityId"))
            .withRole(Role.valueOf(rs.getString("userRole")))
            .build();

    RowMapper<User> ROW_MAPPER_WITH_RELATED_ENTITIES_FOR_SINGLE_USER_QUERIES = (rs, rowNum)
            ->  {
                    User currentUser = null;
                    long id = 0;
                    while(rs.next()) {
                        if(id == 0){
                            currentUser = new User.Builder()
                                                .fromUser(ROW_MAPPER.mapRow(rs, rowNum))
                                                .withCareer(CareerDatabaseMapping
                                                        .ROW_MAPPER_FOR_RELATED_ENTITIES.mapRow(rs, rowNum))
                                                .withUniversity(UniversityDatabaseMapping
                                                        .ROW_MAPPER_FOR_RELATED_ENTITIES.mapRow(rs, rowNum))
                                                .build();
                            id = rs.getLong("id");
                        }
                        else if(rs.getLong("id") != id)
                            throw new IllegalResultSetException("The result set must contain only one user");
                        currentUser.getInterestedProperties()
                                .add(PropertyDatabaseMapping.ROW_MAPPER_FOR_RELATED_ENTITIES.mapRow(rs, rowNum));
                    }
                    return currentUser;
            };
}
