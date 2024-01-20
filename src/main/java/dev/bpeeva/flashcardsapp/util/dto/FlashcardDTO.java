package dev.bpeeva.flashcardsapp.util.dto;

import dev.bpeeva.flashcardsapp.db.constant.WordType;

public record FlashcardDTO(
        WordType wordType,
        String englishWord,
        String polishWord
) {

}
