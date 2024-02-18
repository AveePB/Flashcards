package dev.bpeeva.flashcardsapp.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import dev.bpeeva.flashcardsapp.db.constant.UserRole;
import dev.bpeeva.flashcardsapp.db.model.FlashcardCollection;
import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.db.repo.FlashcardCollectionRepository;
import dev.bpeeva.flashcardsapp.db.repo.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FlashcardCollectionControllerTests {

    @Autowired
    private final UserRepository userRepository = null;
    @Autowired
    private final FlashcardCollectionRepository flashcardCollectionRepository = null;

    @Autowired
    private final TestRestTemplate restTemplate = null;

    private User owner;
    private FlashcardCollection flashcardCollection;

    @BeforeEach
    void setUp() {
        //Create required objects.
        this.owner = new User(null, UserRole.USER, "Jack", "", null);
        this.flashcardCollection = new FlashcardCollection(null, "fighters", null, owner);

        //Save to repo.
        this.owner = this.userRepository.save(this.owner);
        this.flashcardCollection = this.flashcardCollectionRepository.save(this.flashcardCollection);
    }

    @Test
    void shouldCreateANewFlashcardCollection() {
        //Apply request.
        ResponseEntity<String> postResponse = this.restTemplate.postForEntity("/flashcards/Jack/PokemonGo", null, String.class);

        //Check STATUS CODE.
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        //COUNT flashcard collections.
        long collectionCount = this.flashcardCollectionRepository.count();
        assertThat(collectionCount).isEqualTo(2L);
    }

    @Test
    void shouldNotCreateANewFlashcardCollectionWithAlreadyTakenName() {
        //Apply request.
        ResponseEntity<String> postResponse = this.restTemplate.postForEntity("/flashcards/Jack/fighters", null, String.class);

        //Check STATUS CODE.
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        //COUNT flashcard collections.
        long collectionCount = this.flashcardCollectionRepository.count();
        assertThat(collectionCount).isEqualTo(1L);
    }

    @Test
    void shouldNotCreateANewFlashcardCollectionWithNonExistingUser() {
        //Apply request.
        ResponseEntity<String> postResponse = this.restTemplate.postForEntity("/flashcards/someName/PokemonGo", null, String.class);

        //Check STATUS CODE.
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        //COUNT flashcard collections.
        long collectionCount = this.flashcardCollectionRepository.count();
        assertThat(collectionCount).isEqualTo(1L);
    }

    @Test
    void shouldDeleteAFlashcardCollection() {
        //Apply request.
        this.restTemplate.delete("/flashcards/Jack/fighters");

        //COUNT flashcard collections.
        long collectionCount = this.flashcardCollectionRepository.count();
        assertThat(collectionCount).isEqualTo(0L);
    }

    @Test
    void shouldNotDeleteAFlashcardCollectionWithNonExistingName() {
        //Apply request.
        this.restTemplate.delete("/flashcards/Jack/xddd");

        //COUNT flashcard collections.
        long collectionCount = this.flashcardCollectionRepository.count();
        assertThat(collectionCount).isEqualTo(1L);
    }

    @Test
    void shouldNotDeleteAFlashcardCollectionWithNonExistingUser() {
        //Apply request.
        this.restTemplate.delete("/flashcards/XDD/fighters");

        //COUNT flashcard collections.
        long collectionCount = this.flashcardCollectionRepository.count();
        assertThat(collectionCount).isEqualTo(1L);
    }

    @Test
    void shouldReturnAllOwnedFlashcardCollections() {
        //Apply request.
        ResponseEntity<String> getResponse = this.restTemplate.getForEntity("/flashcards/Jack", String.class);

        //Check STATUS CODE.
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());

        //COUNT collections.
        Number collectionCount = documentContext.read("$.length()");
        assertThat(collectionCount).isEqualTo(1);
    }

    @Test
    void shouldReturn0WhenUserDoesNotExist() {
        //Apply request.
        ResponseEntity<List> getResponse = this.restTemplate.getForEntity("/flashcards/XDDs", List.class);

        //Check STATUS CODE.
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());

        //COUNT collections.
        assertThat(getResponse.getBody().size()).isEqualTo(0);
    }
}
