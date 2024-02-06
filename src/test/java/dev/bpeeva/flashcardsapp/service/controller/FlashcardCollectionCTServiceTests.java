package dev.bpeeva.flashcardsapp.service.controller;

import dev.bpeeva.flashcardsapp.app.service.controller.FlashcardCollectionCTService;
import dev.bpeeva.flashcardsapp.db.constant.UserRole;
import dev.bpeeva.flashcardsapp.db.model.FlashcardCollection;
import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.db.repo.FlashcardCollectionRepository;
import dev.bpeeva.flashcardsapp.db.repo.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FlashcardCollectionCTServiceTests {

    @Autowired
    private final UserRepository userRepository = null;
    @Autowired
    private final FlashcardCollectionRepository flashcardCollectionRepository = null;

    @Autowired
    private final FlashcardCollectionCTService flashcardCollectionCTService = null;

    private String username, collectionName;

    @BeforeEach
    void setUp() {
        //Create required variables.
        this.username = "Jackie chan";
        this.collectionName = "Animals";

        //Save to repo.
        User user = this.userRepository.save(new User(null, UserRole.USER, this.username, "", null));
        this.flashcardCollectionRepository.save(new FlashcardCollection(null, this.collectionName, null, user));
    }

    @Test
    void shouldCreateAFlashcardCollection() {
        //Create collection name.
        String collectionName = "Weapons";

        //Create flashcard collection.
        boolean isCreated = this.flashcardCollectionCTService.createFlashcardCollection(this.username, collectionName);

        //Check if created.
        assertThat(isCreated).isTrue();
    }

    @Test
    void shouldNotCreateAFlashcardCollectionWithTakenCollectionName() {
        //Create flashcard collection.
        boolean isCreated = this.flashcardCollectionCTService.createFlashcardCollection(this.username, this.collectionName);

        //Check if created.
        assertThat(isCreated).isFalse();
    }

    @Test
    void shouldNotCreateAFlashcardCollectionWithNonExistingUser() {
        //Create collection name.
        String collectionName = "Some funny name";

        //Create flashcard collection.
        boolean isCreated = this.flashcardCollectionCTService.createFlashcardCollection("", collectionName);

        //Check if created.
        assertThat(isCreated).isFalse();
    }

    @Test
    void shouldDeleteAFlashcardCollection() {
        //Delete flashcard collection.
        boolean isDeleted = this.flashcardCollectionCTService.deleteFlashcardCollection(this.username, this.collectionName);

        //Check if deleted.
        assertThat(isDeleted).isTrue();
    }

    @Test
    void shouldNotDeleteAFlashcardCollectionWithNonExistingCollectionName() {
        //Create collection name.
        String collectionName = "Some Idk collection";

        //Delete flashcard collection.
        boolean isDeleted = this.flashcardCollectionCTService.deleteFlashcardCollection(this.username, collectionName);

        //Check if deleted.
        assertThat(isDeleted).isFalse();
    }

    @Test
    void shouldNotDeleteAFlashcardCollectionWithNonExistingUser() {
        //Delete flashcard collection.
        boolean isDeleted = this.flashcardCollectionCTService.deleteFlashcardCollection("", this.collectionName);

        //Check if deleted.
        assertThat(isDeleted).isFalse();
    }

    @Test
    void shouldReturn1FlashcardCollection() {
        //Fetch all flashcard collection names.
        List<String> flashcardCollectionNames = this.flashcardCollectionCTService.getAllFlashcardCollections(this.username);

        //Check list size.
        assertThat(flashcardCollectionNames.size()).isEqualTo(1);
    }

    @Test
    void shouldReturn0WithNonExistingUser() {
        //Fetch all flashcard collection names.
        List<String> flashcardCollectionNames = this.flashcardCollectionCTService.getAllFlashcardCollections("");

        //Check list size.
        assertThat(flashcardCollectionNames.size()).isEqualTo(0);
    }
}
