package ru.diary.services.auth;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.diary.models.User;
import ru.diary.models.form.UserAuth;
import ru.diary.repositories.PersonalDataDao;
import ru.diary.services.DataAuthService;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PersonalDataUserService implements DataAuthService {

    PersonalDataDao dataDao;
    PasswordEncoder passwordEncoder;


    @Autowired
    public PersonalDataUserService(PersonalDataDao dataDao, PasswordEncoder passwordEncoder) {
        this.dataDao = dataDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void updatePassword(UserAuth user) {

        var newDataUsed = User.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();

        dataDao.updatePassword(newDataUsed);

    }

    @Override
    public void updateName(UserAuth user) {

        var newDateUsed = User.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();

        dataDao.updateName(newDateUsed);
    }


}
