package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;
import org.springframework.mail.javamail.JavaMailSender;

public interface APJavaMailSender extends JavaMailSender {

    void sendEmailToSingleUser(String title, String body, User recipient);
}
