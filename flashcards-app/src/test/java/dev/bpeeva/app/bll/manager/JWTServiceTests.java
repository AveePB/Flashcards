package dev.bpeeva.app.bll.manager;

import dev.bpeeva.app.util.token.JWT;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JWTServiceTests {
    @Autowired
    private JWTService jwtService = null;

    @Test
    void shouldSuccessfullyValidateAToken() {
        JWT jwt = this.jwtService.generateToken("Ayko").get();
        boolean isValid = this.jwtService.validate("Ayko", jwt);

        assertThat(isValid).isTrue();
    }

    @Test
    void shouldReturnFalseBecauseOfAnIncorrectUsername() {
        JWT jwt = this.jwtService.generateToken("Ayko").get();
        boolean isValid = this.jwtService.validate("Okya", jwt);

        assertThat(isValid).isFalse();
    }

    @Test
    void shouldGenerateAJWT() {
        Optional<JWT> jwt = this.jwtService.generateToken("Arek");

        assertThat(jwt.isPresent()).isTrue();
    }

    @Test
    void shouldNotGenerateAJWTBecauseOfLackingUsername() {
        Optional<JWT> jwt = this.jwtService.generateToken(null);

        assertThat(jwt.isPresent()).isFalse();
    }
}
