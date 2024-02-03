package dev.bpeeva.flashcardsapp.app.service.controller;

import dev.bpeeva.flashcardsapp.app.service.model.FlashcardCollectionService;
import dev.bpeeva.flashcardsapp.app.service.model.FlashcardService;
import dev.bpeeva.flashcardsapp.app.service.model.UserService;
import dev.bpeeva.flashcardsapp.db.model.Flashcard;
import dev.bpeeva.flashcardsapp.db.model.FlashcardCollection;
import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.util.dto.FlashcardDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlashcardCTService {

    private final UserService userService;
    private final FlashcardCollectionService flashcardCollectionService;
    private final FlashcardService flashcardService;

    /**
     * Tries to insert a new flashcard object into the database.
     * @param username the name of a collection owner.
     * @param collectionName the flashcard collection name.
     * @param flashcardDTO the flashcard data transfer object.
     * @return the optional object.
     */
    public boolean createFlashcard(String username, String collectionName, FlashcardDTO flashcardDTO) {
        //Fetch user object.
        Optional<User> user = this.userService.getUser(username);

        //Fetch flashcard collection object.
        Optional<FlashcardCollection> flashcardCollection = this.flashcardCollectionService.getFlashcardCollection(user.get(), collectionName);

        //Collection isn't present.
        if (flashcardCollection.isEmpty())
            return false;

        //Try to create new flashcard.
        Optional<Flashcard> flashcard = this.flashcardService.createFlashcard(flashcardCollection.get(), flashcardDTO);

        return flashcard.isPresent();
    }

    /**
     * Tries to delete a flashcard object from the database.
     * @param username the name of a collection owner.
     * @param collectionName the flashcard collection name.
     * @param flashcardDTO the flashcard data transfer object.
     * @return the optional object.
     */
    public boolean deleteFlashcard(String username, String collectionName, FlashcardDTO flashcardDTO) {
        //Fetch user object.
        Optional<User> user = this.userService.getUser(username);

        //Fetch flashcard collection object.
        Optional<FlashcardCollection> flashcardCollection = this.flashcardCollectionService.getFlashcardCollection(user.get(), collectionName);

        //Collection isn't present.
        if (flashcardCollection.isEmpty())
            return false;

        //Fetch collection size before deletion.
        int sizeBeforeDeletion = this.flashcardService.getFlashcards(flashcardCollection.get()).size();

        //Delete flashcard.
        this.flashcardService.deleteFlashcard(flashcardCollection.get(), flashcardDTO);

        //Fetch collection size after deletion.
        int sizeAfterDeletion = this.flashcardService.getFlashcards(flashcardCollection.get()).size();

        return (sizeBeforeDeletion != sizeAfterDeletion);
    }
}
