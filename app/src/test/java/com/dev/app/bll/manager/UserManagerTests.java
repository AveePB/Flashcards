package com.dev.app.bll.manager;

import com.dev.app.db.model.User;
import com.dev.app.db.repo.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserManagerTests {

    @Autowired
    private final UserRepository userRepository = null;

    @Autowired
    private final UserManager userManager = null;

    @BeforeEach
    void setUpDB() {
        //Arrange
        userRepository.save(User.builder()
                .username("Jacek")
                .password("96(SFFWF/[';]")
                .build()
        );
    }

    @Test
    void shouldCreateANewUser() {
        //Act
        Optional<User> createdUser = userManager.createNewUser("Tomek", "(&*WRWFDS");

        //Assert
        assertThat(createdUser.isPresent()).isTrue();
    }

    @Test
    void shouldNotCreateANewUserBecauseOfATakenNickname() {
        //Act
        Optional<User> createdUser = userManager.createNewUser("Jacek", "(*rh9fefw");

        //Assert
        assertThat(createdUser.isPresent()).isFalse();
    }

    @Test
    void shouldNotCreateANewUserBecauseOfNullArgs() {
        //Act
        Optional<User> createdUser1 = userManager.createNewUser(null, "klshdf98yr4");
        Optional<User> createdUser2 = userManager.createNewUser("Keanu", null);
        Optional<User> createdUser3 = userManager.createNewUser(null, null);

        //Assert
        assertThat(createdUser1.isPresent()).isFalse();
        assertThat(createdUser2.isPresent()).isFalse();
        assertThat(createdUser3.isPresent()).isFalse();
    }

    @Test
    void shouldDeleteAUser() {
        //Act
        boolean isDeleted = userManager.deleteUser("Jacek");

        //Assert
        assertThat(isDeleted).isTrue();
        assertThat(userRepository.count()).isEqualTo(0);
    }

    @Test
    void shouldTryToDeleteANonExistingUser() {
        //Act
        boolean isDeleted = userManager.deleteUser("Krzysiek");

        //Assert
        assertThat(isDeleted).isTrue();
        assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    void shouldNotDeleteAUserBecauseOfANullArg() {
        //Act
        boolean isDeleted = userManager.deleteUser(null);

        //Assert
        assertThat(isDeleted).isFalse();
        assertThat(userRepository.count()).isEqualTo(1);
    }
}
