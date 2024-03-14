package dev.bpeeva.app.util.mapper;

import dev.bpeeva.app.db.model.Flashcard;
import dev.bpeeva.app.db.model.FlashcardCollection;
import dev.bpeeva.app.db.model.User;
import dev.bpeeva.app.util.dto.FlashcardCollectionDTO;
import dev.bpeeva.app.util.dto.FlashcardDTO;
import dev.bpeeva.app.util.dto.UserDTO;

public class DTOMapper {

    public static UserDTO parse(User user) {

        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public static FlashcardCollectionDTO parse(FlashcardCollection flashcardCollection) {

        return FlashcardCollectionDTO.builder()
                .name(flashcardCollection.getName())
                .build();
    }

    public static FlashcardDTO parse(Flashcard flashcard) {

        return FlashcardDTO.builder()
                .wordClass(flashcard.getWordClass())
                .motherLang(flashcard.getMotherLang())
                .foreignLang(flashcard.getForeignLang())
                .build();
    }
}
