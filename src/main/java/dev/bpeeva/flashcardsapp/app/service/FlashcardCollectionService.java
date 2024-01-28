package dev.bpeeva.flashcardsapp.app.service;

import dev.bpeeva.flashcardsapp.db.model.FlashcardCollection;
import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.db.repo.FlashcardCollectionRepository;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlashcardCollectionService {

    private final FlashcardCollectionRepository flashcardCollectionRepository;

    /**
     * Checks if the collection name is already taken.
     * @param user the user.
     * @param collectionName the collection name.
     * @return the boolean value.
     */
    public boolean isFlashcardCollectionNameTaken(User user, String collectionName) {

        return this.flashcardCollectionRepository.findByNameAndUser(collectionName, user).isPresent();
    }

    /**
     * Fetches all flashcard collections owned by the user.
     * @param user the user.
     * @return the list of flashcard collections.
     */
    public List<FlashcardCollection> getFlashcardCollections(User user) {

        return this.flashcardCollectionRepository.findAllByUser(user);
    }

    /**
     * Fetch a flashcard collection owned by the user.
     * @param user the user.
     * @param collectionName the collection name.
     * @return the optional object.
     */
    public Optional<FlashcardCollection> getFlashcardCollection(User user, String collectionName) {

        return this.flashcardCollectionRepository.findByNameAndUser(collectionName, user);
    }

    /**
     * Creates a new flashcard collection in the database.
     * @param user the user.
     * @param collectionName the collection name.
     * @return the optional object.
     */
    public Optional<FlashcardCollection> createFlashcardCollection(User user, String collectionName) {
        //Check if not null.
        if (!isFlashcardCollectionNameTaken(user, collectionName) && user != null) {
            //Create flashcard collection object.
            FlashcardCollection flashcardCollection = new FlashcardCollection(null, collectionName, null, user);

            //Save flashcard collection.
            return Optional.of(this.flashcardCollectionRepository.save(flashcardCollection));
        }
        return Optional.empty();
    }

    /**
     * Deletes a flashcard collection owned by the user.
     * @param user the user.
     * @param collectionName the collection name.
     */
    public void deleteFlashcardCollection(User user, String collectionName) {
        //Check if collection exists.
        if (isFlashcardCollectionNameTaken(user, collectionName)) {
            //Delete flashcard collection.
            this.flashcardCollectionRepository.deleteByNameAndUser(collectionName, user);
        }
    }
}
