package dev.bpeeva.flashcardsapp.dto;

import dev.bpeeva.flashcardsapp.db.constant.WordType;
import dev.bpeeva.flashcardsapp.db.model.Flashcard;
import dev.bpeeva.flashcardsapp.util.dto.FlashcardDTO;
import dev.bpeeva.flashcardsapp.util.mapper.FlashcardDTOMapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FlashcardDTOTests {

    @Test
    void shouldCreateAFlashcardDTO() {
        //Create objects.
        Flashcard flashcard = new Flashcard(null, WordType.VERB, "cook", "gotowac", null);
        Optional<FlashcardDTO> flashcardDTO = new FlashcardDTOMapper().apply(flashcard);

        //Check if not null.
        assertThat(flashcardDTO.isPresent()).isEqualTo(true);

        //Check WORD TYPE.
        assertThat(flashcardDTO.get().wordType()).isEqualTo(flashcard.getWordType());

        //Check ENGLISH WORD.
        assertThat(flashcardDTO.get().englishWord()).isEqualTo(flashcard.getEnglishWord());

        //Check POLISH WORD.
        assertThat(flashcardDTO.get().polishWord()).isEqualTo(flashcard.getPolishWord());
    }

    @Test
    void shouldNotCreateAFlashcardDTO() {
        //Create objects.
        Flashcard flashcard = new Flashcard(null, null, null, null, null);
        Optional<FlashcardDTO> flashcardDTO = new FlashcardDTOMapper().apply(flashcard);

        //Check if null.
        assertThat(flashcardDTO.isEmpty()).isEqualTo(true);
    }
}
