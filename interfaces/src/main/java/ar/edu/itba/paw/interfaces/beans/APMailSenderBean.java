package ar.edu.itba.paw.interfaces.beans;

import ar.edu.itba.paw.interfaces.APJavaMailSender;
import ar.edu.itba.paw.model.User;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Collection;

public class APMailSenderBean implements APJavaMailSender {

    private final JavaMailSender emailSender;

    public APMailSenderBean(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public MimeMessage createMimeMessage() {
        return emailSender.createMimeMessage();
    }

    @Override
    public MimeMessage createMimeMessage(InputStream inputStream) throws MailException {
        return emailSender.createMimeMessage(inputStream);
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        emailSender.send(mimeMessage);
    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {
        emailSender.send(mimeMessages);
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
        emailSender.send(mimeMessagePreparator);
    }

    @Override
    public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
        emailSender.send(mimeMessagePreparators);
    }

    @Override
    public void send(SimpleMailMessage simpleMailMessage) throws MailException {
        emailSender.send(simpleMailMessage);
    }

    @Override
    public void send(SimpleMailMessage... simpleMailMessages) throws MailException {
        emailSender.send(simpleMailMessages);
    }


    @Override
    public void sendEmailToUsers(String title, String body, Collection<User> users) {
        String[] to = new String[users.size()];
        int index=0;
        for(User u : users){
            to[index++] = u.getEmail();
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(title);
        message.setText(body);
        emailSender.send(message);
    }

    @Override
    public void sendEmailToSingleUser(String title, String body, User recipient) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient.getEmail());
        message.setSubject(title);
        message.setText(body);
        emailSender.send(message);
    }
}
