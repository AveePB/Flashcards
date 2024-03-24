package com.dev.app.db.repo;

import com.dev.app.db.model.FlashcardCollection;
import com.dev.app.db.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTests {

    @Autowired
    private final UserRepository userRepo = null;
    @Autowired
    private final FlashcardCollectionRepository collRepo = null;

    private User alex;

    @BeforeEach
    void setUpDB() {
        //Arrange
        alex = userRepo.save(new User(null, "Alex", "98y4hG*&G", null));
    }

    @Test
    void shouldCreateANewUser() {
        //Act
        userRepo.save(new User(null, "Jack", "*HG(DW", null));

        //Assert
        assertThat(userRepo.count()).isEqualTo(2);
    }

    @Test
    void shouldFailToCreateANewUserBecauseOfTakenNickname() {
        try {
            //Act
            userRepo.save(new User(null, "Alex", "^&(R^R", null));
        }
        catch (DataIntegrityViolationException ignored) { }

        //Assert
        assertThat(userRepo.count()).isEqualTo(1);
    }

    @Test
    void shouldDeleteAUserWithNoCollections() {
        //Act
        userRepo.delete(alex);

        //Assert
        assertThat(userRepo.count()).isEqualTo(0);
    }

    @Test
    void shouldDeleteAUserWithAFewCollections() {
        //Arrange
        collRepo.save(new FlashcardCollection(null, "Cars", null, alex));
        collRepo.save(new FlashcardCollection(null, "Fruits", null, alex));

        //Act
        userRepo.delete(alex);

        //Assert
        assertThat(userRepo.count()).isEqualTo(0);
        assertThat(collRepo.count()).isEqualTo(0);
    }

    @Test
    void shouldDeleteAUserById() {
        //Act
        userRepo.deleteById(1);

        //Assert
        assertThat(userRepo.count()).isEqualTo(0);
    }

    @Test
    void shouldTryToDeleteAUserById() {
        //Act
        userRepo.deleteById(2);

        //Assert
        assertThat(userRepo.count()).isEqualTo(1);
    }

    @Test
    void shouldFetchAUserById() {
        //Act
        Optional<User> user = userRepo.findById(1);

        //Assert
        assertThat(user.isPresent()).isTrue();
    }

    @Test
    void shouldTryToFetchAUserByIdButGetNull() {
        //Act
        Optional<User> user = userRepo.findById(2);

        //Assert
        assertThat(user.isPresent()).isFalse();
    }
}
