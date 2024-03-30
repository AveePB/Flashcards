package com.dev.app.bll.manager;

import com.dev.app.util.token.JsonWebToken;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JWTManagerTests {

    @Autowired
    private final JWTManager jwtManager = null;

    private String signingKey256Bit = "47ob123Wz81FIlvbbBESn0wtE5leqz98XQSH8Y43Jv1MqclLvVIxjq3iJKYNetg7";
    private int tokenLifespan = 3600 * 1000;

    private Key getSigningKey() {
        byte[] keyInBytes = Decoders.BASE64URL.decode(signingKey256Bit);

        return Keys.hmacShaKeyFor(keyInBytes);
    }

    @Test
    void shouldSuccessfullyValidateAJWT() {
        //Arrange
        String bearer = Jwts.builder()
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .setSubject("Adam")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLifespan))
                .compact();

        JsonWebToken jwt = new JsonWebToken(bearer);

        //Act
        boolean isValid = jwtManager.validate(jwt, signingKey256Bit, "Adam");

        //Assert
        assertThat(isValid).isTrue();
    }

    @Test
    void shouldFailToValidateAJWTBecauseOfTheExpirationDate() {
        //Arrange
        String bearer = Jwts.builder()
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .setSubject("Olaf")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() - tokenLifespan))
                .compact();

        JsonWebToken jwt = new JsonWebToken(bearer);

        //Act
        boolean isValid = jwtManager.validate(jwt, signingKey256Bit, "Olaf");

        //Assert
        assertThat(isValid).isFalse();
    }

    @Test
    void shouldFailToValidateAJWTBecauseOfTheUsername() {
        //Arrange
        String bearer = Jwts.builder()
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .setSubject("Ania")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLifespan))
                .compact();

        JsonWebToken jwt = new JsonWebToken(bearer);

        //Act
        boolean isValid = jwtManager.validate(jwt, signingKey256Bit, "Magda");

        //Assert
        assertThat(isValid).isFalse();
    }

    @Test
    void shouldFailToValidateAJWTBecauseOfNullArgs() {
        //Arrange
        String bearer = Jwts.builder()
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .setSubject("Ania")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLifespan))
                .compact();

        JsonWebToken jwt = new JsonWebToken(bearer);

        //Act
        boolean isValid = jwtManager.validate(null, signingKey256Bit, "Ania");
        boolean isValid2 = jwtManager.validate(jwt, null, "Ania");
        boolean isValid3 = jwtManager.validate(jwt, signingKey256Bit, null);

        //Assert
        assertThat(isValid).isFalse();
        assertThat(isValid2).isFalse();
        assertThat(isValid3).isFalse();
    }

    @Test
    void shouldSuccessfullyGenerateAJWT() {
        //Act
        Optional<JsonWebToken> jwt = jwtManager.generateJWT("Natalia", signingKey256Bit);

        //Assert
        assertThat(jwt.isPresent()).isTrue();
        assertThat(jwtManager.validate(jwt.get(), signingKey256Bit, "Natalia")).isTrue();
    }

    @Test
    void shouldFailToGenerateAJWTBecauseOfNullArgs() {
        //Act
        Optional<JsonWebToken> jwt = jwtManager.generateJWT(null, signingKey256Bit);
        Optional<JsonWebToken> jwt2 = jwtManager.generateJWT("Krzyś", null);

        //Assert
        assertThat(jwt.isPresent()).isFalse();
        assertThat(jwt2.isPresent()).isFalse();
    }
}
