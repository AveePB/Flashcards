package com.dev.app.bll.handler;

import com.dev.app.db.model.Flashcard;
import com.dev.app.db.model.FlashcardCollection;
import com.dev.app.db.model.User;
import com.dev.app.db.repo.FlashcardCollectionRepository;
import com.dev.app.db.repo.FlashcardRepository;
import com.dev.app.db.repo.UserRepository;
import com.dev.app.util.dto.FlashcardCollectionDTO;
import com.dev.app.util.dto.FlashcardDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FlashcardHandlerTests {

    @Autowired
    private final UserRepository userRepo = null;
    @Autowired
    private final FlashcardCollectionRepository collectionRepo = null;
    @Autowired
    private final FlashcardRepository flashcardRepo = null;

    @Autowired
    private final FlashcardHandler flashcardHandler = null;

    private User maks;
    private FlashcardCollection toys;

    @BeforeEach
    void setUpDB() {
        //Arrange
        maks = userRepo.save(new User(null, "Maks", "JF:*W(YF:*", null));
        toys = collectionRepo.save(new FlashcardCollection(null, "Toys", null, maks));
    }

    @Test
    void shouldSuccessfullyCreateANewFlashcard() throws Exception {
        //Arrange
        FlashcardDTO flashcardDTO = new FlashcardDTO("Żołnierz", "A solider", "Toys", "Maks");

        //Act
        URI uri = flashcardHandler.createNewFlashcard(flashcardDTO);

        //Assert
        assertThat(uri).isNotNull();
        assertThat(uri.toString()).isEqualTo("flashcards/Maks/Toys/1");
    }

    @Test
    void shouldFailToCreateANewFlashcardBecauseOfANonExistingCollection() throws Exception {
        //Arrange
        FlashcardDTO flashcardDTO = new FlashcardDTO("Pomarańcza", "An orange", "Fruits", "Maks");

        try {
            //Act
            URI uri = flashcardHandler.createNewFlashcard(flashcardDTO);
        }
        //Assert
        catch (HandlerException ex) {
            return;
        }

        throw new Exception("Created a flashcard, but shouldn't...");
    }

    @Test
    void shouldFailToCreateANewFlashcardBecauseOfANonExistingUser() throws Exception {
        //Arrange
        FlashcardDTO flashcardDTO = new FlashcardDTO("Lalka", "A doll", "Toys", "Alexander");

        try {
            //Act
            URI uri = flashcardHandler.createNewFlashcard(flashcardDTO);
        }
        //Assert
        catch (HandlerException ex) {
            return;
        }

        throw new Exception("Created a flashcard, but shouldn't...");
    }

    @Test
    void shouldFailToDeleteANewFlashcardBecauseOfANullArg() throws Exception {
        try {
            //Act
            URI uri = flashcardHandler.createNewFlashcard(null);
        }
        //Assert
        catch (HandlerException ex) {
            return;
        }

        throw new Exception("Created a flashcard, but shouldn't...");
    }

    @Test
    void shouldSuccessfullyDeleteAFlashcard() throws Exception {
        //Arrange
        flashcardRepo.save(new Flashcard(null, "Klocek lego", "A lego piece", toys));
        FlashcardDTO flashcardDTO = new FlashcardDTO("Klocek lego", "A lego piece", "Toys", "Maks");

        //Act
        flashcardHandler.deleteFlashcard(flashcardDTO);

        //Assert
        assertThat(flashcardRepo.count()).isEqualTo(0);
    }

    @Test
    void shouldTryToDeleteANonExistingFlashcard() throws Exception {
        //Arrange
        FlashcardDTO flashcardDTO = new FlashcardDTO("Minifigurka", "A minifigure", "Toys", "Maks");

        //Act
        flashcardHandler.deleteFlashcard(flashcardDTO);

        //Assert
        assertThat(flashcardRepo.count()).isEqualTo(0);
    }

    @Test
    void shouldFailToDeleteAFlashcardBecauseOfANonExistingUser() throws Exception {
        //Arrange
        FlashcardDTO flashcardDTO = new FlashcardDTO("Fortnie", "Fortnite", "Games", "Sebastian");

        try {
            //Act
            flashcardHandler.deleteFlashcard(flashcardDTO);
        }
        //Assert
        catch (HandlerException ex) {
            return;
        }
        throw new Exception("Deleted a flashcard, but shouldn't...");
    }

    @Test
    void shouldFailToDeleteAFlashcardBecauseOfANonExistingFlashcardCollection() throws Exception {
        //Arrange
        FlashcardDTO flashcardDTO = new FlashcardDTO("Fortnie", "Fortnite", "Games", "Maks");

        try {
            //Act
            flashcardHandler.deleteFlashcard(flashcardDTO);
        }
        //Assert
        catch (HandlerException ex) {
            return;
        }
        throw new Exception("Deleted a flashcard, but shouldn't...");
    }

    @Test
    void shouldFailToDeleteAFlashcardBecauseOfANullArg() throws Exception {
        try {
            //Act
            flashcardHandler.deleteFlashcard(null);
        }
        //Assert
        catch (HandlerException ex) {
            return;
        }
        throw new Exception("Deleted a flashcard, but shouldn't...");
    }

    @Test
    void shouldSuccessfullyGetARandomFlashcardFromACollection() throws Exception {
        //Arrange
        flashcardRepo.save(new Flashcard(null, "Dinozaur", "A dinosaur", toys));
        FlashcardCollectionDTO flashcardCollectionDTO = new FlashcardCollectionDTO("Toys", "Maks");

        //Act
        Optional<FlashcardDTO> randomFlashcard = flashcardHandler.getRandomFlashcard(flashcardCollectionDTO);

        //Assert
        assertThat(randomFlashcard.isPresent()).isTrue();
    }

    @Test
    void shouldTryToGetARandomFlashcardFromAnEmptyCollection() throws Exception {
        //Arrange
        FlashcardCollectionDTO flashcardCollectionDTO = new FlashcardCollectionDTO("Toys", "Maks");

        //Act
        Optional<FlashcardDTO> randomFlashcard = flashcardHandler.getRandomFlashcard(flashcardCollectionDTO);

        //Assert
        assertThat(randomFlashcard.isPresent()).isFalse();
    }

    @Test
    void shouldFailToGetARandomFlashcardFromACollectionBecauseOfANonExistingUser() throws Exception {
        //Arrange
        FlashcardCollectionDTO flashcardCollectionDTO = new FlashcardCollectionDTO("Toys", "Xavier");

        try {
            //Act
            Optional<FlashcardDTO> randomFlashcard = flashcardHandler.getRandomFlashcard(flashcardCollectionDTO);
        }
        //Assert
        catch (HandlerException ex) {
            return;
        }

        throw new Exception("Somehow fetched a non-existing flashcard...");
    }

    @Test
    void shouldFailToGetARandomFlashcardFromACollectionBecauseOfANonExistingCollection() throws Exception {
        //Arrange
        FlashcardCollectionDTO flashcardCollectionDTO = new FlashcardCollectionDTO("Tools", "Maks");

        try {
            //Act
            Optional<FlashcardDTO> randomFlashcard = flashcardHandler.getRandomFlashcard(flashcardCollectionDTO);
        }
        //Assert
        catch (HandlerException ex) {
            return;
        }

        throw new Exception("Somehow fetched a non-existing flashcard...");
    }

    @Test
    void shouldFailToGetARandomFlashcardFromACollectionBecauseOfANullArg() throws Exception {
        try {
            //Act
            Optional<FlashcardDTO> randomFlashcard = flashcardHandler.getRandomFlashcard(null);
        }
        //Assert
        catch (HandlerException ex) {
            return;
        }

        throw new Exception("Somehow fetched a non-existing flashcard...");
    }
}
