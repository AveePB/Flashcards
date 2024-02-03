package dev.bpeeva.flashcardsapp.service.model;

import dev.bpeeva.flashcardsapp.app.service.model.FlashcardCollectionService;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FlashcardCollectionServiceTests {

    @Autowired
    private final FlashcardCollectionService flashcardCollectionService = null;
    @Autowired
    private final UserRepository userRepository = null;
    @Autowired
    private final FlashcardCollectionRepository flashcardCollectionRepository = null;

    private FlashcardCollection flashcardCollection;
    private User user;

    @BeforeEach
    void setUp() {
        //Create required objects.
        this.user = new User(null, UserRole.USER, "J4ck1e Ch4n", "", null);
        this.flashcardCollection = new FlashcardCollection(null, "Animals", null, this.user);

        //Save to repo.
        this.userRepository.save(this.user);
        this.flashcardCollectionRepository.save(this.flashcardCollection);
    }

    @Test
    void shouldReturnTrueWhenAskedAboutTheTakenFlashcardCollectionName() {
        //Fetch boolean value.
        boolean isCollectionNameTaken = this.flashcardCollectionService.isFlashcardCollectionNameTaken(this.user, this.flashcardCollection.getName());

        //Check if collection name taken.
        assertThat(isCollectionNameTaken).isEqualTo(true);
    }

    @Test
    void shouldReturnFalseWhenAskedAboutTheNonTakenFlashcardCollectionName() {
        //Fetch boolean value.
        boolean isCollectionNameTaken = this.flashcardCollectionService.isFlashcardCollectionNameTaken(this.user, "Some name");

        //Check if collection name taken.
        assertThat(isCollectionNameTaken).isEqualTo(false);
    }

    @Test
    void shouldReturnAllFlashcardCollections() {
        //Fetch all flashcard collections.
        List<FlashcardCollection> flashcardCollectionList = this.flashcardCollectionService.getFlashcardCollections(this.user);

        //Check list size.
        assertThat(flashcardCollectionList.size()).isEqualTo(1);
    }

    @Test
    void shouldReturn0IfThereIsNoUser() {
        //Fetch all flashcard collections.
        List<FlashcardCollection> flashcardCollectionList = this.flashcardCollectionService.getFlashcardCollections(null);

        //Check list size.
        assertThat(flashcardCollectionList.size()).isEqualTo(0);
    }

    @Test
    void shouldReturnAFlashcardCollectionOwnedByTheUser() {
        //Fetch flashcard collection.
        Optional<FlashcardCollection> flashcardCollection = this.flashcardCollectionService.getFlashcardCollection(this.user, this.flashcardCollection.getName());

        //Check if present.
        assertThat(flashcardCollection.isPresent()).isEqualTo(true);

        //Check flashcard collection NAME.
        assertThat(flashcardCollection.get().getName()).isEqualTo(this.flashcardCollection.getName());
    }

    @Test
    void shouldNotReturnAFlashcardCollectionNotOwnedByTheUser() {
        //Fetch flashcard collection.
        Optional<FlashcardCollection> flashcardCollection = this.flashcardCollectionService.getFlashcardCollection(this.user, "Random Name");

        //Check if empty.
        assertThat(flashcardCollection.isEmpty()).isEqualTo(true);
    }

    @Test
    void shouldCreateANewFlashcardCollection() {
        //Create flashcard collection.
        Optional<FlashcardCollection> flashcardCollection = this.flashcardCollectionService.createFlashcardCollection(this.user, "Unique Name");

        //Check if present.
        assertThat(flashcardCollection.isPresent()).isEqualTo(true);

        //Check if ID not null.
        assertThat(flashcardCollection.get().getId()).isNotNull();
    }

    @Test
    void shouldNotCreateANewFlashcardCollectionWithTakenName() {
        //Create flashcard collection.
        Optional<FlashcardCollection> flashcardCollection = this.flashcardCollectionService.createFlashcardCollection(this.user, this.flashcardCollection.getName());

        //Check if empty.
        assertThat(flashcardCollection.isEmpty()).isEqualTo(true);
    }

    @Test
    void shouldNotCreateANewFlashcardCollectionWithNoUser() {
        //Create flashcard collection.
        Optional<FlashcardCollection> flashcardCollection = this.flashcardCollectionService.createFlashcardCollection(null, this.flashcardCollection.getName());

        //Check if empty.
        assertThat(flashcardCollection.isEmpty()).isEqualTo(true);
    }

    @Test
    void shouldDeleteAFlashcardCollection() {
        //Delete flashcard collection.
        this.flashcardCollectionService.deleteFlashcardCollection(this.user, this.flashcardCollection.getName());

        //Check if flashcard collection is present.
        boolean isFlashcardCollectionNameTaken = this.flashcardCollectionService.isFlashcardCollectionNameTaken(this.user, this.flashcardCollection.getName());
        assertThat(isFlashcardCollectionNameTaken).isEqualTo(false);
    }

    @Test
    void shouldNotDeleteAFlashcardCollectionThatDoesNotExist() {
        //Delete flashcard collection.
        this.flashcardCollectionService.deleteFlashcardCollection(this.user, "Some name");

        //Check if flashcard collection is present.
        boolean isFlashcardCollectionNameTaken = this.flashcardCollectionService.isFlashcardCollectionNameTaken(this.user, this.flashcardCollection.getName());
        assertThat(isFlashcardCollectionNameTaken).isEqualTo(true);
    }

    @Test
    void shouldNotDeleteAFlashcardCollectionWithNoUser() {
        //Delete flashcard collection.
        this.flashcardCollectionService.deleteFlashcardCollection(null, this.flashcardCollection.getName());

        //Check if flashcard collection is present.
        boolean isFlashcardCollectionNameTaken = this.flashcardCollectionService.isFlashcardCollectionNameTaken(this.user, this.flashcardCollection.getName());
        assertThat(isFlashcardCollectionNameTaken).isEqualTo(true);
    }
}
