package dev.bpeeva.flashcardsapp.jwt;

import dev.bpeeva.flashcardsapp.db.constant.UserRole;
import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.security.jwt.Token;
import dev.bpeeva.flashcardsapp.security.jwt.TokenService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TokenServiceTests {

    @Autowired
    private final TokenService tokenService = null;

    private static UserDetails userDetails;

    @BeforeAll
    static void setUp() {
        //Create user.
        userDetails = new User(null, UserRole.USER, "J4ck", "", null);
    }

    @Test
    void shouldGenerateAJWT() {
        //Apply function.
        Token generatedToken = this.tokenService.generateJWT(userDetails, TokenService.getSigningKey());

        //Check token.
        assertThat(generatedToken.getBearerToken()).isNotNull();
        assertThat(generatedToken.getBearerToken().equals("")).isFalse();
    }

    @Test
    void shouldNotGenerateAJWTWithNoUserDetails() {
        //Apply function.
        Token generatedToken = this.tokenService.generateJWT(null, TokenService.getSigningKey());

        //Check token.
        assertThat(generatedToken.isValid(userDetails, TokenService.getSigningKey())).isFalse();
    }

    @Test
    void shouldNotGenerateAJWTWithNoKey() {
        //Apply function.
        Token generatedToken = this.tokenService.generateJWT(userDetails, null);

        //Check token.
        assertThat(generatedToken.isValid(userDetails, TokenService.getSigningKey())).isFalse();
    }
}
