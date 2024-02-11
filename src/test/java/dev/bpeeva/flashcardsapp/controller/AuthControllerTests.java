package dev.bpeeva.flashcardsapp.controller;

import dev.bpeeva.flashcardsapp.db.constant.UserRole;
import dev.bpeeva.flashcardsapp.security.jwt.Token;
import dev.bpeeva.flashcardsapp.util.dto.UserDTO;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthControllerTests {

    @Autowired
    private final TestRestTemplate restTemplate = null;

    @Test
    void shouldCreateANewUser() {
        //Apply request.
        ResponseEntity<Token> response = this.restTemplate.postForEntity("/auth/signup", new UserDTO(UserRole.USER, "Jackie Chan 231", ""), Token.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Check JWT TOKEN.
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldNotCreateANewUserWithTakenName() {
        //Apply requests.
        this.restTemplate.postForEntity("/auth/signup", new UserDTO(UserRole.USER, "Jackie Chan 231", ""), Token.class);
        ResponseEntity<Token> response = this.restTemplate.postForEntity("/auth/signup", new UserDTO(UserRole.USER, "Jackie Chan 231", ""), Token.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldSuccessfullyVerifyAUser() {
        //Apply requests.
        this.restTemplate.postForEntity("/auth/signup", new UserDTO(UserRole.USER, "Youkemas", ""), Token.class);
        ResponseEntity<Token> response = this.restTemplate.postForEntity("/auth/login", new UserDTO(UserRole.USER, "Youkemas", ""), Token.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Check JWT TOKEN.
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldNotSuccessfullyVerifyAUser() {
        //Apply requests.
        ResponseEntity<Token> response = this.restTemplate.postForEntity("/auth/login", new UserDTO(UserRole.USER, "SomeUserName", ""), Token.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
