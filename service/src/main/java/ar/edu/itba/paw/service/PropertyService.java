package ar.edu.itba.paw.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.IPropertyDao;
import ar.edu.itba.paw.interfaces.IPropertyService;
import ar.edu.itba.paw.model.Property;

@Service
public class PropertyService implements IPropertyService {

    @Autowired
    private IPropertyDao propertyDao;

    @Override
    public Property get(int id) {
        return propertyDao.get(id);
    }

    @Override
    public Collection<Property> getAll() {
        return propertyDao.getAll();
    }

}