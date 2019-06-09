package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.io.InputStream;
import java.util.Collection;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "images_id_seq")
    @SequenceGenerator(sequenceName = "images_id_seq", name = "images_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Transient
    private long propertyId;

    @Column
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propertyId")
    private Property property;

    /* package */ Image() { }

    public Image(long id, long propertyId, byte[] image) {
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

    public byte[] getImage() {
        return image;
    }

    public Property getProperty() {
        return property;
    }
}
