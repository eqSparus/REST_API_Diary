package ru.diary.services.auth;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.diary.models.User;
import ru.diary.models.form.UserAuth;
import ru.diary.models.form.UserForm;
import ru.diary.repositories.PersonalDataDao;
import ru.diary.repositories.UserDao;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UpdateDataUser {

    PersonalDataDao personalDataDao;
    UserDao userDao;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UpdateDataUser(PersonalDataDao personalDataDao, UserDao userDao, PasswordEncoder passwordEncoder) {
        this.personalDataDao = personalDataDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean updatePassword(String email, String password, String newPassword) {

        var user = userDao.findUserByEmail(email).orElseThrow(IllegalAccessError::new);

        if (passwordEncoder.matches(password, user.getPassword())) {
            personalDataDao.updatePassword(
                    User.builder()
                            .email(email)
                            .password(passwordEncoder.encode(newPassword))
                            .build()
            );
            return true;
        }
        return false;
    }

    public UserForm updateName(UserAuth userAuth, String email) {
        personalDataDao.updateName(User.builder().username(userAuth.getUsername())
                .email(email).build());
        return new UserForm(userDao.findUserByEmail(email).orElseThrow(IllegalAccessError::new));
    }
}
