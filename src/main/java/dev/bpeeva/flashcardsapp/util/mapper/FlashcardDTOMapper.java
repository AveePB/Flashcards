package dev.bpeeva.flashcardsapp.util.mapper;

import dev.bpeeva.flashcardsapp.db.model.Flashcard;
import dev.bpeeva.flashcardsapp.util.dto.FlashcardDTO;

import java.util.function.Function;

public class FlashcardDTOMapper implements Function<Flashcard, FlashcardDTO> {

    @Override
    public FlashcardDTO apply(Flashcard flashcard) {
        return new FlashcardDTO(
                flashcard.getWordType(),
                flashcard.getEnglishWord(),
                flashcard.getPolishWord()
        );
    }
}
