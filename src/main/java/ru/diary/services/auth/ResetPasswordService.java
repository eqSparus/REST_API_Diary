package ru.diary.services.auth;

import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ru.diary.configurations.security.jwt.TokenCreator;
import ru.diary.mail.MailingLetters;
import ru.diary.models.Status;
import ru.diary.repositories.UserDao;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ResetPasswordService {

    private static final long TIME_RESET = 300L;
    UserDao userDao;
    MailingLetters mailingLetters;
    TokenCreator creator;

    @Autowired
    public ResetPasswordService(UserDao userDao, MailingLetters mailingLetters, TokenCreator creator) {
        this.userDao = userDao;
        this.mailingLetters = mailingLetters;
        this.creator = creator;
    }

    public boolean resetPassword(String email) {
        var user = userDao.findUserByEmail(email);

        if (user.isPresent() && user.get().getStatus().equals(Status.ACTIVE)) {
            recoverPasswordEmail(email);
            return true;
        }
        return false;
    }

    //TODO Изменить время setTime()
    private void recoverPasswordEmail(String email) {

        creator.setTime(TIME_RESET);
        var token = creator.createToken(email);

        var uriComponents = UriComponentsBuilder
                .fromUriString("http://localhost:3000/recover/{token}")
                .build();

        final var subject = "Сброс пароля";

        final var text = """
                Здравствуйте!
                Чтобы сбросить пароль, перейдите по ссылке ниже.
                Если вы не собирались сбрасывать пароль, просто проигнорируйте это сообщение.
                """ + uriComponents.expand(token).toUri();

        try {
            mailingLetters.sendingMessageEmail(email, subject, text);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}