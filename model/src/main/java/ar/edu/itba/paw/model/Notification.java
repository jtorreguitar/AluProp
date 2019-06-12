package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.NotificationState;

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
    private NotificationState state;

    Notification(){ }

    public static class Builder{
        private Notification notification;
        public Builder(){
            this.notification = new Notification();
        }
        public Builder withId(long id) {
            notification.id = id;
            return this;
        }

        public Builder withUserId(long id) {
            notification.userId = id;
            return this;
        }

        public Builder withTextCode(String textCode) {
            notification.textCode = textCode;
            return this;
        }

        public Builder withSubjectCode(String subjectCode) {
            notification.subjectCode = subjectCode;
            return this;
        }

        public Builder withLink(String link) {
            notification.link = link;
            return this;
        }

        public Builder withState(NotificationState state) {
            notification.state = state;
            return this;
        }

        public Notification build(){
            return notification;
        }
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

    public NotificationState getState() { return state; }
    public void setState(NotificationState state) { this.state = state; }
}
