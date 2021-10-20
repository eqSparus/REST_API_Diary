package ru.diary.configurations.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class TokenCreator {

    @Value("${token.key}")
    String key;

    @Value("${token.time}")
    Long time;

    @PostConstruct
    protected void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    public String createToken(String email) {
        var claims = Jwts.claims()
                .setSubject(email);
        var timeStart = Instant.now();
        var timeStop = Instant.ofEpochSecond(timeStart.getEpochSecond() + time);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(timeStart))
                .setExpiration(Date.from(timeStop))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String getLogin(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validTokenTime(String token) {
        var claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        return !claimsJws.getBody().getExpiration().before(new Date());
    }

}
