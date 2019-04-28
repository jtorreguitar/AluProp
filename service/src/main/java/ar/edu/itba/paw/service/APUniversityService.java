package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.dao.UniversityDao;
import ar.edu.itba.paw.interfaces.service.UniversityService;
import ar.edu.itba.paw.model.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class APUniversityService implements UniversityService {

    @Autowired private UniversityDao universityDao;

    public University get(long id) {
        return universityDao.get(id);
    }

    public Collection<University> getAll() {
        return universityDao.getAll();
    }
}
