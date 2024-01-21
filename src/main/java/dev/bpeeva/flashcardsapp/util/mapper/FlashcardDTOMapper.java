package dev.bpeeva.flashcardsapp.util.mapper;

import dev.bpeeva.flashcardsapp.db.model.Flashcard;
import dev.bpeeva.flashcardsapp.util.dto.FlashcardDTO;

import java.util.Optional;
import java.util.function.Function;

public class FlashcardDTOMapper implements Function<Flashcard, Optional<FlashcardDTO>> {

    @Override
    public Optional<FlashcardDTO> apply(Flashcard flashcard) {
        //Create data transfer object.
        FlashcardDTO flashcardDTO = new FlashcardDTO(
                flashcard.getWordType(),
                flashcard.getEnglishWord(),
                flashcard.getPolishWord()
        );

        //Check if not null.
        if (flashcardDTO.isNotNull())
            return Optional.of(flashcardDTO);

        return Optional.empty();
    }
}
