package ru.diary.services.auth;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.diary.configurations.security.jwt.TokenCreator;
import ru.diary.models.Role;
import ru.diary.models.Status;
import ru.diary.models.User;
import ru.diary.models.UserAuth;
import ru.diary.repositories.UserDao;
import ru.diary.services.UserService;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserAuthService implements UserService {

    UserDao userDao;
    TokenCreator creator;
    PasswordEncoder encoder;
    static Logger LOG = LoggerFactory.getLogger("test");

    @Autowired
    public UserAuthService(UserDao userDao, TokenCreator creator, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.creator = creator;
        this.encoder = encoder;
    }

    @Override
    public String loginUser(UserAuth user) {

        var userExit = userDao.findUserByEmail(user.getEmail());

        if (userExit.isPresent()) {
            var password = userExit.get().getPassword();
            if (encoder.matches(user.getPassword(), password)) {
                var token = creator.createToken(user.getEmail());
                LOG.info("Login user {} get token {}", user, token);
                return token;
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean registrationUser(UserAuth user) {
        var userExit = userDao.findUserByEmail(user.getEmail());

        if (userExit.isEmpty()) {

            var newUser = User.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(encoder.encode(user.getPassword()))
                    .role(Role.USER)
                    .status(Status.IN_BASE)
                    .build();

            userDao.create(newUser);

            LOG.info("create user {}", user);
            return true;
        }
        return false;
    }
}
