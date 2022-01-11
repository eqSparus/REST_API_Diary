package ru.diary.services.auth;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diary.models.User;
import ru.diary.models.dto.UserAuth;
import ru.diary.repositories.IPersonalDataRepository;
import ru.diary.services.IDataAuthService;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Transactional
public class PersonalDataUserService implements IDataAuthService {

    IPersonalDataRepository personalDataRepository;
    PasswordEncoder passwordEncoder;


    @Autowired
    public PersonalDataUserService(IPersonalDataRepository personalDataRepository, PasswordEncoder passwordEncoder) {
        this.personalDataRepository = personalDataRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void resetPassword(UserAuth user) {

        var newDataUsed = User.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();

        personalDataRepository.updatePassword(newDataUsed);

    }
}
