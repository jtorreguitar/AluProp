package ar.edu.itba.paw.interfaces.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import ar.edu.itba.paw.interfaces.Either;
import ar.edu.itba.paw.interfaces.PageRequest;
import ar.edu.itba.paw.interfaces.PageResponse;
import ar.edu.itba.paw.model.Property;
import ar.edu.itba.paw.model.User;
import org.springframework.lang.Nullable;

public interface PropertyService {

    Property get(long id);
    PageResponse<Property> getAll(PageRequest pageRequest);

    PageResponse<Property> getByDescription(PageRequest pageRequest, String description);
    PageResponse<Property> advancedSearch(PageRequest pageRequest,
                                          String description,
                                          @Nullable Integer propertyType,
                                          @Nullable int neighborhood,
                                          @Nullable Integer privacyLevel,
                                          @Nullable Integer capacity,
                                          @Nullable float minPrice,
                                          @Nullable float maxPrice,
                                          @Nullable long[] rules,
                                          @Nullable long[] services);

    int showInterestOrReturnErrors(long propertyId, User user);
	int undoInterestOrReturnErrors(long propertyId, User user);
    Property getPropertyWithRelatedEntities(long id);
    Either<Property, Collection<String>> create(Property property);
    Collection<Property> getInterestsOfUser(long id);
}
