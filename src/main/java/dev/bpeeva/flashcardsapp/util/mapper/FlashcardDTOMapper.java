package dev.bpeeva.flashcardsapp.util.mapper;

import dev.bpeeva.flashcardsapp.db.model.Flashcard;
import dev.bpeeva.flashcardsapp.util.dto.FlashcardDTO;

import java.util.Optional;
import java.util.function.Function;

public class FlashcardDTOMapper implements Function<Flashcard, Optional<FlashcardDTO>> {

    @Override
    public Optional<FlashcardDTO> apply(Flashcard flashcard) {
        //Check if not null.
        if (flashcard.getWordType() == null ||
                flashcard.getEnglishWord() == null ||
                flashcard.getPolishWord() == null)
            return Optional.empty();

        //Create new object.
        return Optional.of(
                new FlashcardDTO(
                        flashcard.getWordType(),
                        flashcard.getEnglishWord(),
                        flashcard.getPolishWord()
                ));
    }
}
