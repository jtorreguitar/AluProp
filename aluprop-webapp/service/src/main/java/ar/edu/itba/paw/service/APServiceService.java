package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.dao.ServiceDao;
import ar.edu.itba.paw.interfaces.service.ServiceService;
import ar.edu.itba.paw.model.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@org.springframework.stereotype.Service
public class APServiceService implements ServiceService {

    @Autowired
    private ServiceDao serviceDao;

    @Override
    public Collection<Service> getAll() {
        return serviceDao.getAll();
    }
}
