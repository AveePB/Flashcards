package dev.bpeeva.app.util;

import dev.bpeeva.app.db.constant.WordClass;
import dev.bpeeva.app.db.model.Flashcard;
import dev.bpeeva.app.db.model.FlashcardCollection;
import dev.bpeeva.app.db.model.User;
import dev.bpeeva.app.util.dto.FlashcardCollectionDTO;
import dev.bpeeva.app.util.dto.FlashcardDTO;
import dev.bpeeva.app.util.dto.UserDTO;
import dev.bpeeva.app.util.mapper.DTOMapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DTOMapperTests {

    private User user = new User(234, "Jacob", "%#%&#@DFS", null);
    private FlashcardCollection flashcardCollection = new FlashcardCollection(21, "Animals", this.user, null);
    private Flashcard flashcard = new Flashcard(1, WordClass.NOUN, "A dog", "Pies", this.flashcardCollection);

    @Test
    void shouldSuccessfullyParseAUser() {
        UserDTO userDTO = DTOMapper.parse(this.user);

        assertThat(userDTO.username()).isEqualTo(this.user.getUsername());
        assertThat(userDTO.password()).isEqualTo(this.user.getPassword());
    }

    @Test
    void shouldSuccessfullyParseAFlashcardCollection() {
        FlashcardCollectionDTO flashcardCollectionDTO = DTOMapper.parse(this.flashcardCollection);

        assertThat(flashcardCollectionDTO.name()).isEqualTo(this.flashcardCollection.getName());
    }

    @Test
    void shouldSuccessfullyParseAFlashcard() {
        FlashcardDTO flashcardDTO = DTOMapper.parse(this.flashcard);

        assertThat(flashcardDTO.wordClass()).isEqualTo(this.flashcard.getWordClass());
        assertThat(flashcardDTO.motherLang()).isEqualTo(this.flashcard.getMotherLang());
        assertThat(flashcardDTO.foreignLang()).isEqualTo(this.flashcard.getForeignLang());
    }
}
