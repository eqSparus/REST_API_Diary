package ru.diary.configurations.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Component
public class JwtTokenProvider {

    @Value("${token.key}")
    String key;

    @Value("${token.time}")
    long time;

    @Value("${token.header}")
    String header;

    UserDetailsService detailsService;

    @Autowired
    public JwtTokenProvider(UserDetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @PostConstruct
    protected void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String email) {

        var dateNow = Instant.now();
        var dateValidTime = Instant.ofEpochSecond(dateNow.getEpochSecond() + time);


        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(dateNow))
                .setExpiration(Date.from(dateValidTime))
                .signWith(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public String getEmail(String token) {

        var parser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8))).build();

        return parser.parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isValidTokenTime(String token) {

        var parser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8))).build();

        return !parser.parseClaimsJws(token).getBody().getExpiration().before(Date.from(Instant.now()));
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader(header);
    }

    public Authentication getAuthentication(String token){

        var details = detailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(details,"",details.getAuthorities());
    }

}
