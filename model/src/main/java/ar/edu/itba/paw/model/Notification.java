package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notifications_id_seq")
    @SequenceGenerator(sequenceName = "notifications_id_seq", name = "notifications_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Transient
    private long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @Column
    private String subjectCode;

    @Column
    private String textCode;

    @Column
    private String link;

    // TODO: please turn into enum
    @Column
    private int state;

    public Notification(long id, long userId, String subjectCode, String textCode, String link, int state) {
        this.id = id;
        this.userId = userId;
        this.textCode = textCode;
        this.subjectCode = subjectCode;
        this.link = link;
        this.state = state;
    }

    public long getId() {return id; }
    public void setId(long id) { this.id = id; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getTextCode() { return textCode; }
    public void setTextCode(String textCode) { this.textCode = textCode; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public int getState() { return state; }
    public void setState(int state) { this.state = state; }
}
