package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class APImageService implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Override
    public Image get(long id) {
        return imageDao.get(id);
    }
}
