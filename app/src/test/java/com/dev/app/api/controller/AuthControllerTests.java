package com.dev.app.api.controller;

import com.dev.app.bll.manager.JWTManager;
import com.dev.app.db.model.User;
import com.dev.app.db.repo.UserRepository;
import com.dev.app.util.dto.UserDTO;

import com.dev.app.util.token.JsonWebToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthControllerTests {

    private static final String LOGIN_URL = "/auth/log-in";
    private static final String SIGN_UP_URL = "/auth/sign-up";

    @Autowired
    private final TestRestTemplate restTemplate = null;
    @Autowired
    private final PasswordEncoder passwordEncoder = null;

    @Autowired
    private final UserRepository userRepo = null;

    @BeforeEach
    void setUpDB() {
        //Arrange
        userRepo.save(new User(null, "Kacper", passwordEncoder.encode("SHFO$H$#R$"), null));
        userRepo.save(new User(null, "Jan", passwordEncoder.encode("FSOUDHFOF"), null));
        userRepo.save(new User(null, "Sebastian", passwordEncoder.encode("HIR3horr"), null));
    }

    @Test
    void shouldSuccessfullyLogInAUser() {
        //Arrange
        UserDTO userDTO = new UserDTO("Sebastian", "HIR3horr");

        //Act
        ResponseEntity<JsonWebToken> responseEntity = restTemplate.postForEntity(LOGIN_URL, userDTO, JsonWebToken.class);
        JsonWebToken jwt = responseEntity.getBody();

        //Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jwt.bearer()).isNotNull();
        assertThat(jwt.bearer().length()).isGreaterThan(0);
    }

    @Test
    void shouldFailToLogInAUserBecauseOfBadRequestForm() {
        //Act
        ResponseEntity<JsonWebToken> responseEntity = restTemplate.postForEntity(LOGIN_URL, null, JsonWebToken.class);
        JsonWebToken jwt = responseEntity.getBody();

        //Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(jwt).isNull();
    }

    @Test
    void shouldFailToLogInAUserBecauseOfInvalidCredentials() {
        //Arrange
        UserDTO userDTO = new UserDTO("Jonas", "(F*YW(R$RR$R$");

        //Act
        ResponseEntity<JsonWebToken> responseEntity = restTemplate.postForEntity(LOGIN_URL, userDTO, JsonWebToken.class);
        JsonWebToken jwt = responseEntity.getBody();

        //Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(jwt).isNull();
    }

    @Test
    void shouldSuccessfullySignUpAUser() {
        //Arrange
        UserDTO userDTO = new UserDTO("Emily", "FJD(F*EHEWF(");

        //Act
        ResponseEntity<JsonWebToken> responseEntity = restTemplate.postForEntity(SIGN_UP_URL, userDTO, JsonWebToken.class);
        JsonWebToken jwt = responseEntity.getBody();

        //Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jwt.bearer()).isNotNull();
        assertThat(jwt.bearer().length()).isGreaterThan(0);
    }

    @Test
    void shouldFailToSignUpAUserBecauseOfBadRequestForm() {
        //Act
        ResponseEntity<JsonWebToken> responseEntity = restTemplate.postForEntity(SIGN_UP_URL, null, JsonWebToken.class);
        JsonWebToken jwt = responseEntity.getBody();

        //Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(jwt).isNull();
    }

    @Test
    void shouldFailToSignUpAUserBecauseOfTakenUsername() {
        //Arrange
        UserDTO userDTO = new UserDTO("Sebastian", "FSDHOSFqw34");
        //Act
        ResponseEntity<JsonWebToken> responseEntity = restTemplate.postForEntity(SIGN_UP_URL, userDTO, JsonWebToken.class);
        JsonWebToken jwt = responseEntity.getBody();

        //Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(jwt).isNull();
    }
}
