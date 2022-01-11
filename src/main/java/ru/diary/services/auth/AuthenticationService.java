package ru.diary.services.auth;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diary.configurations.security.jwt.JwtTokenProvider;
import ru.diary.models.Role;
import ru.diary.models.Status;
import ru.diary.models.User;
import ru.diary.models.dto.UserAuth;
import ru.diary.repositories.IUserRepository;
import ru.diary.services.IEmailService;
import ru.diary.services.IAuthenticationService;
import ru.diary.services.auth.exeption.AuthException;
import ru.diary.services.auth.exeption.RegistrationErrorLoginExistsException;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Transactional
public class AuthenticationService implements IAuthenticationService {

    IEmailService emailService;
    IUserRepository userRepository;
    JwtTokenProvider creator;
    PasswordEncoder encoder;
    AuthenticationManager manager;


    @Autowired
    public AuthenticationService(
            @Qualifier("emailAuthenticationService") IEmailService emailService,
            IUserRepository userRepository,
            JwtTokenProvider creator,
            PasswordEncoder encoder,
            AuthenticationManager manager) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.creator = creator;
        this.encoder = encoder;
        this.manager = manager;
    }

    @Override
    public String loginUser(UserAuth user) throws AuthException {

        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getEmail(), user.getPassword()));
            return creator.createToken(user.getEmail());
        } catch (AuthenticationException e) {
            throw new AuthException("Fail authenticate");
        }
    }

    @Override
    public String registrationUser(UserAuth user) throws RegistrationErrorLoginExistsException {
        var userExit = userRepository.findUserByEmail(user.getEmail());


        if (userExit.isEmpty()) {

            new Thread(() -> emailService.sendingEmail(user.getEmail())).start();

            var newUser = User.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(encoder.encode(user.getPassword()))
                    .role(Role.USER)
                    .status(Status.IN_BASE)
                    .build();
            userRepository.create(newUser);
            return "Пользователь создан";
        } else {
            throw new RegistrationErrorLoginExistsException();
        }
    }
}
