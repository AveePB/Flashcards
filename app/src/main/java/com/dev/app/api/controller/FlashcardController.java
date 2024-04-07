package com.dev.app.api.controller;

import com.dev.app.api.config.SecurityConfig;
import com.dev.app.bll.handler.FlashcardHandler;
import com.dev.app.bll.handler.HandlerException;
import com.dev.app.bll.manager.JWTManager;
import com.dev.app.util.dto.FlashcardCollectionDTO;
import com.dev.app.util.dto.FlashcardDTO;
import com.dev.app.util.token.JsonWebToken;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@Controller
@RequestMapping("/flashcards")
@RequiredArgsConstructor
public class FlashcardController {

    private static final String AUTHORIZATION = "Authorization";
    private static final String PREFIX = "Bearer ";

    private final FlashcardHandler flashcardHandler;
    private final JWTManager jwtManager;

    private boolean isUserApproved(String bearer, String username) {
        JsonWebToken jwt = new JsonWebToken(bearer);

        return jwtManager.validate(jwt, SecurityConfig.SECRET_KEY_256_BIT, username);
    }

    @GetMapping("/{ownerNickname}/{collectionName}")
    private ResponseEntity<FlashcardDTO> getRandomFlashcard(RequestEntity<String> request, @PathVariable String ownerNickname, @PathVariable String collectionName) throws HandlerException {
        String bearer = request.getHeaders().get(AUTHORIZATION).get(0).substring(PREFIX.length());
        FlashcardCollectionDTO dto = new FlashcardCollectionDTO(collectionName, ownerNickname);

        if (isUserApproved(bearer, ownerNickname)) {
            Optional<FlashcardDTO> flashcardDTO = flashcardHandler.getRandomFlashcard(dto);

            return flashcardDTO
                    .map(ResponseEntity::ok)
                    .orElseGet(
                            () -> ResponseEntity.noContent().build()
                    );
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping
    private ResponseEntity<URI> createFlashcard(RequestEntity<FlashcardDTO> request) throws HandlerException {
        String bearer = request.getHeaders().get(AUTHORIZATION).get(0).substring(PREFIX.length());
        FlashcardDTO dto = request.getBody();

        if (isUserApproved(bearer, dto.ownerNickname()))
            return ResponseEntity.ok(flashcardHandler.createNewFlashcard(dto));

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping
    private ResponseEntity<String> deleteFlashcard(RequestEntity<FlashcardDTO> request) throws HandlerException {
        String bearer = request.getHeaders().get(AUTHORIZATION).get(0).substring(PREFIX.length());
        FlashcardDTO dto = request.getBody();

        if (isUserApproved(bearer, dto.ownerNickname())) {
            flashcardHandler.deleteFlashcard(dto);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
