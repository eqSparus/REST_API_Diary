package ru.diary.services.auth;

import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ru.diary.configurations.security.jwt.TokenCreator;
import ru.diary.mail.MailingLetters;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ResetPassService {

    MailingLetters mailingLetters;
    TokenCreator creator;

    @Autowired
    public ResetPassService(MailingLetters mailingLetters, TokenCreator creator) {
        this.mailingLetters = mailingLetters;
        this.creator = creator;
    }

    //TODO Изменить время setTime()
    public void resetPassword(String email) {

        creator.setTime(300L);
        String token = creator.createToken(email);

        var uriComponents = UriComponentsBuilder
                .fromUriString("http://localhost:3000/recover/{token}")
                .build();

        final String subject = "Сброс пароля";

        final String text = """
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
