package com.dev.app.bll.handler;

import com.dev.app.db.model.User;
import com.dev.app.db.repo.UserRepository;
import com.dev.app.util.dto.UserDTO;
import com.dev.app.util.token.JsonWebToken;

import org.assertj.core.error.AssertionErrorCreator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthHandlerTests {

    @Autowired
    private final AuthHandler authHandler = null;
    @Autowired
    private final PasswordEncoder passwordEncoder = null;

    @Autowired
    private final UserRepository userRepo = null;

    @BeforeEach
    void setUpDB() {
        //Arrange
        userRepo.save(new User(null, "Sebastian", passwordEncoder.encode("HIR3horr"), null));
    }

    @Test
    void shouldSuccessfullyLogInAUser() throws Exception {
        //Act
        JsonWebToken jwt = authHandler.logIn(new UserDTO("Sebastian", "HIR3horr"));

        //Assert
        assertThat(jwt).isNotNull();
    }

    @Test
    void shouldFailToLogInAUserBecauseOfBadCredentials() throws Exception{
        try {
            //Act
            JsonWebToken jwt = authHandler.logIn(new UserDTO("Adrian", "S*HR)HR$*"));
        }
        //Assert
        catch (BadCredentialsException ex) {
            return;
        }

        throw new AssertionErrorCreator().assertionError("Successfully logged a user, but shouldn't...");
    }

    @Test
    void shouldFailToLogInAUserBecauseOfANullArg() {
        try {
            //Act
            JsonWebToken jwt = authHandler.logIn(null);
        }
        //Assert
        catch (HandlerException ex) {
            return;
        }

        throw new AssertionErrorCreator().assertionError("Successfully logged a user, but shouldn't...");
    }

    @Test
    void shouldSuccessfullySignUpAUser() throws Exception {
        //Act
        JsonWebToken jwt = authHandler.signUp(new UserDTO("Jack", "JKSF($*(H$#"));

        //Assert
        assertThat(jwt).isNotNull();
    }

    @Test
    void shouldFailToSignUpAUserBecauseOfTakenUsername() {
        try {
            //Act
            JsonWebToken jwt = authHandler.signUp(new UserDTO("Sebastian", "FNHW$&*T%"));
        }
        //Assert
        catch (HandlerException ex) {
            return;
        }

        throw new AssertionErrorCreator().assertionError("Successfully created a user, but shouldn't...");
    }

    @Test
    void shouldFailToSignUpAUserBecauseOfANullArg() {
        try {
            //Act
            JsonWebToken jwt = authHandler.signUp(null);
        }
        //Assert
        catch (HandlerException ex) {
            return;
        }

        throw new AssertionErrorCreator().assertionError("Successfully created a user, but shouldn't...");
    }
}
