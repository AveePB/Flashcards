package dev.bpeeva.flashcardsapp.app.controller;

import dev.bpeeva.flashcardsapp.db.model.Flashcard;
import dev.bpeeva.flashcardsapp.util.dto.FlashcardDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("flashcards/{username}/{collectionName}")
@RequiredArgsConstructor
public class FlashcardController {

    @PostMapping("/create")
    private ResponseEntity<String> createFlashcard(@PathVariable String username, @PathVariable String collectionName, @RequestBody FlashcardDTO flashcardDTO) {

        return null;
    }

    @DeleteMapping("/delete")
    private ResponseEntity<String> deleteFlashcard(@PathVariable String username, @PathVariable String collectionName, @RequestBody FlashcardDTO flashcardDTO) {

        return null;
    }

    @GetMapping("/random")
    private ResponseEntity<Flashcard> fetchRandomFlashcard(@PathVariable String username, @PathVariable String collectionName) {

        return null;
    }

    @GetMapping("/all")
    private ResponseEntity<Flashcard> fetchAllFlashcards(@PathVariable String username, @PathVariable String collectionName) {

        return null;
    }
}
