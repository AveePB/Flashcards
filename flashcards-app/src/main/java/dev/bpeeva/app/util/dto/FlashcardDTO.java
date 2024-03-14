package dev.bpeeva.app.util.dto;

import dev.bpeeva.app.db.constant.WordClass;
import lombok.Builder;

@Builder
public record FlashcardDTO(WordClass wordClass, String motherLang, String foreignLang) {
    //...
}
