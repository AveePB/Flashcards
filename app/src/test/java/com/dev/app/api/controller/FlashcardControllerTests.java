package com.dev.app.api.controller;

import com.dev.app.api.config.SecurityConfig;
import com.dev.app.bll.manager.JWTManager;
import com.dev.app.db.model.Flashcard;
import com.dev.app.db.model.FlashcardCollection;
import com.dev.app.db.model.User;
import com.dev.app.db.repo.FlashcardCollectionRepository;
import com.dev.app.db.repo.FlashcardRepository;
import com.dev.app.db.repo.UserRepository;
import com.dev.app.util.dto.FlashcardCollectionDTO;
import com.dev.app.util.dto.FlashcardDTO;
import com.dev.app.util.token.JsonWebToken;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FlashcardControllerTests {

    private static final String AUTHORIZATION = "Authorization";
    private static final String FLASHCARDS_URL = "/flashcards";

    @Autowired
    private TestRestTemplate restTemplate = null;
    @Autowired
    private PasswordEncoder passwordEncoder = null;

    @Autowired
    private final UserRepository userRepo = null;
    @Autowired
    private final FlashcardCollectionRepository collectionRepo = null;
    @Autowired
    private final FlashcardRepository flashcardRepo = null;

    @Autowired
    private final JWTManager jwtManager = null;

    private String jwtInStringForm;

    @BeforeEach
    void setUpDB() {
        //Arrange
        User keanu = userRepo.save(new User(null, "Keanu", passwordEncoder.encode("FNW*F&EG)FWE"), null));

        FlashcardCollection tools = collectionRepo.save(new FlashcardCollection(null, "Tools", null, keanu));
        flashcardRepo.save(new Flashcard(null, "Nożyczki", "Scissors", tools));

        FlashcardCollection views = collectionRepo.save(new FlashcardCollection(null, "Views", null, keanu));

        JsonWebToken jwt = jwtManager.generateJWT("Keanu", SecurityConfig.SECRET_KEY_256_BIT).get();
        jwtInStringForm = "Bearer " + jwt.bearer();
    }

    @Test
    void shouldSuccessfullyGetARandomFlashcard() {
        //Arrange
        MultiValueMap<String, String> requestHeaders = new HttpHeaders();
        requestHeaders.put(AUTHORIZATION, List.of(jwtInStringForm));

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL + "/Keanu/Tools")
                .build()
                .toUri();

        RequestEntity<FlashcardCollectionDTO> GETRequest = new RequestEntity<>(requestHeaders, HttpMethod.GET, requestURL);

        //Act
        ResponseEntity<FlashcardDTO> response = restTemplate.exchange(GETRequest, FlashcardDTO.class);
        FlashcardDTO flashcardDTO = response.getBody();

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(flashcardDTO).isNotNull();
    }

    @Test
    void shouldTryToGetARandomFlashcardFromAnEmptyCollection() {
        //Arrange
        MultiValueMap<String, String> requestHeaders = new HttpHeaders();
        requestHeaders.put(AUTHORIZATION, List.of(jwtInStringForm));

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL + "/Keanu/Views")
                .build()
                .toUri();

        RequestEntity<FlashcardCollectionDTO> GETRequest = new RequestEntity<>(requestHeaders, HttpMethod.GET, requestURL);

        //Act
        ResponseEntity<FlashcardDTO> response = restTemplate.exchange(GETRequest, FlashcardDTO.class);

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldFailToGetARandomFlashcardBecauseOfANonExistingCollection() {
        //Arrange
        MultiValueMap<String, String> requestHeaders = new HttpHeaders();
        requestHeaders.put(AUTHORIZATION, List.of(jwtInStringForm));

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL + "/Keanu/Animals")
                .build()
                .toUri();

        RequestEntity<FlashcardCollectionDTO> GETRequest = new RequestEntity<>(requestHeaders, HttpMethod.GET, requestURL);

        //Act
        ResponseEntity<FlashcardDTO> response = restTemplate.exchange(GETRequest, FlashcardDTO.class);

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldFailToGetARandomFlashcardBecauseOfALackingJWT() {
        //Arrange
        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL + "/Keanu/Tools")
                .build()
                .toUri();

        RequestEntity<FlashcardCollectionDTO> GETRequest = new RequestEntity<>(HttpMethod.GET, requestURL);

        //Act
        ResponseEntity<FlashcardDTO> response = restTemplate.exchange(GETRequest, FlashcardDTO.class);

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldFailToGetAFlashcardBecauseOfNotMatchingUsernamesInJWTAndURL() {
        //Arrange
        MultiValueMap<String, String> requestHeaders = new HttpHeaders();
        requestHeaders.put(AUTHORIZATION, List.of(jwtInStringForm));

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL + "/Jack/Tools")
                .build()
                .toUri();

        RequestEntity<FlashcardCollectionDTO> GETRequest = new RequestEntity<>(requestHeaders, HttpMethod.GET, requestURL);

        //Act
        ResponseEntity<FlashcardDTO> response = restTemplate.exchange(GETRequest, FlashcardDTO.class);

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldSuccessfullyCreateAFlashcard() {
        //Arrange
        FlashcardDTO requestBody = new FlashcardDTO("Młot", "A hammer", "Tools", "Keanu");
        MultiValueMap<String, String> requestHeaders = new HttpHeaders();
        requestHeaders.put(AUTHORIZATION, List.of(jwtInStringForm));

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL)
                .build()
                .toUri();

        RequestEntity<FlashcardDTO> POSTRequest = new RequestEntity<>(requestBody, requestHeaders, HttpMethod.POST, requestURL);

        //Act
        ResponseEntity<URI> response = restTemplate.exchange(POSTRequest, URI.class);
        URI uri = response.getBody();

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(uri).isNotNull();
    }

    @Test
    void shouldTryToCreateAFlashcard() {
        //Arrange
        FlashcardDTO requestBody = new FlashcardDTO("Nożyczki", "Scissors", "Tools", "Keanu");
        MultiValueMap<String, String> requestHeaders = new HttpHeaders();
        requestHeaders.put(AUTHORIZATION, List.of(jwtInStringForm));

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL)
                .build()
                .toUri();

        RequestEntity<FlashcardDTO> POSTRequest = new RequestEntity<>(requestBody, requestHeaders, HttpMethod.POST, requestURL);

        //Act
        ResponseEntity<URI> response = restTemplate.exchange(POSTRequest, URI.class);
        URI uri = response.getBody();

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(uri).isNotNull();
    }

    @Test
    void shouldFailToCreateAFlashcardBecauseOfALackingJWT() {
        //Arrange
        FlashcardDTO requestBody = new FlashcardDTO("Młot", "A hammer", "Tools", "Keanu");

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL)
                .build()
                .toUri();

        RequestEntity<FlashcardDTO> POSTRequest = new RequestEntity<>(requestBody, HttpMethod.POST, requestURL);

        //Act
        ResponseEntity<URI> response = restTemplate.exchange(POSTRequest, URI.class);

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldFailToCreateAFlashcardBecauseOfNotMatchingUsernamesInJWTAndDTO() {
        //Arrange
        FlashcardDTO requestBody = new FlashcardDTO("Młot", "A hammer", "Tools", "Jackie Chan");
        MultiValueMap<String, String> requestHeaders = new HttpHeaders();
        requestHeaders.put(AUTHORIZATION, List.of(jwtInStringForm));

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL)
                .build()
                .toUri();

        RequestEntity<FlashcardDTO> POSTRequest = new RequestEntity<>(requestBody, requestHeaders, HttpMethod.POST, requestURL);

        //Act
        ResponseEntity<URI> response = restTemplate.exchange(POSTRequest, URI.class);

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldSuccessfullyDeleteAFlashcard() {
        //Arrange
        FlashcardDTO requestBody = new FlashcardDTO("Nożyczki", "Scissors", "Tools", "Keanu");
        MultiValueMap<String, String> requestHeaders = new HttpHeaders();
        requestHeaders.put(AUTHORIZATION, List.of(jwtInStringForm));

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL)
                .build()
                .toUri();

        RequestEntity<FlashcardDTO> DELETERequest = new RequestEntity<>(requestBody, requestHeaders, HttpMethod.DELETE, requestURL);

        //Act
        ResponseEntity<String> response = restTemplate.exchange(DELETERequest, String.class);

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldTryToDeleteANonExistingFlashcard() {
        //Arrange
        FlashcardDTO requestBody = new FlashcardDTO("Młot", "A hammer", "Tools", "Keanu");
        MultiValueMap<String, String> requestHeaders = new HttpHeaders();
        requestHeaders.put(AUTHORIZATION, List.of(jwtInStringForm));

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL)
                .build()
                .toUri();

        RequestEntity<FlashcardDTO> DELETERequest = new RequestEntity<>(requestBody, requestHeaders, HttpMethod.DELETE, requestURL);

        //Act
        ResponseEntity<String> response = restTemplate.exchange(DELETERequest, String.class);

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldFailToDeleteAFlashcardBecauseOfALackingJWT() {
        //Arrange
        FlashcardDTO requestBody = new FlashcardDTO("Młot", "A hammer", "Tools", "Keanu");

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL)
                .build()
                .toUri();

        RequestEntity<FlashcardDTO> DELETERequest = new RequestEntity<>(requestBody, HttpMethod.DELETE, requestURL);

        //Act
        ResponseEntity<String> response = restTemplate.exchange(DELETERequest, String.class);

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldFailToDeleteAFlashcardBecauseOfNotMatchingUsernamesInJWTAndDTO() {
        //Arrange
        FlashcardDTO requestBody = new FlashcardDTO("Młot", "A hammer", "Tools", "Adam");
        MultiValueMap<String, String> requestHeaders = new HttpHeaders();
        requestHeaders.put(AUTHORIZATION, List.of(jwtInStringForm));

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL)
                .build()
                .toUri();

        RequestEntity<FlashcardDTO> DELETERequest = new RequestEntity<>(requestBody, requestHeaders, HttpMethod.DELETE, requestURL);

        //Act
        ResponseEntity<String> response = restTemplate.exchange(DELETERequest, String.class);

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
