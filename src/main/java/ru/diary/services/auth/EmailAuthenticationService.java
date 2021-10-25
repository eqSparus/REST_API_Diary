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
public class EmailAuthenticationService implements EmailService {

    UserDao dao;
    MailingLetters mailingLetters;

    @Autowired
    public EmailAuthenticationService(UserDao dao, MailingLetters mailingLetters) {
        this.dao = dao;
        this.mailingLetters = mailingLetters;
    }

    @Override
    public boolean sendingEmail(String email) {

        var uriComponents = UriComponentsBuilder
                .fromUriString("http://localhost:8080/diary/API/confirm")
                .queryParam("email", "{email}")
                .build();

        final var subject = "Подтверждения адреса";

        final String text = """
                Здравствуйте!
                Вы зарегистрировались на сайте Diary пожалуйста перейдите по
                ссылке ниже для подтверждения электроного адреса
                если это были не вы просто проигнорируйте это сообщения
                """ + uriComponents.expand(email).toUri();

        try {
            mailingLetters.sendingMessageEmail(email, subject, text);
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }

    @Override
    public void updateData(String email) {
        dao.updateStatusActive(email);
    }
}
