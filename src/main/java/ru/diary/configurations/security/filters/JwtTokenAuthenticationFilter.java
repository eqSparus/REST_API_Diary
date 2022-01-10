package ru.diary.configurations.security.filters;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.diary.configurations.security.jwt.JwtTokenProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class JwtTokenAuthenticationFilter extends GenericFilterBean {

    final JwtTokenProvider provider;

    @Autowired
    public JwtTokenAuthenticationFilter(JwtTokenProvider provider) {
        this.provider = provider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        var token = provider.resolveToken((HttpServletRequest) request);

        if (token!=null&&provider.isValidTokenTime(token)){
            var auth = provider.getAuthentication(token);

            if (auth!=null){
                var securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(auth);
                SecurityContextHolder.setContext(securityContext);
            }
        }

        chain.doFilter(request, response);
    }
}
