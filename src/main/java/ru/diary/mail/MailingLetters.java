package ru.diary.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class MailingLetters {

    @Value("${email.address}")
    String address;

    final SessionMail session;

    @Autowired
    public MailingLetters(SessionMail session) {
        this.session = session;
    }

    public void sendingMessageEmail(String email, String subject, String text) throws MessagingException {
        var message = new MimeMessage(session.getSession());
        message.setFrom(new InternetAddress(address));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject(subject);
        message.setText(text);
        Transport.send(message);
    }

}
