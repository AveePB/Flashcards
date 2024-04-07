package com.dev.app.api.controller;

import com.dev.app.api.config.SecurityConfig;
import com.dev.app.bll.handler.FlashcardCollectionHandler;
import com.dev.app.bll.handler.HandlerException;
import com.dev.app.bll.manager.JWTManager;
import com.dev.app.util.dto.FlashcardCollectionDTO;
import com.dev.app.util.token.JsonWebToken;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequestMapping("/collections")
@RequiredArgsConstructor
public class FlashcardCollectionController {

    private static final String AUTHORIZATION = "Authorization";
    private static final String PREFIX = "Bearer ";

    private final FlashcardCollectionHandler flashcardCollectionHandler;
    private final JWTManager jwtManager;

    private boolean isUserApproved(RequestEntity<FlashcardCollectionDTO> request) {
        String bearer = request.getHeaders().get(AUTHORIZATION).get(0).substring(PREFIX.length());
        JsonWebToken jwt = new JsonWebToken(bearer);

        FlashcardCollectionDTO dto = request.getBody();

        return jwtManager.validate(jwt, SecurityConfig.SECRET_KEY_256_BIT, dto.ownerNickname());
    }

    @PostMapping
    private ResponseEntity<URI> createFlashcardCollection(RequestEntity<FlashcardCollectionDTO> request) throws HandlerException {
        if (isUserApproved(request))
            return ResponseEntity.ok(flashcardCollectionHandler.createNewFlashcardCollection(request.getBody()));

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping
    private ResponseEntity<String> deleteFlashcardCollection(RequestEntity<FlashcardCollectionDTO> request) throws HandlerException {
        if (isUserApproved(request)) {
            flashcardCollectionHandler.deleteFlashcardCollection(request.getBody());

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
