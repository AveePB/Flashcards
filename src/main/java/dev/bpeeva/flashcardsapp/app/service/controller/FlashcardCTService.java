package dev.bpeeva.flashcardsapp.app.service.controller;

import dev.bpeeva.flashcardsapp.app.service.model.FlashcardCollectionService;
import dev.bpeeva.flashcardsapp.app.service.model.FlashcardService;
import dev.bpeeva.flashcardsapp.app.service.model.UserService;
import dev.bpeeva.flashcardsapp.db.model.Flashcard;
import dev.bpeeva.flashcardsapp.db.model.FlashcardCollection;
import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.util.dto.FlashcardDTO;
import dev.bpeeva.flashcardsapp.util.mapper.FlashcardDTOMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

        //User isn't present.
        if (user.isEmpty())
            return false;

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

        //User isn't present.
        if (user.isEmpty())
            return false;

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

    /**
     * Chooses a random flashcard from the collection.
     * @param username the name of a collection owner.
     * @param collectionName the flashcard collection name.
     * @return the optional object.
     */
    public Optional<FlashcardDTO> getRandomFlashcard(String username, String collectionName) {
        //Fetch user object.
        Optional<User> user = this.userService.getUser(username);

        //User isn't present.
        if (user.isEmpty())
            return Optional.empty();

        //Fetch flashcard collection object.
        Optional<FlashcardCollection> flashcardCollection = this.flashcardCollectionService.getFlashcardCollection(user.get(), collectionName);

        //Collection isn't present.
        if (flashcardCollection.isEmpty())
            return Optional.empty();

        //Fetch random flashcard.
        Optional<Flashcard> flashcard = this.flashcardService.getRandomFlashcard(flashcardCollection.get());

        //Flashcard isn't present.
        if (flashcard.isEmpty())
            return Optional.empty();

        return new FlashcardDTOMapper().apply(flashcard.get());
    }

    /**
     * Fetches all flashcards from the collection.
     * @param username the name of a collection owner.
     * @param collectionName the flashcard collection name.
     * @return the list object.
     */
    public List<FlashcardDTO> getAllFlashcards(String username, String collectionName) {
        //Fetch user object.
        Optional<User> user = this.userService.getUser(username);

        //User isn't present.
        if (user.isEmpty())
            return new ArrayList<>();

        //Fetch flashcard collection object.
        Optional<FlashcardCollection> flashcardCollection = this.flashcardCollectionService.getFlashcardCollection(user.get(), collectionName);

        //Collection isn't present.
        if (flashcardCollection.isEmpty())
            return new ArrayList<>();

        //Fetch flashcards.
        List<Flashcard> flashcards = this.flashcardService.getFlashcards(flashcardCollection.get());

        //Convert to flashcard dto.
        return flashcards.stream()
                .map(new FlashcardDTOMapper())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
