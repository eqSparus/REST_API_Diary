package ru.diary.configurations.security.filters;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.diary.configurations.security.jwt.TokenAuth;
import ru.diary.configurations.security.jwt.TokenCreator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class TokenAuthenticationFilter implements Filter {

    @Value("${token.header}")
    String header;

    final TokenCreator provider;

    @Autowired
    public TokenAuthenticationFilter(TokenCreator provider) {
        this.provider = provider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        var token = req.getHeader(header);

        var tokenAuthentication = new TokenAuth(token);

        if (token != null && provider.validTokenTime(token)) {
            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
        } else {
            tokenAuthentication.setAuthenticated(false);
        }
        chain.doFilter(request, response);
    }
}
