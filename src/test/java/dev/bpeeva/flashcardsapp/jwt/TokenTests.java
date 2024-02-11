package dev.bpeeva.flashcardsapp.jwt;

import dev.bpeeva.flashcardsapp.db.constant.UserRole;
import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.security.jwt.Token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TokenTests {

    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode("5ZYb6tZ9S76wVSNOEL+NJVCD9mbTVM+/QwXuZohaJrt0EGYhaCu2TSZNcenuaHID"));
    private static final int TOKEN_LIFESPAN = 3600 * 1000; //ONE HOUR

    private static String subject = "Johnathan";
    private static Token token, expiredToken;

    @BeforeAll
    static void setUp() {
        //Generate token.
        String bearerToken = Jwts.builder()
                .signWith(SECRET_KEY)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_LIFESPAN))
                .compact();

        token = new Token(bearerToken);

        //Generate token that will expire.
        String bearerToken2 = Jwts.builder()
                .signWith(SECRET_KEY)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()))
                .compact();

        expiredToken = new Token(bearerToken2);
    }

    @Test
    void shouldSuccessfullyValidateAToken() {
        //Validate token.
        boolean isValid = token.isValid(new User(null, UserRole.USER, subject, "", null), SECRET_KEY);

        //Check validation result.
        assertThat(isValid).isTrue();
    }

    @Test
    void shouldNotSuccessfullyValidateAToken() {
        //Validate token.
        boolean isValid = token.isValid(new User(null, UserRole.USER, "Jas", "", null), SECRET_KEY);

        //Check validation result.
        assertThat(isValid).isFalse();
    }

    @Test
    void shouldReturnTrueWhenTokenIsExpired() {
        //Apply function.
        boolean isExpired = expiredToken.isExpired(SECRET_KEY);

        //Check result.
        assertThat(isExpired).isTrue();
    }
}
