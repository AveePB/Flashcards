package com.dev.app.db.repo;

import com.dev.app.db.model.Flashcard;
import com.dev.app.db.model.FlashcardCollection;
import com.dev.app.db.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FlashcardCollectionRepositoryTests {

    @Autowired
    private final UserRepository userRepo = null;
    @Autowired
    private final FlashcardCollectionRepository collRepo = null;
    @Autowired
    private final FlashcardRepository flashcardRepo = null;

    private User jeremy;
    private FlashcardCollection jobs;

    @BeforeEach
    void setUpDB() {
        //Arrange
        jeremy = userRepo.save(new User(null, "Jeremy", "&(*#@3422", null));
        jobs = collRepo.save(new FlashcardCollection(null, "Jobs", null, jeremy));
        flashcardRepo.save(new Flashcard(null, "Mechanik", "A mechanic", jobs));
    }

    @Test
    void shouldCreateANewCollection() {
        //Act
        FlashcardCollection flashcardCollection = collRepo.save(new FlashcardCollection(null, "Vegetables", null, jeremy));

        //Assert
        assertThat(collRepo.count()).isEqualTo(2);
    }

    @Test
    void shouldDeleteAnEmptyCollection() {
        //Arrange
        FlashcardCollection flashcardCollection = collRepo.save(new FlashcardCollection(null, "Vegetables", null, jeremy));

        //Act
        collRepo.delete(flashcardCollection);

        //Assert
        assertThat(collRepo.count()).isEqualTo(1);
    }

    @Test
    void shouldDeleteANonEmptyCollection() {
        //Act
        collRepo.delete(jobs);

        //Assert
        assertThat(collRepo.count()).isEqualTo(0);
        assertThat(flashcardRepo.count()).isEqualTo(0);
    }

    @Test
    void shouldDeleteACollectionById() {
        //Act
        collRepo.deleteById(1);

        //Assert
        assertThat(collRepo.count()).isEqualTo(0);
    }

    @Test
    void shouldTryToDeleteACollectionById() {
        //Act
        collRepo.deleteById(3);

        //Assert
        assertThat(collRepo.count()).isEqualTo(1);
    }

    @Test
    void shouldFetchACollectionById() {
        //Act
        Optional<FlashcardCollection> flashcardCollection = collRepo.findById(1);

        //Assert
        assertThat(flashcardCollection.isPresent()).isTrue();
    }

    @Test
    void shouldTryToFetchACollectionByIdButGetNull() {
        //Act
        Optional<FlashcardCollection> flashcardCollection = collRepo.findById(2);

        //Assert
        assertThat(flashcardCollection.isPresent()).isFalse();
    }

    @Test
    void shouldFetchAllOwnedCollections() {
        //Act
        List<FlashcardCollection> flashcardCollectionList = collRepo.findAllByOwner(jeremy);

        //Assert
        assertThat(flashcardCollectionList.size()).isEqualTo(1);
    }
}
