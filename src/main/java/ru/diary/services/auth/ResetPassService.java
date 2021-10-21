package ru.diary.services.auth;

import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ru.diary.mail.MailingLetters;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ResetPassService {

    MailingLetters mailingLetters;

    @Autowired
    public ResetPassService(MailingLetters mailingLetters) {
        this.mailingLetters = mailingLetters;
    }

    public void resetPassword(String email) {
        var uriComponents = UriComponentsBuilder
                .fromUriString("http://localhost:3000/recover/{token}")
                .build();

        final String subject = "Password reset";

        final String text = """
                Hello!
                To reset your password, follow the link below. If you weren going to
                reset password just ignore this message
                """ + uriComponents.expand(email).toUri();

        try {
            mailingLetters.sendingMessageEmail(email, subject, text);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
