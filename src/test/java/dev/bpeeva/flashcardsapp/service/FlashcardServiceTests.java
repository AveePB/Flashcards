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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

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
        this.flashcardCollection = new FlashcardCollection(null, "Animals", null, this.user);

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
    void shouldNotReturnAnyFlashcardsFromTheCollection() {
        //Fetch flashcards.
        List<Flashcard> flashcardList = this.flashcardService.getFlashcards(this.flashcardCollection);
        List<Flashcard> flashcardList2 = this.flashcardService.getFlashcards(null);

        //Check list sizes.
        assertThat(flashcardList.size()).isEqualTo(0);
        assertThat(flashcardList2.size()).isEqualTo(0);
    }
}
