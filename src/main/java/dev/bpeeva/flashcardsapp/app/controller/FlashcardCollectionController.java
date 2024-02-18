package dev.bpeeva.flashcardsapp.app.controller;

import dev.bpeeva.flashcardsapp.app.service.controller.FlashcardCollectionCTService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/flashcards/{username}")
@RequiredArgsConstructor
public class FlashcardCollectionController {

    private final FlashcardCollectionCTService flashcardCollectionCTService;

    @PostMapping("/{collectionName}")
    private ResponseEntity<String> createFlashcardCollection(@PathVariable String username, @PathVariable String collectionName) {
        //Create flashcard collection.
        boolean isCreated = this.flashcardCollectionCTService.createFlashcardCollection(username, collectionName);

        //Operation success.
        if (isCreated)
            return ResponseEntity
                    .ok()
                    .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .build();
    }

    @DeleteMapping("/{collectionName}")
    private ResponseEntity<String> deleteFlashcardCollection(@PathVariable String username, @PathVariable String collectionName) {
        //Delete flashcard collection.
        this.flashcardCollectionCTService.deleteFlashcardCollection(username, collectionName);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping
    private ResponseEntity<List<String>> fetchOwnedFlashcardCollections(@PathVariable String username) {
        //Fetch owned flashcard collections.
        List<String> flashcardCollections = this.flashcardCollectionCTService.getAllFlashcardCollections(username);

        return ResponseEntity.ok(flashcardCollections);
    }
}
