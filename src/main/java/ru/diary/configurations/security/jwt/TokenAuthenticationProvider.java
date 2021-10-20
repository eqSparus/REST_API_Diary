package ru.diary.configurations.security.jwt;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    UserDetailsService service;
    TokenCreator creator;

    @Autowired
    public TokenAuthenticationProvider(UserDetailsService service, TokenCreator creator) {
        this.service = service;
        this.creator = creator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var tokenAuthentication = (TokenAuth) authentication;

        var login = creator.getLogin(tokenAuthentication.getName());
        var userDetails = service.loadUserByUsername(login);

        tokenAuthentication.setDetails(userDetails);
        tokenAuthentication.setAuthenticated(true);
        return tokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuth.class.equals(authentication);
    }
}
