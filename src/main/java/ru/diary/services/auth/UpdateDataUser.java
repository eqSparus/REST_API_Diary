package ru.diary.services.auth;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diary.models.User;
import ru.diary.models.dto.UserAuth;
import ru.diary.models.dto.UserDto;
import ru.diary.repositories.IPersonalDataRepository;
import ru.diary.repositories.IUserRepository;
import ru.diary.services.auth.exeption.RestPasswordException;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Transactional
public class UpdateDataUser {

    IPersonalDataRepository personalDataDao;
    IUserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UpdateDataUser(IPersonalDataRepository personalDataDao,
                          IUserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.personalDataDao = personalDataDao;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String updatePassword(String email, String password, String newPassword) throws RestPasswordException {

        var user = userRepository.findUserByEmail(email).orElseThrow(IllegalAccessError::new);

        if (passwordEncoder.matches(password, user.getPassword())) {
            personalDataDao.updatePassword(
                    User.builder()
                            .email(email)
                            .password(passwordEncoder.encode(newPassword))
                            .build()
            );
            return "Пароль изменен";
        }else {
            throw new RestPasswordException();
        }
    }

    public UserDto updateName(UserAuth userAuth, String email) {
        personalDataDao.updateName(User.builder().username(userAuth.getUsername())
                .email(email).build());
        return new UserDto(userRepository.findUserByEmail(email).orElseThrow(IllegalAccessError::new));
    }
}
