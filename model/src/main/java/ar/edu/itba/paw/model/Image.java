package ar.edu.itba.paw.model;

import java.io.InputStream;

public class Image {
    private long id;
    private long propertyId;
    private InputStream image;

    public Image(long id, long propertyId, InputStream image) {
        this.id = id;
        this.propertyId = propertyId;
        this.image = image;
    }

    public Image(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getPropertyId() {
        return propertyId;
    }

    public InputStream getImage() {
        return image;
    }
}
