package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.Gender;
import ar.edu.itba.paw.model.utilities.ArgumentUtility;

import java.sql.Date;
import java.util.Collection;

public class User {
    private long id;
    private String email;
    private String username;
    private String name;
    private String lastName;
    private Date birthDate;
    private Gender gender;
    private String passwordHash;
    private long universityId;
    private University university;
    private long careerId;
    private Career career;
    private String bio;
    private String contactNumber;
    private Collection<Property> interestedProperties;

    private User() { }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public long getUniversityId() {
        return universityId;
    }

    public University getUniversity() {
        return university;
    }

    public long getCareerId() {
        return careerId;
    }

    public Career getCareer() {
        return career;
    }

    public String getBio() {
        return bio;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public Collection<Property> getInterestedProperties() {
        return interestedProperties;
    }

    public static class Builder {
        private static User user;

        public Builder() {
            this.user = new User();
        }

        public User build(){
            ArgumentUtility.isNotPositive(user.id, "id must be provided.");
            ArgumentUtility.stringIsNotNullOrEmpty(user.email, "email must be provided.");
            ArgumentUtility.stringIsNotNullOrEmpty(user.username, "username must be provided.");
            ArgumentUtility.stringIsNotNullOrEmpty(user.name, "name must be provided.");
            ArgumentUtility.stringIsNotNullOrEmpty(user.lastName, "last name must be provided.");
            ArgumentUtility.isNotNull(user.birthDate, "birth date must be provided.");
            ArgumentUtility.isNotNull(user.gender, "gender must be provided");
            ArgumentUtility.stringIsNotNullOrEmpty(user.passwordHash, "password must be provided");
            if(user.universityId < 1 || user.university == null) throw new IllegalArgumentException("university must be provided");
            if(user.careerId < 1 || user.career == null) throw new IllegalArgumentException("university must be provided");
            ArgumentUtility.stringIsNotNullOrEmpty(user.bio, "bio must be provided");
            ArgumentUtility.stringIsNotNullOrEmpty(user.contactNumber, "contact number must be provided");
            return user;
        }

        public Builder fromUser(User u) {
            user.id = u.id;
            user.email = u.email;
            user.username = u.username;
            user.name = u.name;
            user.lastName = u.lastName;
            user.birthDate = u.birthDate;
            user.gender = u.gender;
            user.passwordHash = u.passwordHash;
            user.university = u.university;
            user.universityId = u.universityId;
            user.career = u.career;
            user.careerId = u.careerId;
            user.bio = u.bio;
            user.contactNumber = u.contactNumber;
            user.interestedProperties = u.interestedProperties;
            return this;
        }

        public Builder withId(long id) {
            user.id = id;
            return this;
        }

        public Builder withEmail(String email) {
            user.email = email;
            return this;
        }

        public Builder withUsername(String username) {
            user.username = username;
            return this;
        }

        public Builder withName(String name) {
            user.name = name;
            return this;
        }

        public Builder withLastName(String lastName) {
            user.lastName = lastName;
            return this;
        }

        public Builder withBirthDate(Date birthDate) {
            user.birthDate = birthDate;
            return this;
        }

        public Builder withGender(Gender gender) {
            user.gender = gender;
            return this;
        }

        public Builder withPasswordHash(String passwordHash) {
            user.passwordHash = passwordHash;
            return this;
        }

        public Builder withUniversityId(long universityId) {
            user.universityId = universityId;
            return this;
        }

        public Builder withUniversity(University university) {
            user.university = university;
            return this;
        }

        public Builder withCareerId(long careerId) {
            user.careerId = careerId;
            return this;
        }

        public Builder withCareer(Career career) {
            user.career = career;
            return this;
        }

        public Builder withBio(String bio) {
            user.bio = bio;
            return this;
        }

        public Builder withContactNumber(String contactNumber) {
            user.contactNumber = contactNumber;
            return this;
        }

        public Builder withInterestedProperties(Collection<Property> interestedProperties) {
            user.interestedProperties = interestedProperties;
            return this;
        }
    }
}
