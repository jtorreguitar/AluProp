package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.interfaces.SearchableProperty;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.PropertyOrder;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Optional;

public interface PropertyService {

    Property get(long id);
    PageResponse<Property> getAll(PageRequest pageRequest, PropertyOrder propertyOrder);
    PageResponse<Property> advancedSearch(PageRequest pageRequest, SearchableProperty property);
    int showInterestOrReturnErrors(long propertyId, User user);
	int undoInterestOrReturnErrors(long propertyId, User user);
    Property getPropertyWithRelatedEntities(long id);
    Either<Property, Collection<String>> create(Property property);
    int delete(long id, User currentUser);
    Collection<Property> getInterestsOfUser(long id);
    int changeStatus(long propertyId);
}
