package dev.bpeeva.flashcardsapp.app.service.model;

import dev.bpeeva.flashcardsapp.db.model.Flashcard;
import dev.bpeeva.flashcardsapp.db.model.FlashcardCollection;
import dev.bpeeva.flashcardsapp.db.repo.FlashcardRepository;
import dev.bpeeva.flashcardsapp.util.dto.FlashcardDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;

    /**
     * Fetches the flashcards from a given collection.
     * @param flashcardCollection the flashcard collection.
     * @return the optional object.
     */
    public List<Flashcard> getFlashcards(FlashcardCollection flashcardCollection) {

        return this.flashcardRepository.findAllByFlashcardCollection(flashcardCollection);
    }

    /**
     * Chooses the random flashcard from a given collection.
     * @param flashcardCollection the flashcard collection.
     * @return the optional object.
     */
    public Optional<Flashcard> getRandomFlashcard(FlashcardCollection flashcardCollection) {
        //Fetch flashcards.
        List<Flashcard> flashcards = this.getFlashcards(flashcardCollection);

        //Check if collection is empty.
        if (flashcards.size() == 0)
            return Optional.empty();

        //Choose random index.
        int randomIdx = new Random().nextInt(0, flashcards.size());

        return Optional.of(flashcards.get(randomIdx));
    }

    /**
     * Creates a new flashcard in the collection.
     * @param flashcardCollection the flashcard collection.
     * @param flashcardDTO the flashcard data transfer object.
     * @return the optional object.
     */
    public Optional<Flashcard> createFlashcard(FlashcardCollection flashcardCollection, FlashcardDTO flashcardDTO) {
        //Check if not null.
        if (flashcardDTO.isNotNull() && flashcardCollection != null) {
            //Create flashcard object.
            Flashcard flashcard = new Flashcard(null, flashcardDTO.wordType(), flashcardDTO.englishWord(), flashcardDTO.polishWord(), flashcardCollection);

            //Save flashcard.
            return Optional.of(this.flashcardRepository.save(flashcard));
        }

        return Optional.empty();
    }

    /**
     * Deletes the flashcards from a given collection.
     * @param flashcardCollection the flashcard collection.
     * @param flashcardDTO the flashcard data transfer object.
     */
    public void deleteFlashcard(FlashcardCollection flashcardCollection, FlashcardDTO flashcardDTO) {
        //Check if not null.
        if (flashcardDTO.isNotNull() && flashcardCollection != null) {
            //Delete flashcard.
            this.flashcardRepository.deleteByWordTypeAndEnglishWordAndFlashcardCollection(flashcardDTO.wordType(), flashcardDTO.englishWord(), flashcardCollection);
        }
    }
}
