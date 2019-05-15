package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.model.User;

import java.util.List;

public interface UserService {
    User get(long id);
    User getByEmail(String username);
    Either<User, List<String>> CreateUser(User user);
    User getUserWithRelatedEntitiesByEmail(String email);
    PageResponse<User> getUsersInterestedInProperty(long id, PageRequest pageRequest);
    boolean getUserIsInterestedInProperty(long userId, long propertyId);
}
