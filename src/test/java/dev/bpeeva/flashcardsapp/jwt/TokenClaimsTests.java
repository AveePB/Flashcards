package dev.bpeeva.flashcardsapp.jwt;

import dev.bpeeva.flashcardsapp.security.jwt.TokenClaims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TokenClaimsTests {

    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode("5ZYb6tZ9S76wVSNOEL+NJVCD9mbTVM+/QwXuZohaJrt0EGYhaCu2TSZNcenuaHID"));
    private static final int TOKEN_LIFESPAN = 3600 * 1000; //ONE HOUR

    private static Date expirationDate = new Date(System.currentTimeMillis() + TOKEN_LIFESPAN);
    private static String subject = "Johnny", token;

    @BeforeAll
    static void setUp() {
        //Generate token.
        token = Jwts.builder()
                .signWith(SECRET_KEY)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .compact();
    }

    @Test
    void shouldExtractAnExpirationDate() {
        //Apply function.
        Optional<Date> extractedExpirationDate = TokenClaims.extractExpirationDate(token, SECRET_KEY);

        //Check presence.
        assertThat(extractedExpirationDate.isPresent()).isTrue();

        //Check expiration date.
        assertThat(extractedExpirationDate.get().before(expirationDate)).isTrue();
    }

    @Test
    void shouldNotExtractAnExpirationDateWithNoToken() {
        //Apply function.
        Optional<Date> extractedExpirationDate = TokenClaims.extractExpirationDate("", SECRET_KEY);

        //Check presence.
        assertThat(extractedExpirationDate.isPresent()).isFalse();
    }

    @Test
    void shouldExtractASubject() {
        //Apply function.
        Optional<String> extractedSubject = TokenClaims.extractSubject(token, SECRET_KEY);

        //Check presence.
        assertThat(extractedSubject.isPresent()).isTrue();

        //Check expiration date.
        assertThat(extractedSubject.get()).isEqualTo(subject);
    }

    @Test
    void shouldNotExtractASubjectWithNoToken() {
        //Apply function.
        Optional<String> extractedSubject = TokenClaims.extractSubject(null, SECRET_KEY);

        //Check presence.
        assertThat(extractedSubject.isPresent()).isFalse();
    }
}
