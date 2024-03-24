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
public class FlashcardRepositoryTests {

    @Autowired
    private final UserRepository userRepo = null;
    @Autowired
    private final FlashcardCollectionRepository collRepo = null;
    @Autowired
    private final FlashcardRepository flashcardRepo = null;

    private User arthur;
    private FlashcardCollection animals;

    @BeforeEach
    void setUpDB() {
        //Arrange
        arthur = userRepo.save(new User(null, "Arthur", "^*^#@Rfa", null));
        animals = collRepo.save(new FlashcardCollection(null, "Animals", null, arthur));
    }

    @Test
    void shouldCreateANewFlashcard() {
        //Act
        Flashcard flashcard = flashcardRepo.save(new Flashcard(null, "Pies", "A dog", null));

        //Assert
        assertThat(flashcard).isNotNull();
    }

    @Test
    void shouldDeleteAFlashcard() {
        //Arrange
        Flashcard flashcard = flashcardRepo.save(new Flashcard(null, "Kaczka", "A duck", null));

        //Act
        flashcardRepo.delete(flashcard);

        //Assert
        assertThat(flashcardRepo.count()).isEqualTo(0);
    }

    @Test
    void shouldTryToDeleteByIdANonExistingFlashcard() {
        //Act
        flashcardRepo.deleteById(1);

        //Assert
        assertThat(flashcardRepo.count()).isEqualTo(0);
    }

    @Test
    void shouldFetchAFlashcardById() {
        //Arrange
        Flashcard flashcard = flashcardRepo.save(new Flashcard(null, "Kaczka", "A duck", null));

        //Act
        Optional<Flashcard> fetchedFlashcard = flashcardRepo.findById(flashcard.getId());

        //Assert
        assertThat(fetchedFlashcard.isPresent()).isTrue();
    }

    @Test
    void shouldTryToFetchAFlashcardByIdButGetNull() {
        //Act
        Optional<Flashcard> flashcard = flashcardRepo.findById(1);

        //Assert
        assertThat(flashcard.isPresent()).isFalse();
    }

    @Test
    void shouldFetch2FlashcardsByCollection() {
        //Arrange
        flashcardRepo.save(new Flashcard(null, "Kot", "A cat", animals));
        flashcardRepo.save(new Flashcard(null, "Krowa", "A cow", animals));

        //Act
        List<Flashcard> flashcardList = flashcardRepo.findAllByCollection(animals);

        //Assert
        assertThat(flashcardList.size()).isEqualTo(2);
    }

    @Test
    void shouldFetch0FlashcardsByCollection() {
        //Act
        List<Flashcard> flashcardList = flashcardRepo.findAllByCollection(animals);

        //Assert
        assertThat(flashcardList.size()).isEqualTo(0);
    }
}
