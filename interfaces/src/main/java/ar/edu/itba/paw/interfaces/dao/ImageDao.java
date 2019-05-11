package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Image;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

public interface ImageDao {
    Image get(long id);
    Collection<Image> getByProperty(long propertyId);
    void addProperty(long id, long propertyId);
    long create(byte[] image);
}
