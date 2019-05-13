package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.model.Image;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class APImageService implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Override
    public Image get(long id) {
        return imageDao.get(id);
    }

    @Override
    public long create(MultipartFile file) {
        try {
            return imageDao.create(file.getBytes());
        }
        catch(IOException e) {
            return 0;
        }
    }
}
