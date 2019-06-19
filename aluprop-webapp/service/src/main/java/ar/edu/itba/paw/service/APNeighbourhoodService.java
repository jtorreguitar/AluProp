package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.dao.NeighbourhoodDao;
import ar.edu.itba.paw.interfaces.service.NeighbourhoodService;
import ar.edu.itba.paw.model.Neighbourhood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class APNeighbourhoodService implements NeighbourhoodService {

    @Autowired
    private NeighbourhoodDao neighbourhoodDao;

    @Override
    public Collection<Neighbourhood> getAll() {
        return neighbourhoodDao.getAll();
    }
}
