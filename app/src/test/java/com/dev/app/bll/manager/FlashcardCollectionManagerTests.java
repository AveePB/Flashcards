package com.dev.app.bll.manager;

import com.dev.app.db.model.FlashcardCollection;
import com.dev.app.db.model.User;
import com.dev.app.db.repo.FlashcardCollectionRepository;
import com.dev.app.db.repo.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FlashcardCollectionManagerTests {

    @Autowired
    private final UserRepository userRepository = null;
    @Autowired
    private final FlashcardCollectionRepository flashcardCollectionRepository = null;

    @Autowired
    private final FlashcardCollectionManager flashcardCollectionManager = null;

    private User john;
    private FlashcardCollection books;

    @BeforeEach
    void setUpDB() {
        //Arrange
        john = userRepository.save(User.builder()
                .username("John")
                .password("(*&GFDFG")
                .build()
        );

        books = flashcardCollectionRepository.save(FlashcardCollection.builder()
                .name("Books")
                .owner(john)
                .build()
        );
    }

    @Test
    void shouldCreateANewFlashcardCollection() {
        //Act
        Optional<FlashcardCollection> flashcardCollection = flashcardCollectionManager.createNewFlashcardCollection("Food", john);

        //Assert
        assertThat(flashcardCollection.isPresent()).isTrue();
        assertThat(flashcardCollectionRepository.count()).isEqualTo(2);
    }

    @Test
    void shouldNotCreateANewFlashcardCollectionBecauseOfTakenName() {
        //Act
        Optional<FlashcardCollection> flashcardCollection = flashcardCollectionManager.createNewFlashcardCollection("Books", john);

        //Assert
        assertThat(flashcardCollection.isPresent()).isFalse();
        assertThat(flashcardCollectionRepository.count()).isEqualTo(1);
    }

    @Test
    void shouldNotCreateANewFlashcardCollectionBecauseOfANonExistingUser() {
        //Arrange
        User sebastian = User.builder()
                .username("Sebastian")
                .password("SFLH(@$$@")
                .build();

        //Act
        Optional<FlashcardCollection> flashcardCollection = flashcardCollectionManager.createNewFlashcardCollection("Dogs", sebastian);

        //Assert
        assertThat(flashcardCollection.isPresent()).isFalse();
        assertThat(flashcardCollectionRepository.count()).isEqualTo(1);
    }

    @Test
    void shouldNotCreateANewFlashcardCollectionBecauseOfNullArgs() {
        //Act
        Optional<FlashcardCollection> flashcardCollection = flashcardCollectionManager.createNewFlashcardCollection(null, john);
        Optional<FlashcardCollection> flashcardCollection2 = flashcardCollectionManager.createNewFlashcardCollection("Clothes", null);
        Optional<FlashcardCollection> flashcardCollection3 = flashcardCollectionManager.createNewFlashcardCollection(null, null);

        //Assert
        assertThat(flashcardCollection.isPresent()).isFalse();
        assertThat(flashcardCollection2.isPresent()).isFalse();
        assertThat(flashcardCollection3.isPresent()).isFalse();
        assertThat(flashcardCollectionRepository.count()).isEqualTo(1);
    }

    @Test
    void shouldFetchAllOwnedFlashcardCollections() {
        //Act
        List<FlashcardCollection> collectionList = flashcardCollectionManager.getAllFlashcardCollections(john);

        //Assert
        assertThat(collectionList.size()).isEqualTo(1);
    }

    @Test
    void shouldFetchNoFlashcardCollectionBecauseOfLackOfCollections() {
        //Arrange
        User gerard = userRepository.save(new User(null, "Gerard", "SLHFWE(^#$$%", null));

        //Act
        List<FlashcardCollection> collectionList = flashcardCollectionManager.getAllFlashcardCollections(gerard);

        //Assert
        assertThat(collectionList.size()).isEqualTo(0);
    }

    @Test
    void shouldFailToFetchCollectionsBecauseOfANonExistingUser() {
        //Arrange
        User maks = User.builder()
                .username("Maks")
                .password("O*YW$RFSD")
                .build();

        //Act
        List<FlashcardCollection> flashcardCollections = flashcardCollectionManager.getAllFlashcardCollections(maks);

        //Assert
        assertThat(flashcardCollections.size()).isEqualTo(0);
    }

    @Test
    void shouldFailToFetchFlashcardCollectionsBecauseOfNullArgs() {
        //Act
        List<FlashcardCollection> collectionList = flashcardCollectionManager.getAllFlashcardCollections(null);

        //Assert
        assertThat(collectionList.size()).isEqualTo(0);
    }

    @Test
    void shouldUpdateAFlashcardCollectionName() {
        //Act
        Optional<FlashcardCollection> flashcardCollection = flashcardCollectionManager.updateFlashcardCollectionName(1, "Sweets");

        //Assert
        assertThat(flashcardCollection.isPresent()).isTrue();
        assertThat(flashcardCollection.get().getName()).isEqualTo("Sweets");
    }

    @Test
    void shouldTryToUpdateANameNonExistingFlashcardCollection() {
        //Act
        Optional<FlashcardCollection> flashcardCollection = flashcardCollectionManager.updateFlashcardCollectionName(2, "Tools");

        //Assert
        assertThat(flashcardCollection.isPresent()).isFalse();
    }

    @Test
    void shouldNotUpdateAFlashcardCollectionNameBecauseOfNullArgs() {
        //Act
        Optional<FlashcardCollection> flashcardCollection = flashcardCollectionManager.updateFlashcardCollectionName(null, "People");
        Optional<FlashcardCollection> flashcardCollection2 = flashcardCollectionManager.updateFlashcardCollectionName(1, null);
        Optional<FlashcardCollection> flashcardCollection3 = flashcardCollectionManager.updateFlashcardCollectionName(null, null);

        //Assert
        assertThat(flashcardCollection.isPresent()).isFalse();
        assertThat(flashcardCollection2.isPresent()).isFalse();
        assertThat(flashcardCollection3.isPresent()).isFalse();
    }

    @Test
    void shouldDeleteAFlashcardCollection() {
        //Act
        boolean isDeleted = flashcardCollectionManager.deleteFlashcardCollection(books.getId());

        //Assert
        assertThat(isDeleted).isTrue();
        assertThat(flashcardCollectionRepository.count()).isEqualTo(0);
    }

    @Test
    void shouldTryToDeleteANonExistingFlashcardCollection() {
        //Act
        boolean isDeleted = flashcardCollectionManager.deleteFlashcardCollection(2);

        //Assert
        assertThat(isDeleted).isTrue();
        assertThat(flashcardCollectionRepository.count()).isEqualTo(1);
    }

    @Test
    void shouldNotDeleteAFlashcardCollectionBecauseOfNullArg() {
        //Act
        boolean isDeleted = flashcardCollectionManager.deleteFlashcardCollection(null);

        //Assert
        assertThat(isDeleted).isFalse();
        assertThat(flashcardCollectionRepository.count()).isEqualTo(1);
    }
}
