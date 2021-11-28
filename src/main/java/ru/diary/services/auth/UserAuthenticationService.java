package ru.diary.services.auth;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.diary.configurations.security.jwt.TokenCreator;
import ru.diary.models.Role;
import ru.diary.models.Status;
import ru.diary.models.User;
import ru.diary.models.form.UserAuth;
import ru.diary.repositories.UserDao;
import ru.diary.services.EmailService;
import ru.diary.services.UserService;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserAuthenticationService implements UserService {

    EmailService emailService;
    UserDao userDao;
    TokenCreator creator;
    PasswordEncoder encoder;


    @Autowired
    public UserAuthenticationService(@Qualifier("emailAuthenticationService") EmailService emailService,
                                     UserDao userDao, TokenCreator creator, PasswordEncoder encoder) {
        this.emailService = emailService;
        this.userDao = userDao;
        this.creator = creator;
        this.encoder = encoder;
    }

    @Override
    public Optional<String> loginUser(UserAuth user) {

        var userExit = userDao.findUserByEmail(user.getEmail());

        return userExit.flatMap(u -> {
            if (encoder.matches(user.getPassword(), u.getPassword())) {
                return Optional.ofNullable(creator.createToken(user.getEmail()));
            }
            return Optional.empty();
        });
    }

    //TODO Перенести emailService
    @Override
    public boolean registrationUser(UserAuth user) {
        var userExit = userDao.findUserByEmail(user.getEmail());

        return userExit.map(u -> false)
                .orElseGet(() -> {
                    var newUser = User.builder()
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .password(encoder.encode(user.getPassword()))
                            .role(Role.USER)
                            .status(Status.IN_BASE)
                            .build();
                    userDao.create(newUser);
                    emailService.sendingEmail(newUser.getEmail());
                    return true;
                });
    }
}
