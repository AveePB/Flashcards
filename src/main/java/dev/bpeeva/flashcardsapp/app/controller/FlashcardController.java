package dev.bpeeva.flashcardsapp.app.controller;

import dev.bpeeva.flashcardsapp.app.service.controller.FlashcardCTService;
import dev.bpeeva.flashcardsapp.util.dto.FlashcardDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/flashcards/{username}/{collectionName}")
@RequiredArgsConstructor
public class FlashcardController {

    private final FlashcardCTService flashcardCTService;

    @PostMapping("/create")
    private ResponseEntity<String> createFlashcard(@PathVariable String username, @PathVariable String collectionName, @RequestBody FlashcardDTO flashcardDTO) {
        //Create flashcard.
        boolean isCreated = this.flashcardCTService.createFlashcard(username, collectionName, flashcardDTO);

        //Operation success.
        if (isCreated)
            return ResponseEntity.ok().build();

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/delete")
    private ResponseEntity<String> deleteFlashcard(@PathVariable String username, @PathVariable String collectionName, @RequestBody FlashcardDTO flashcardDTO) {
        //Delete flashcard.
        this.flashcardCTService.deleteFlashcard(username, collectionName, flashcardDTO);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/random")
    private ResponseEntity<FlashcardDTO> fetchRandomFlashcard(@PathVariable String username, @PathVariable String collectionName) {
        //Fetch random flashcard.
        Optional<FlashcardDTO> randomFlashcard = this.flashcardCTService.getRandomFlashcard(username, collectionName);

        //Operation success.
        if (randomFlashcard.isPresent())
            return ResponseEntity.ok(randomFlashcard.get());

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/all")
    private ResponseEntity<List<FlashcardDTO>> fetchAllFlashcards(@PathVariable String username, @PathVariable String collectionName) {
        //Fetch flashcards.
        List<FlashcardDTO> flashcardList = this.flashcardCTService.getAllFlashcards(username, collectionName);

        return ResponseEntity.ok(flashcardList);
    }
}
