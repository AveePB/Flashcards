package com.dev.app.bll.manager;

import com.dev.app.db.model.Flashcard;
import com.dev.app.db.model.FlashcardCollection;
import com.dev.app.db.model.User;
import com.dev.app.db.repo.FlashcardCollectionRepository;
import com.dev.app.db.repo.FlashcardRepository;
import com.dev.app.db.repo.UserRepository;

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
public class FlashcardManagerTests {

    @Autowired
    private final UserRepository userRepo = null;
    @Autowired
    private final FlashcardCollectionRepository collectionRepo = null;
    @Autowired
    private final FlashcardRepository flashcardRepo = null;

    @Autowired
    private final FlashcardManager flashcardManager = null;

    private User bartek;
    private FlashcardCollection views;

    @BeforeEach
    void setUpDB() {
        //Arrange
        bartek = userRepo.save(User.builder()
                .username("Bartek")
                .password("JF(WU&(Y$")
                .build()
        );

        views = collectionRepo.save(FlashcardCollection.builder()
                .name("Views")
                .owner(bartek)
                .build()
        );

        flashcardRepo.save(Flashcard.builder()
                .motherLang("Góry")
                .foreignLang("Mountains")
                .collection(views)
                .build()
        );
    }

    @Test
    void shouldCreateANewFlashcard() {
        //Act
        Optional<Flashcard> flashcard = flashcardManager.createNewFlashcard("Jezioro", "A lake", views);

        //Assert
        assertThat(flashcard.isPresent()).isTrue();
        assertThat(flashcardRepo.count()).isEqualTo(2);
    }

    @Test
    void shouldNotCreateANewFlashcardBecauseOfANonExistingCollection() {
        //Arrange
        FlashcardCollection tools = FlashcardCollection.builder()
                .name("Tools")
                .build();

        //Act
        Optional<Flashcard> flashcard = flashcardManager.createNewFlashcard("Młot", "A hammer", tools);

        //Assert
        assertThat(flashcard.isPresent()).isFalse();
        assertThat(flashcardRepo.count()).isEqualTo(1);
    }

    @Test
    void shouldNotCreateANewFlashcardBecauseOfNullArgs() {
        //Act
        Optional<Flashcard> flashcard = flashcardManager.createNewFlashcard(null, "A sea", views);
        Optional<Flashcard> flashcard2 = flashcardManager.createNewFlashcard("Morze", null, views);
        Optional<Flashcard> flashcard3 = flashcardManager.createNewFlashcard("Morze", "A sea", null);
        Optional<Flashcard> flashcard4 = flashcardManager.createNewFlashcard(null, null, null);

        //Assert
        assertThat(flashcard.isPresent()).isFalse();
        assertThat(flashcard2.isPresent()).isFalse();
        assertThat(flashcard3.isPresent()).isFalse();
        assertThat(flashcard4.isPresent()).isFalse();
        assertThat(flashcardRepo.count()).isEqualTo(1);
    }

    @Test
    void shouldReturnAllFlashcardsInTheCollection() {
        //Act
        List<Flashcard> flashcards = flashcardManager.getAllFlashcards(views);

        //Assert
        assertThat(flashcards.size()).isEqualTo(1);
    }

    @Test
    void shouldNotReturnAnyFlashcardsBecauseOfANonExistingCollection() {
        //Arrange
        FlashcardCollection animals = FlashcardCollection.builder()
                .name("Animals")
                .build();

        //Act
        List<Flashcard> flashcards = flashcardManager.getAllFlashcards(animals);

        //Assert
        assertThat(flashcards.size()).isEqualTo(0);
    }

    @Test
    void shouldReturnNoFlashcardsBecauseOfNullArg() {
        //Act
        List<Flashcard> flashcards = flashcardManager.getAllFlashcards(null);

        //Assert
        assertThat(flashcards.size()).isEqualTo(0);
    }

    @Test
    void shouldReturnARandomFlashcard() {
        //Act
        Optional<Flashcard> flashcard = flashcardManager.getRandomFlashcard(views);

        //Assert
        assertThat(flashcard.isPresent()).isTrue();
        assertThat(flashcard.get().getId()).isEqualTo(1);
    }

    @Test
    void shouldNotReturnARandomFlashcardBecauseOfANonExistingCollection() {
        //Arrange
        FlashcardCollection people = FlashcardCollection.builder()
                .name("People")
                .build();

        //Act
        Optional<Flashcard> flashcard = flashcardManager.getRandomFlashcard(people);

        //Assert
        assertThat(flashcard.isPresent()).isFalse();
    }

    @Test
    void shouldNotReturnARandomFlashcardBecauseOfNullArg() {
        //Act
        Optional<Flashcard> flashcard = flashcardManager.getRandomFlashcard(null);

        //Assert
        assertThat(flashcard.isPresent()).isFalse();
    }

    @Test
    void shouldDeleteAFlashcard() {
        //Act
        boolean isDeleted = flashcardManager.deleteFlashcard(1);

        //Assert
        assertThat(isDeleted).isTrue();
        assertThat(flashcardRepo.count()).isEqualTo(0);
    }

    @Test
    void shouldTryToDeleteANonExistingFlashcard() {
        //Act
        boolean isDeleted = flashcardManager.deleteFlashcard(2);

        //Assert
        assertThat(isDeleted).isTrue();
        assertThat(flashcardRepo.count()).isEqualTo(1);
    }

    @Test
    void shouldNotDeleteAFlashcardBecauseOfNullArg() {
        //Act
        boolean isDeleted = flashcardManager.deleteFlashcard(null);

        //Assert
        assertThat(isDeleted).isFalse();
        assertThat(flashcardRepo.count()).isEqualTo(1);
    }
}
