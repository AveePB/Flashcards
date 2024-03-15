package dev.bpeeva.app.util;

import dev.bpeeva.app.bll.manager.JWTService;
import dev.bpeeva.app.util.token.JWT;

import io.jsonwebtoken.security.Keys;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JWTTests {
    @Autowired
    private final JWTService jwtService = null;

    @Test
    void shouldReturnFalseBecauseOfLackingBearerToken() {
        boolean isValid = new JWT(null).isValid("Haranami", Keys.hmacShaKeyFor(new byte[32]));

        assertThat(isValid).isFalse();
    }

    @Test
    void shouldReturnFalseBecauseOfLackingUsername() {
        JWT jwt = this.jwtService.generateToken("Haranami").get();
        boolean isValid = jwt.isValid(null, Keys.hmacShaKeyFor(new byte[32]));

        assertThat(isValid).isFalse();
    }

    @Test
    void shouldReturnFalseBecauseOfLackingSecretKey() {
        JWT jwt = this.jwtService.generateToken("Haranami").get();
        boolean isValid = jwt.isValid("Haranami", null);

        assertThat(isValid).isFalse();
    }
}
