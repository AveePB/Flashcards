package dev.bpeeva.flashcardsapp.app.service.controller;

import dev.bpeeva.flashcardsapp.app.service.model.FlashcardCollectionService;
import dev.bpeeva.flashcardsapp.app.service.model.UserService;
import dev.bpeeva.flashcardsapp.db.model.FlashcardCollection;
import dev.bpeeva.flashcardsapp.db.model.User;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlashcardCollectionCTService {

    private final UserService userService;
    private final FlashcardCollectionService flashcardCollectionService;

    /**
     * Tries to insert a new collection into the database.
     * @param username the name of a collection owner.
     * @param collectionName the flashcard collection name.
     * @return the boolean value.
     */
    public boolean createFlashcardCollection(String username, String collectionName) {
        //Fetch user object.
        Optional<User> user = this.userService.getUser(username);

        //User isn't present.
        if (user.isEmpty())
            return false;

        //Create flashcard collection.
        Optional<FlashcardCollection> flashcardCollection = this.flashcardCollectionService.createFlashcardCollection(user.get(), collectionName);

        return flashcardCollection.isPresent();
    }

    /**
     * Tries to delete an existing collection from the database.
     * @param username the name of a collection owner.
     * @param collectionName the flashcard collection name.
     * @return the boolean value.
     */
    public boolean deleteFlashcardCollection(String username, String collectionName) {
        //Fetch user object.
        Optional<User> user = this.userService.getUser(username);

        //User isn't present.
        if (user.isEmpty())
            return false;

        //Flashcard collection doesn't exist.
        if (!this.flashcardCollectionService.isFlashcardCollectionNameTaken(user.get(), collectionName))
            return false;

        //Delete flashcard collection.
        this.flashcardCollectionService.deleteFlashcardCollection(user.get(), collectionName);

        return !(this.flashcardCollectionService.isFlashcardCollectionNameTaken(user.get(), collectionName));
    }

    /**
     * Fetches all flashcard collections owned by the user.
     * @param username the name of a user.
     * @return the list of collection names.
     */
    public List<String> getAllFlashcardCollections(String username) {
        //Fetch user object.
        Optional<User> user = this.userService.getUser(username);

        //User isn't present.
        if (user.isEmpty())
            return new ArrayList<>();

        //Get all collection names.
        return new ArrayList<>(this.flashcardCollectionService.getFlashcardCollections(user.get()).stream()
                .filter(col -> col.getId() != null)
                .map(FlashcardCollection::getName)
                .toList());
    }
}
