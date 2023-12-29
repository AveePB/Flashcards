package com.aveepb.flashcardapp.app.flashcards;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/flashcards")
@RequiredArgsConstructor
public class FlashCardsController {

    @GetMapping("/{username}")
    public ResponseEntity<String> fetchFlashCardsCollections(@PathVariable String username) {


        return ResponseEntity.ok("ENDPOINT: fetchFlashCardsCollections");
    }

    @GetMapping("/{username}/{collectionName}")
    public ResponseEntity<String> fetchRandomFlashCard(@PathVariable String username, @PathVariable String collectionName) {


        return ResponseEntity.ok("ENDPOINT: fetchRandomFlashCard");
    }

    @PostMapping("/{username}/{collectionName}")
    public ResponseEntity<String> createFlashCardsCollection(@PathVariable String username, @PathVariable String collectionName) {


        return ResponseEntity.ok("ENDPOINT: createFlashCardsCollection");
    }

    @DeleteMapping("/{username}/{collectionName}")
    public ResponseEntity<String> deleteFlashCardsCollection(@PathVariable String username, @PathVariable String collectionName) {


        return ResponseEntity.ok("ENDPOINT: deleteFlashCardsCollection");
    }


    @PostMapping("/{username}/{collectionName}/add")
    public ResponseEntity<String> addFlashCardToCollection(@PathVariable String username, @PathVariable String collectionName, @RequestBody AddFlashCardRequest request) {


        return ResponseEntity.ok("ENDPOINT: addFlashCardToCollection");
    }

    @DeleteMapping("/{username}/{collectionName}/remove")
    public ResponseEntity<String> deleteFlashCardFromCollection(@PathVariable String username, @PathVariable String collectionName, @RequestBody RemoveFlashCardRequest request) {


        return ResponseEntity.ok("ENDPOINT: deleteFlashCardFromCollection");
    }
}
