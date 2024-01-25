package dev.bpeeva.flashcardsapp.service;

import dev.bpeeva.flashcardsapp.app.service.FlashcardService;
import dev.bpeeva.flashcardsapp.db.constant.UserRole;
import dev.bpeeva.flashcardsapp.db.constant.WordType;
import dev.bpeeva.flashcardsapp.db.model.Flashcard;
import dev.bpeeva.flashcardsapp.db.model.FlashcardCollection;
import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.db.repo.FlashcardCollectionRepository;
import dev.bpeeva.flashcardsapp.db.repo.FlashcardRepository;
import dev.bpeeva.flashcardsapp.db.repo.UserRepository;
import dev.bpeeva.flashcardsapp.util.dto.FlashcardDTO;
import dev.bpeeva.flashcardsapp.util.mapper.FlashcardDTOMapper;

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
public class FlashcardServiceTests {

    @Autowired
    private final UserRepository userRepository = null;
    @Autowired
    private final FlashcardCollectionRepository flashcardCollectionRepository = null;
    @Autowired
    private final FlashcardRepository flashcardRepository = null;
    @Autowired
    private final FlashcardService flashcardService = null;

    private User user;
    private FlashcardCollection flashcardCollection;

    @BeforeEach
    void setUp() {
        //Create required objects.
        this.user = new User(null, UserRole.USER, "Alex", "", null);
        this.flashcardCollection = new FlashcardCollection(null, "Random Words", null, this.user);

        //Save to repos.
        this.userRepository.save(this.user);
        this.flashcardCollectionRepository.save(this.flashcardCollection);
    }

    @Test
    void shouldReturnAllFlashcardsFromTheCollection() {
        //Save flashcards to repo.
        this.flashcardRepository.save(new Flashcard(null, WordType.VERB, "Eat", "Jesc", this.flashcardCollection));
        this.flashcardRepository.save(new Flashcard(null, WordType.VERB, "E4t", "J3sc", this.flashcardCollection));

        //Fetch flashcards.
        List<Flashcard> flashcardList = this.flashcardService.getFlashcards(this.flashcardCollection);

        //Check list size.
        assertThat(flashcardList.size()).isEqualTo(2);
    }

    @Test
    void shouldNotReturnAnAnyFlashcardsFromTheCollection() {
        //Fetch flashcards.
        List<Flashcard> flashcardList = this.flashcardService.getFlashcards(this.flashcardCollection);
        List<Flashcard> flashcardList2 = this.flashcardService.getFlashcards(null);

        //Check list sizes.
        assertThat(flashcardList.size()).isEqualTo(0);
        assertThat(flashcardList2.size()).isEqualTo(0);
    }

    @Test
    void shouldReturnARandomFlashcard() {
        //Save flashcard to repo.
        this.flashcardRepository.save(new Flashcard(null, WordType.VERB, "Go", "Isc", this.flashcardCollection));

        //Fetch random flashcard.
        Optional<Flashcard> flashcard = this.flashcardService.getRandomFlashcard(this.flashcardCollection);

        //Check if present.
        assertThat(flashcard.isPresent()).isTrue();
    }

    @Test
    void shouldNotReturnARandomFlashcard() {
        //Fetch random flashcard.
        Optional<Flashcard> flashcard = this.flashcardService.getRandomFlashcard(this.flashcardCollection);

        //Check if null.
        assertThat(flashcard.isEmpty()).isTrue();
    }

    @Test
    void shouldInsertANewFlashcardIntoTheCollection() {
        //Create flashcard DTO.
        FlashcardDTO flashcardDTO = new FlashcardDTO(WordType.VERB, "Kick", "Kopnac");

        //Update collection.
        this.flashcardService.createFlashcard(this.flashcardCollection, flashcardDTO);

        //Find collection size.
        int collectionSize = this.flashcardRepository.findAllByFlashcardCollection(this.flashcardCollection).size();

        //Check collection size.
        assertThat(collectionSize).isEqualTo(1);
    }

    @Test
    void shouldNotInsertAFlashcardWithNoCollection() {
        //Create flashcard DTO.
        FlashcardDTO flashcardDTO = new FlashcardDTO(WordType.VERB, "Kick", "Kopnac");

        //Update collection.
        this.flashcardService.createFlashcard(null, flashcardDTO);

        //Find collection size.
        int collectionSize = this.flashcardRepository.findAllByFlashcardCollection(this.flashcardCollection).size();

        //Check collection size.
        assertThat(collectionSize).isEqualTo(0);
    }

    @Test
    void shouldNotInsertAFlashcardWithLackingParameters() {
        //Create flashcard DTO.
        FlashcardDTO flashcardDTO = new FlashcardDTO(null, null, null);

        //Update collection.
        this.flashcardService.createFlashcard(this.flashcardCollection, flashcardDTO);

        //Find collection size.
        int collectionSize = this.flashcardRepository.findAllByFlashcardCollection(this.flashcardCollection).size();

        //Check collection size.
        assertThat(collectionSize).isEqualTo(0);
    }

    @Test
    void shouldDeleteAFlashcardFromTheCollection() {
        //Create flashcard.
        Flashcard flashcard = new Flashcard(null, WordType.VERB, "Eat", "Jesc", this.flashcardCollection);

        //Save flashcard to repo.
        this.flashcardRepository.save(flashcard);

        //Create flashcard DTO.
        Optional<FlashcardDTO> flashcardDTO = new FlashcardDTOMapper().apply(flashcard);

        //Delete flashcard.
        this.flashcardService.deleteFlashcard(this.flashcardCollection, flashcardDTO.get());

        //Check collection size.
        int collectionSize = this.flashcardRepository.findAllByFlashcardCollection(this.flashcardCollection).size();
        assertThat(collectionSize).isEqualTo(0);
    }

    @Test
    void shouldNotDeleteAFlashcardWithNoCollection() {
        //Create flashcard.
        Flashcard flashcard = new Flashcard(null, WordType.VERB, "Eat", "Jesc", this.flashcardCollection);

        //Save flashcard to repo.
        this.flashcardRepository.save(flashcard);

        //Create flashcard DTO.
        Optional<FlashcardDTO> flashcardDTO = new FlashcardDTOMapper().apply(flashcard);

        //Delete flashcard.
        this.flashcardService.deleteFlashcard(null, flashcardDTO.get());

        //Check collection size.
        int collectionSize = this.flashcardRepository.findAllByFlashcardCollection(this.flashcardCollection).size();
        assertThat(collectionSize).isEqualTo(1);
    }

    @Test
    void shouldNotDeleteAFlashcardWithLackingParameters() {
        //Create flashcard.
        Flashcard flashcard = new Flashcard(null, WordType.VERB, "Eat", "Jesc", this.flashcardCollection);

        //Save flashcard to repo.
        this.flashcardRepository.save(flashcard);

        //Delete flashcard.
        this.flashcardService.deleteFlashcard(this.flashcardCollection, new FlashcardDTO(null, null, null));

        //Check collection size.
        int collectionSize = this.flashcardRepository.findAllByFlashcardCollection(this.flashcardCollection).size();
        assertThat(collectionSize).isEqualTo(1);
    }
}
