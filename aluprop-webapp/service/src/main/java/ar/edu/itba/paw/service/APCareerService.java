package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.dao.CareerDao;
import ar.edu.itba.paw.interfaces.service.CareerService;
import ar.edu.itba.paw.model.Career;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class APCareerService implements CareerService {

    @Autowired private CareerDao careerDao;

    public Career get(long id) {
        return careerDao.get(id);
    }

    public Collection<Career> getAll() {
        return careerDao.getAll();
    }
}
