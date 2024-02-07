package dev.bpeeva.flashcardsapp.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import dev.bpeeva.flashcardsapp.db.constant.UserRole;
import dev.bpeeva.flashcardsapp.db.constant.WordType;
import dev.bpeeva.flashcardsapp.db.model.Flashcard;
import dev.bpeeva.flashcardsapp.db.model.FlashcardCollection;
import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.db.repo.FlashcardCollectionRepository;
import dev.bpeeva.flashcardsapp.db.repo.FlashcardRepository;
import dev.bpeeva.flashcardsapp.db.repo.UserRepository;
import dev.bpeeva.flashcardsapp.util.dto.FlashcardDTO;
import dev.bpeeva.flashcardsapp.util.mapper.FlashcardDTOMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FlashcardControllerTests {

    @Autowired
    private final UserRepository userRepository = null;
    @Autowired
    private final FlashcardCollectionRepository flashcardCollectionRepository = null;
    @Autowired
    private final FlashcardRepository flashcardRepository = null;

    @Autowired
    private final TestRestTemplate restTemplate = null;

    private String username, collectionName;
    private Flashcard flashcard;

    @BeforeEach
    void setUp() {
        //Create required variables.
        this.username = "J4ck";
        this.collectionName = "Verbs";

        //Save to repo.
        User user = this.userRepository.save(new User(null, UserRole.USER, this.username, "", null));
        FlashcardCollection flashcardCollection = this.flashcardCollectionRepository.save(new FlashcardCollection(null, this.collectionName, null, user));
        this.flashcard  = this.flashcardRepository.save(new Flashcard(null, WordType.VERB, "Go", "Isc", flashcardCollection));
    }

    @Test
    void shouldCreateANewFlashcard() {
        //Prepare request data.
        String url = "/flashcards/" + this.username + "/" + this.collectionName + "/create";
        FlashcardDTO flashcardDTO = new FlashcardDTO(WordType.VERB, "Eat", "Jesc");

        //Apply request.
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, flashcardDTO, String.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldNotCreateANewFlashcardWithNonExistingCollection() {
        //Prepare request data.
        String url = "/flashcards/" + this.username + "/RandomCollectionName/create";
        FlashcardDTO flashcardDTO = new FlashcardDTO(WordType.VERB, "Eat", "Jesc");

        //Apply request.
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, flashcardDTO, String.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldNotCreateANewFlashcardWithNonExistingUser() {
        //Prepare request data.
        String url = "/flashcards/RandomUsername/" + this.collectionName + "/create";
        FlashcardDTO flashcardDTO = new FlashcardDTO(WordType.VERB, "Eat", "Jesc");

        //Apply request.
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, flashcardDTO, String.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldNotCreateANewFlashcardWithInvalidRequestBody() {
        //Prepare request data.
        String url = "/flashcards/" + this.username + "/" + this.collectionName + "/create";
        FlashcardDTO flashcardDTO = new FlashcardDTO(WordType.VERB, null, "Jesc");

        //Apply request.
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, flashcardDTO, String.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldDeleteAnExistingFlashcard() {
        //Prepare request data.
        String url = "/flashcards/" + this.username + "/" + this.collectionName + "/delete";
        FlashcardDTO flashcardDTO = new FlashcardDTOMapper().apply(this.flashcard).get();
        RequestEntity<FlashcardDTO> deleteRequest = new RequestEntity<>(flashcardDTO, HttpMethod.DELETE, URI.create(url));

        //Apply request.
        ResponseEntity<String> response = this.restTemplate.exchange(deleteRequest, String.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        //Check FLASHCARD COUNT.
        assertThat(this.flashcardRepository.count()).isEqualTo(0L);
    }

    @Test
    void shouldReturn204WhenTryingToDeleteANonExistingFlashcard() {
        //Prepare request data.
        String url = "/flashcards/" + this.username + "/" + this.collectionName + "/delete";
        FlashcardDTO flashcardDTO = new FlashcardDTO(WordType.VERB, "Cook", "Gotowac");
        RequestEntity<FlashcardDTO> deleteRequest = new RequestEntity<>(flashcardDTO, HttpMethod.DELETE, URI.create(url));

        //Apply request.
        ResponseEntity<String> response = this.restTemplate.exchange(deleteRequest, String.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        //Check FLASHCARD COUNT.
        assertThat(this.flashcardRepository.count()).isEqualTo(1L);
    }

    @Test
    void shouldReturn204WhenTryingToDeleteAFlashcardWithNonExistingUser() {
        //Prepare request data.
        String url = "/flashcards/RandomUserName/" + this.collectionName + "/delete";
        FlashcardDTO flashcardDTO = new FlashcardDTO(WordType.VERB, "Cook", "Gotowac");
        RequestEntity<FlashcardDTO> deleteRequest = new RequestEntity<>(flashcardDTO, HttpMethod.DELETE, URI.create(url));

        //Apply request.
        ResponseEntity<String> response = this.restTemplate.exchange(deleteRequest, String.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        //Check FLASHCARD COUNT.
        assertThat(this.flashcardRepository.count()).isEqualTo(1L);
    }

    @Test
    void shouldReturn204WhenTryingToDeleteAFlashcardWithNonExistingCollection() {
        //Prepare request data.
        String url = "/flashcards/" + this.username + "/RandomCollectionName/delete";
        FlashcardDTO flashcardDTO = new FlashcardDTO(WordType.VERB, "Cook", "Gotowac");
        RequestEntity<FlashcardDTO> deleteRequest = new RequestEntity<>(flashcardDTO, HttpMethod.DELETE, URI.create(url));

        //Apply request.
        ResponseEntity<String> response = this.restTemplate.exchange(deleteRequest, String.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        //Check FLASHCARD COUNT.
        assertThat(this.flashcardRepository.count()).isEqualTo(1L);
    }

    @Test
    void shouldReturnARandomFlashcard() {
        //Prepare request data.
        String url = "/flashcards/" + this.username + "/" + this.collectionName + "/random";

        //Apply request.
        ResponseEntity<FlashcardDTO> response = this.restTemplate.getForEntity(url, FlashcardDTO.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Check WORD TYPE.
        assertThat(response.getBody().wordType()).isEqualTo(this.flashcard.getWordType());

        //Check ENGLISH MEANING.
        assertThat(response.getBody().englishWord()).isEqualTo(this.flashcard.getEnglishWord());

        //Check POLISH MEANING.
        assertThat(response.getBody().polishWord()).isEqualTo(this.flashcard.getPolishWord());
    }

    @Test
    void shouldNotReturnARandomFlashcardWithNonExistingUser() {
        //Prepare request data.
        String url = "/flashcards/RandomUsername/" + this.collectionName + "/random";

        //Apply request.
        ResponseEntity<FlashcardDTO> response = this.restTemplate.getForEntity(url, FlashcardDTO.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldNotReturnARandomFlashcardWithNonExistingCollection() {
        //Prepare request data.
        String url = "/flashcards/" + this.username + "/RandomCollectionName/random";

        //Apply request.
        ResponseEntity<FlashcardDTO> response = this.restTemplate.getForEntity(url, FlashcardDTO.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldReturnAllFlashcardsInTheCollection() {
        //Prepare request data.
        String url = "/flashcards/" + this.username + "/" + this.collectionName + "/all";

        //Apply request.
        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Check LIST SIZE.
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number len = documentContext.read("$.length()");
        assertThat(len).isEqualTo(1);
    }

    @Test
    void shouldReturnNoFlashcardsWithNonExistingUser() {
        //Prepare request data.
        String url = "/flashcards/RandomUserName/" + this.collectionName + "/all";

        //Apply request.
        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Check LIST SIZE.
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number len = documentContext.read("$.length()");
        assertThat(len).isEqualTo(0);
    }

    @Test
    void shouldReturnNoFlashcardsWithNonExistingCollection() {
        //Prepare request data.
        String url = "/flashcards/" + this.username + "/RandomCollectionName/all";

        //Apply request.
        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);

        //Check STATUS CODE.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Check LIST SIZE.
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number len = documentContext.read("$.length()");
        assertThat(len).isEqualTo(0);
    }
}
