package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Gender;
import ar.edu.itba.paw.model.enums.Role;

import java.sql.Date;

public class Factories {
    public static User userCreator(){
        return new User.Builder()
                .withEmail(APUserDaoTest.EMAIL2)
                .withName(APUserDaoTest.NAME2)
                .withLastName("Tester")
                .withBirthDate(Date.valueOf("1996-07-12"))
                .withGender(Gender.MALE)
                .withPasswordHash("SuperS3CReTP4ssW0rd")
                .withUniversityId(1)
                .withCareerId(1)
                .withBio("Hello, how are you today?")
                .withContactNumber("1166765456")
                .withRole(Role.ROLE_GUEST)
                .build();
    }
}
