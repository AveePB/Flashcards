package com.dev.app.api.controller;

import com.dev.app.api.config.SecurityConfig;
import com.dev.app.bll.manager.JWTManager;
import com.dev.app.db.model.FlashcardCollection;
import com.dev.app.db.model.User;
import com.dev.app.db.repo.FlashcardCollectionRepository;
import com.dev.app.db.repo.UserRepository;
import com.dev.app.util.dto.FlashcardCollectionDTO;
import com.dev.app.util.token.JsonWebToken;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FlashcardCollectionControllerTests {

    private static final String AUTHORIZATION = "Authorization";
    private static final String FLASHCARDS_URL = "/flashcards";

    @Autowired
    private TestRestTemplate restTemplate = null;
    @Autowired
    private PasswordEncoder passwordEncoder = null;

    @Autowired
    private final UserRepository userRepo = null;
    @Autowired
    private final FlashcardCollectionRepository flashcardCollectionRepo = null;

    @Autowired
    private final JWTManager jwtManager = null;

    private String jwtInStringForm;

    @BeforeEach
    void setUpDB() {
        //Arrange
        User henry = userRepo.save(new User(null, "Henry", passwordEncoder.encode("(SYFSDY("), null));
        userRepo.save(new User(null, "Jacek", passwordEncoder.encode("(SYFSDYHSFD*STF("), null));

        flashcardCollectionRepo.save(new FlashcardCollection(null, "Colors", null, henry));

        JsonWebToken jwt = jwtManager.generateJWT("Henry", SecurityConfig.SECRET_KEY_256_BIT).get();
        jwtInStringForm = "Bearer " + jwt.bearer();
    }

    @Test
    void shouldCreateANewCollection() {
        //Arrange
        FlashcardCollectionDTO requestBody = new FlashcardCollectionDTO("Games", "Henry");

        MultiValueMap<String, String> requestHeaders = new HttpHeaders();
        requestHeaders.put(AUTHORIZATION, List.of(jwtInStringForm));

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL)
                .build()
                .toUri();

        RequestEntity<FlashcardCollectionDTO> POSTRequest = new RequestEntity<>(requestBody, requestHeaders, HttpMethod.POST, requestURL);

        //Act
        ResponseEntity<URI> response = restTemplate.exchange(POSTRequest, URI.class);
        URI uri = response.getBody();

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(uri).isNotNull();
    }

    @Test
    void shouldFailToCreateANewCollectionBecauseOfALackingJWT() {
        //Arrange
        FlashcardCollectionDTO requestBody = new FlashcardCollectionDTO("Fruits", "Henry");

        //Act
        ResponseEntity<URI> response = restTemplate.postForEntity(FLASHCARDS_URL, requestBody, URI.class);

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldFailToCreateANewCollectionBecauseOfDifferingUsernamesInBodyAndJWT() {
        //Arrange
        FlashcardCollectionDTO requestBody = new FlashcardCollectionDTO("Games", "Jacek");

        MultiValueMap<String, String> requestHeaders = new HttpHeaders();
        requestHeaders.put(AUTHORIZATION, List.of(jwtInStringForm));

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL)
                .build()
                .toUri();

        RequestEntity<FlashcardCollectionDTO> POSTRequest = new RequestEntity<>(requestBody, requestHeaders, HttpMethod.POST, requestURL);

        //Act
        ResponseEntity<URI> response = restTemplate.exchange(POSTRequest, URI.class);

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldSuccessfullyDeleteACollection() {
        //Arrange
        FlashcardCollectionDTO requestBody = new FlashcardCollectionDTO("Colors", "Henry");

        MultiValueMap<String, String> requestHeaders = new HttpHeaders();
        requestHeaders.put(AUTHORIZATION, List.of(jwtInStringForm));

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL)
                .build()
                .toUri();

        RequestEntity<FlashcardCollectionDTO> DELETERequest = new RequestEntity<>(requestBody, requestHeaders, HttpMethod.DELETE, requestURL);

        //Act
        ResponseEntity<URI> response = restTemplate.exchange(DELETERequest, URI.class);

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldTryToDeleteACollection() {
        //Arrange
        FlashcardCollectionDTO requestBody = new FlashcardCollectionDTO("Computer Games", "Henry");

        MultiValueMap<String, String> requestHeaders = new HttpHeaders();
        requestHeaders.put(AUTHORIZATION, List.of(jwtInStringForm));

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL)
                .build()
                .toUri();

        RequestEntity<FlashcardCollectionDTO> DELETERequest = new RequestEntity<>(requestBody, requestHeaders, HttpMethod.DELETE, requestURL);

        //Act
        ResponseEntity<URI> response = restTemplate.exchange(DELETERequest, URI.class);

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldFailToDeleteACollectionBecauseOfALackingJWT() {
        //Arrange
        FlashcardCollectionDTO requestBody = new FlashcardCollectionDTO("Colors", "Henry");

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL)
                .build()
                .toUri();

        RequestEntity<FlashcardCollectionDTO> DELETERequest = new RequestEntity<>(requestBody, HttpMethod.DELETE, requestURL);

        //Act
        ResponseEntity<URI> response = restTemplate.exchange(DELETERequest, URI.class);

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldFailToDeleteACollectionBecauseOfDifferingUsernamesInBodyAndJWT() {
        //Arrange
        FlashcardCollectionDTO requestBody = new FlashcardCollectionDTO("Computer Games", "Jack Sparrow");

        MultiValueMap<String, String> requestHeaders = new HttpHeaders();
        requestHeaders.put(AUTHORIZATION, List.of(jwtInStringForm));

        URI requestURL = UriComponentsBuilder.newInstance()
                .path(FLASHCARDS_URL)
                .build()
                .toUri();

        RequestEntity<FlashcardCollectionDTO> DELETERequest = new RequestEntity<>(requestBody, requestHeaders, HttpMethod.DELETE, requestURL);

        //Act
        ResponseEntity<URI> response = restTemplate.exchange(DELETERequest, URI.class);

        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
