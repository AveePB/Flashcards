package com.aveepb.flashcardapp.controller;

import com.aveepb.flashcardapp.web.conn.auth.request.AuthRequest;
import com.aveepb.flashcardapp.web.conn.auth.response.AuthResponse;

import com.aveepb.flashcardapp.web.service.UserService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthControllerTests {

    @Autowired
    private final TestRestTemplate restTemplate = null;

    @Autowired
    private final UserService userService = null;

    @Test
    void shouldSignUpANewUser() {

        AuthRequest authRequest = AuthRequest.builder()
                .username("J4ck")
                .password("xDF231")
                .build();

        //Send request.
        ResponseEntity<AuthResponse> response = this.restTemplate.postForEntity("/auth/sign-up", authRequest, AuthResponse.class);

        //Check the status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Check if the repository contains the user.
        UserDetails user = this.userService.loadUserByUsername(authRequest.getUsername());
        assertThat(user.getUsername()).isEqualTo("J4ck");
    }

    @Test
    void shouldNotSignUpAUserWithTakenName() {

        AuthRequest authRequest = AuthRequest.builder()
                .username("Jonathan")
                .password("12312312fdsa")
                .build();

        //Send requests.
        this.restTemplate.postForEntity("/auth/sign-up", authRequest, AuthResponse.class);
        ResponseEntity<AuthResponse> response = this.restTemplate.postForEntity("/auth/sign-up", authRequest, AuthResponse.class);

        //Check the status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        //Check if the repository contains the user.
        UserDetails user = this.userService.loadUserByUsername(authRequest.getUsername());
        assertThat(user.getUsername()).isEqualTo("Jonathan");
    }

    @Test
    void shouldLogInAUser() {

        AuthRequest authRequest = AuthRequest.builder()
                .username("Kasia")
                .password("123@#FSXDe_fdsa")
                .build();

        //Send requests.
        this.restTemplate.postForEntity("/auth/sign-up", authRequest, AuthResponse.class);
        ResponseEntity<AuthResponse> response = this.restTemplate.postForEntity("/auth/log-in", authRequest, AuthResponse.class);

        //Check the status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldNotLogInAUserWithIncorrectData() {

        AuthRequest signUpRequest = AuthRequest.builder()
                .username("S3ba")
                .password("13w4axdsa")
                .build();

        AuthRequest logInRequest = AuthRequest.builder()
                .username("Fr4nk")
                .password("13w4axdsa")
                .build();

        //Send requests.
        this.restTemplate.postForEntity("/auth/sign-up", signUpRequest, AuthResponse.class);
        ResponseEntity<AuthResponse> response = this.restTemplate.postForEntity("/auth/log-in", logInRequest, AuthResponse.class);

        //Check the status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
