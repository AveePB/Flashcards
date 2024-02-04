package dev.bpeeva.flashcardsapp.service.controller;

import dev.bpeeva.flashcardsapp.app.service.controller.FlashcardCTService;
import dev.bpeeva.flashcardsapp.db.constant.UserRole;
import dev.bpeeva.flashcardsapp.db.constant.WordType;
import dev.bpeeva.flashcardsapp.db.model.Flashcard;
import dev.bpeeva.flashcardsapp.db.model.FlashcardCollection;
import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.db.repo.FlashcardCollectionRepository;
import dev.bpeeva.flashcardsapp.db.repo.FlashcardRepository;
import dev.bpeeva.flashcardsapp.db.repo.UserRepository;
import dev.bpeeva.flashcardsapp.util.dto.FlashcardDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FlashcardCTServiceTests {

    @Autowired
    private final UserRepository userRepository = null;
    @Autowired
    private final FlashcardCollectionRepository flashcardCollectionRepository = null;
    @Autowired
    private final FlashcardRepository flashcardRepository = null;

    @Autowired
    private final FlashcardCTService flashcardCTService = null;

    private String username, collectionName;

    @BeforeEach
    void setUp() {
        //Create required variables.
        this.username = "Jonathan";
        this.collectionName = "Animals";

        //Save to repo.
        User user = this.userRepository.save(new User(null, UserRole.USER, this.username, "", null));
        FlashcardCollection flashcardCollection = this.flashcardCollectionRepository.save(new FlashcardCollection(null, this.collectionName, null, user));
        this.flashcardRepository.save(new Flashcard(null, WordType.VERB, "Kill", "ZaBIJAC", flashcardCollection));
    }

    @Test
    void shouldCreateAFlashcard() {
        //Create flashcard dto.
        FlashcardDTO flashcardDTO = new FlashcardDTO(WordType.VERB, "Eat", "Jesc");

        //Create flashcard.
        boolean isCreated = this.flashcardCTService.createFlashcard(this.username, this.collectionName, flashcardDTO);

        //Check if created.
        assertThat(isCreated).isTrue();
    }

    @Test
    void shouldNotCreateAFlashcardWithNonExistingCollection() {
        //Create flashcard dto.
        FlashcardDTO flashcardDTO = new FlashcardDTO(WordType.VERB, "Eat", "Jesc");

        //Create flashcard.
        boolean isCreated = this.flashcardCTService.createFlashcard(this.username, "Some name", flashcardDTO);

        //Check if created.
        assertThat(isCreated).isFalse();
    }

    @Test
    void shouldNotCreateAFlashcardWithNonExistingUser() {
        //Create flashcard dto.
        FlashcardDTO flashcardDTO = new FlashcardDTO(WordType.VERB, "Eat", "Jesc");

        //Create flashcard.
        boolean isCreated = this.flashcardCTService.createFlashcard("Some name", this.collectionName, flashcardDTO);

        //Check if created.
        assertThat(isCreated).isFalse();
    }

    @Test
    void shouldDeleteAFlashcard() {
        //Create flashcard dto.
        FlashcardDTO flashcardDTO = new FlashcardDTO(WordType.VERB, "Kill", "ZaBIJAC");

        //Delete flashcard.
        boolean isDeleted = this.flashcardCTService.deleteFlashcard(this.username, this.collectionName, flashcardDTO);

        assertThat(isDeleted).isTrue();
    }

    @Test
    void shouldDeleteANonExistingFlashcard() {
        //Create flashcard dto.
        FlashcardDTO flashcardDTO = new FlashcardDTO(WordType.VERB, "Random", "Randomowe");

        //Delete flashcard.
        boolean isDeleted = this.flashcardCTService.deleteFlashcard(this.username, this.collectionName, flashcardDTO);

        assertThat(isDeleted).isFalse();
    }

    @Test
    void shouldNotDeleteAFlashcardWithNonExistingCollection() {
        //Create flashcard dto.
        FlashcardDTO flashcardDTO = new FlashcardDTO(WordType.VERB, "Kill", "ZaBIJAC");

        //Delete flashcard.
        boolean isDeleted = this.flashcardCTService.deleteFlashcard(this.username, "Some name", flashcardDTO);

        assertThat(isDeleted).isFalse();
    }

    @Test
    void shouldNotDeleteAFlashcardWithNonExistingUser() {
        //Create flashcard dto.
        FlashcardDTO flashcardDTO = new FlashcardDTO(WordType.VERB, "Kill", "ZaBIJAC");

        //Delete flashcard.
        boolean isDeleted = this.flashcardCTService.deleteFlashcard("Some name", this.collectionName, flashcardDTO);

        assertThat(isDeleted).isFalse();
    }

    @Test
    void shouldReturnARandomFlashcard() {
        //Fetch random flashcard.
        Optional<FlashcardDTO> flashcardDTO = this.flashcardCTService.getRandomFlashcard(this.username, this.collectionName);

        //Check if present.
        assertThat(flashcardDTO.isPresent()).isTrue();

        //Check WORD TYPE.
        assertThat(flashcardDTO.get().wordType()).isNotNull();

        //Check ENGLISH MEANING.
        assertThat(flashcardDTO.get().englishWord()).isNotNull();

        //Check POLISH MEANING.
        assertThat(flashcardDTO.get().polishWord()).isNotNull();
    }

    @Test
    void shouldNotReturnARandomFlashcardWithNonExistingCollection() {
        //Fetch random flashcard.
        Optional<FlashcardDTO> flashcardDTO = this.flashcardCTService.getRandomFlashcard(this.username, "Some name");

        //Check if present.
        assertThat(flashcardDTO.isPresent()).isFalse();
    }

    @Test
    void shouldNotReturnARandomFlashcardWithNonExistingUser() {
        //Fetch random flashcard.
        Optional<FlashcardDTO> flashcardDTO = this.flashcardCTService.getRandomFlashcard("Some name", this.collectionName);

        //Check if present.
        assertThat(flashcardDTO.isPresent()).isFalse();
    }

    @Test
    void shouldReturn1Flashcard() {
        //Fetch all flashcards.
        List<FlashcardDTO> flashcardDTOList = this.flashcardCTService.getAllFlashcards(this.username, this.collectionName);

        //Check size.
        assertThat(flashcardDTOList.size()).isEqualTo(1);
    }

    @Test
    void shouldReturn0WithNonExistingCollection() {
        //Fetch all flashcards.
        List<FlashcardDTO> flashcardDTOList = this.flashcardCTService.getAllFlashcards(this.username, "Some name");

        //Check size.
        assertThat(flashcardDTOList.size()).isEqualTo(0);
    }

    @Test
    void shouldReturn0WithNonExistingUser() {
        //Fetch all flashcards.
        List<FlashcardDTO> flashcardDTOList = this.flashcardCTService.getAllFlashcards("Some name", this.collectionName);

        //Check size.
        assertThat(flashcardDTOList.size()).isEqualTo(0);
    }
}
