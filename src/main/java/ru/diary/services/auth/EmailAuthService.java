package ru.diary.services.auth;

import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ru.diary.mail.MailingLetters;
import ru.diary.repositories.UserDao;
import ru.diary.services.EmailService;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class EmailAuthService implements EmailService {

    UserDao dao;
    MailingLetters mailingLetters;

    @Autowired
    public EmailAuthService(UserDao dao, MailingLetters mailingLetters) {
        this.dao = dao;
        this.mailingLetters = mailingLetters;
    }

    @Override
    public void emailActiveMail(String email) {

        var uriComponents = UriComponentsBuilder
                .fromUriString("http://localhost:8080/diary/API/confirm")
                .queryParam("email", "{email}")
                .build();

        final String subject = "Confirmation of address";

        final String text = """
                Hello!
                Please confirm your email address by clicking on the link.
                If you haven't registered just ignore these are messages.
                """ + uriComponents.expand(email).toUri();

        try {
            mailingLetters.sendingMessageEmail(email, subject, text);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateUserActive(String email) {
        dao.updateStatusActive(email);
    }
}