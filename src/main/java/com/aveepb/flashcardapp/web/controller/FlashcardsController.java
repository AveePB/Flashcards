package com.aveepb.flashcardapp.web.controller;

import com.aveepb.flashcardapp.web.conn.flashcards.AddFlashcardRequest;
import com.aveepb.flashcardapp.web.conn.flashcards.RemoveFlashcardRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/flashcards")
@RequiredArgsConstructor
public class FlashcardsController {

    @GetMapping("/{username}")
    public ResponseEntity<String> fetchFlashcardsCollections(@PathVariable String username) {


        return ResponseEntity.ok("ENDPOINT: fetchFlashCardsCollections");
    }

    @GetMapping("/{username}/{collectionName}")
    public ResponseEntity<String> fetchFlashcardsCollection(@PathVariable String username, @PathVariable String collectionName) {


        return ResponseEntity.ok("ENDPOINT: fetchFlashcardsCollection");
    }

    @PostMapping("/{username}/{collectionName}")
    public ResponseEntity<String> createFlashcardsCollection(@PathVariable String username, @PathVariable String collectionName) {


        return ResponseEntity.ok("ENDPOINT: createFlashCardsCollection");
    }

    @DeleteMapping("/{username}/{collectionName}")
    public ResponseEntity<String> deleteFlashcardsCollection(@PathVariable String username, @PathVariable String collectionName) {


        return ResponseEntity.ok("ENDPOINT: deleteFlashCardsCollection");
    }

    @GetMapping("/{username}/{collectionName}/random")
    public ResponseEntity<String> fetchRandomFlashcard(@PathVariable String username, @PathVariable String collectionName) {


        return ResponseEntity.ok("ENDPOINT: fetchRandomFlashCard");
    }

    @PostMapping("/{username}/{collectionName}/add")
    public ResponseEntity<String> addFlashcard(@PathVariable String username, @PathVariable String collectionName, @RequestBody AddFlashcardRequest request) {


        return ResponseEntity.ok("ENDPOINT: addFlashCardToCollection");
    }

    @DeleteMapping("/{username}/{collectionName}/remove")
    public ResponseEntity<String> deleteFlashcard(@PathVariable String username, @PathVariable String collectionName, @RequestBody RemoveFlashcardRequest request) {


        return ResponseEntity.ok("ENDPOINT: deleteFlashCardFromCollection");
    }
}
