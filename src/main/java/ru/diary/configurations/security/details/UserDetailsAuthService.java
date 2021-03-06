package ru.diary.configurations.security.details;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.diary.repositories.IUserRepository;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserDetailsAuthService implements UserDetailsService {

    IUserRepository dao;

    @Autowired
    public UserDetailsAuthService(IUserRepository dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new UserDetailsAuth(dao.findUserByEmail(email).orElseThrow(IllegalArgumentException::new));
    }
}
