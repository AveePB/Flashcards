package com.dev.app.bll.handler;

import com.dev.app.db.model.FlashcardCollection;
import com.dev.app.db.model.User;
import com.dev.app.db.repo.FlashcardCollectionRepository;
import com.dev.app.db.repo.UserRepository;
import com.dev.app.util.dto.FlashcardCollectionDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FlashcardCollectionHandlerTests {

    @Autowired
    private final UserRepository userRepo = null;
    @Autowired
    private final FlashcardCollectionRepository collRepo = null;

    @Autowired
    private final FlashcardCollectionHandler flashcardCollectionHandler = null;

    @BeforeEach
    void setUpDB() {
        //Arrange
        User janusz = userRepo.save(new User(null, "Janusz", "SHDFOI$T#$T$#T", null));
        collRepo.save(new FlashcardCollection(null, "Animals", null, janusz));
    }

    @Test
    void shouldSuccessfullyCreateANewCollection() throws Exception {
        //Arrange
        FlashcardCollectionDTO flashcardCollectionDTO = new FlashcardCollectionDTO("Tools", "Janusz");

        //Act
        URI uri = flashcardCollectionHandler.createNewFlashcardCollection(flashcardCollectionDTO);

        //Assert
        assertThat(uri).isNotNull();
        assertThat(uri.toString()).isEqualTo("flashcards/Janusz/2");
    }

    @Test
    void shouldFailToCreateANewCollectionBecauseOfANonExistingUser() throws Exception {
        //Arrange
        FlashcardCollectionDTO flashcardCollectionDTO = new FlashcardCollectionDTO("Jobs", "Steve");

        try {
            //Act
            URI uri = flashcardCollectionHandler.createNewFlashcardCollection(flashcardCollectionDTO);
        }
        //Assert
        catch (Exception ex) {
            return;
        }
        throw new Exception("Created a new resource, but shouldn't...");
    }

    @Test
    void shouldFailToCreateANewCollectionBecauseOfATakenCollectionName() throws Exception {
        //Arrange
        FlashcardCollectionDTO flashcardCollectionDTO = new FlashcardCollectionDTO("Animals", "Janusz");

        try {
            //Act
            URI uri = flashcardCollectionHandler.createNewFlashcardCollection(flashcardCollectionDTO);
        }
        //Assert
        catch (Exception ex) {
            return;
        }
        throw new Exception("Created a new resource, but shouldn't...");
    }

    @Test
    void shouldFailToCreateANewCollectionBecauseOfANullArg() throws Exception {
        try {
            //Act
            URI uri = flashcardCollectionHandler.createNewFlashcardCollection(null);
        }
        //Assert
        catch (Exception ex) {
            return;
        }
        throw new Exception("Created a new resource, but shouldn't...");
    }
}
