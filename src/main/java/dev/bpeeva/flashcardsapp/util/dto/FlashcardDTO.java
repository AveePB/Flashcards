package dev.bpeeva.flashcardsapp.util.dto;

import dev.bpeeva.flashcardsapp.db.constant.WordType;

public record FlashcardDTO(
        WordType wordType,
        String englishWord,
        String polishWord
) {

    /**
     * Checks if all object parameters aren't null.
     * @return the boolean value.
     */
    public boolean isNotNull() {
        //Check word type.
        if (this.wordType == null)
            return false;

        //Check english word.
        if (this.englishWord == null)
            return false;

        //Check polish word.
        if (this.polishWord == null)
            return false;

        return true;
    }

}
