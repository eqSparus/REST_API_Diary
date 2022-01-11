package ru.diary.services.auth.exeption;


import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException {

    public AuthException(String msg) {
        super(msg);
    }

    public AuthException() {
        super("Fail authentication");
    }
}
