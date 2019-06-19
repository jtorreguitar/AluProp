package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image get(long id);

    long create(MultipartFile file);
}
