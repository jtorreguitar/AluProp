package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Collection;

public interface APJavaMailSender extends JavaMailSender {

    void sendEmailToUsers(String title, String body, Collection<User> users);
    void sendEmailToSingleUser(String title, String body, User recipient);
}
