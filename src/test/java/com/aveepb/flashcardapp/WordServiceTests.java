package com.aveepb.flashcardapp;

import com.aveepb.flashcardapp.db.constant.UserRole;
import com.aveepb.flashcardapp.db.constant.WordType;
import com.aveepb.flashcardapp.db.model.Collection;
import com.aveepb.flashcardapp.db.model.User;
import com.aveepb.flashcardapp.db.model.Word;
import com.aveepb.flashcardapp.db.repo.UserRepository;
import com.aveepb.flashcardapp.db.repo.WordRepository;
import com.aveepb.flashcardapp.web.service.CollectionService;
import com.aveepb.flashcardapp.web.service.WordService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class WordServiceTests {

    //Repositories:
    @Autowired
    private final WordRepository wordRepository = null;
    @Autowired
    private final UserRepository userRepository = null;

    //Services:
    @Autowired
    private final WordService wordService = null;
    @Autowired
    private final CollectionService collectionService = null;

    //Encoder:
    @Autowired
    private final PasswordEncoder passwordEncoder = null;

    @Test
    void shouldReturnWordFromCollection() {
        //Create user.
        User userStas = new User(null, "Stas", this.passwordEncoder.encode(""), UserRole.USER, null);
        userStas = this.userRepository.save(userStas);

        //Create collection.
        this.collectionService.createCollection("Animals", userStas);
        Collection animalsCollection = this.collectionService.getCollection("Animals", userStas).get();

        //Create word.
        Word word = Word.builder()
                .type(WordType.NOUN)
                .englishMeaning("Dog")
                .polishMeaning("Pies")
                .collection(animalsCollection)
                .build();

        this.wordRepository.save(word);

        //Get word from collection.
        Optional<Word> randomWordOptional = this.wordService.getRandomWord(animalsCollection);

        //Check word validation.
        assertThat(randomWordOptional.isPresent()).isEqualTo(true);
        Word randomWord = randomWordOptional.get();

        assertThat(randomWord.getType()).isEqualTo(WordType.NOUN);
        assertThat(randomWord.getEnglishMeaning()).isEqualTo("Dog");
        assertThat(randomWord.getPolishMeaning()).isEqualTo("Pies");
    }

    @Test
    void shouldReturnNoWordFromCollection() {
        //Create user.
        User userStas = new User(null, "Stas", this.passwordEncoder.encode(""), UserRole.USER, null);
        userStas = this.userRepository.save(userStas);

        //Create collection.
        this.collectionService.createCollection("Animals", userStas);
        Collection animalsCollection = this.collectionService.getCollection("Animals", userStas).get();


        //Get word from collection.
        Optional<Word> wordOptional = this.wordService.getRandomWord(animalsCollection);

        //Check word validation.
        assertThat(wordOptional.isPresent()).isEqualTo(false);
    }

    @Test
    void shouldCreateWordInCollection() {
        //Create user.
        User userJohn = new User(null, "John", this.passwordEncoder.encode(""), UserRole.USER, null);
        userJohn = this.userRepository.save(userJohn);

        //Create collection owned by the users.
        this.collectionService.createCollection("jobs", userJohn);
        Collection jobsCollection = this.collectionService.getCollection("Jobs", userJohn).get();

        //Create word in jobs collection.
        this.wordService.createWord(WordType.NOUN, "Doctor", "Doktor", jobsCollection);

        //Get words in the collection.
        List<Word> wordList = this.wordRepository.findAllByCollection(jobsCollection);
        assertThat(wordList.size()).isEqualTo(1);
    }

    @Test
    void shouldNotCreateWordInCollectionWithTakenNameAndType() {
        //Create user.
        User userJohn = new User(null, "John", this.passwordEncoder.encode(""), UserRole.USER, null);
        userJohn = this.userRepository.save(userJohn);

        //Create collection owned by the users.
        this.collectionService.createCollection("jobs", userJohn);
        Collection jobsCollection = this.collectionService.getCollection("Jobs", userJohn).get();

        //Create word in jobs collection.
        this.wordService.createWord(WordType.NOUN, "Doctor", "Doktor", jobsCollection);
        this.wordService.createWord(WordType.NOUN, "Doctor", "Doktor", jobsCollection);

        //Get words in the collection.
        List<Word> wordList = this.wordRepository.findAllByCollection(jobsCollection);
        assertThat(wordList.size()).isEqualTo(1);
    }

    @Test
    void shouldDeleteWord() {
        //Create user.
        User userJohn = new User(null, "John", this.passwordEncoder.encode(""), UserRole.USER, null);
        userJohn = this.userRepository.save(userJohn);

        //Create collection owned by the users.
        this.collectionService.createCollection("jobs", userJohn);
        Collection jobsCollection = this.collectionService.getCollection("Jobs", userJohn).get();

        //Create word in jobs collection.
        this.wordService.createWord(WordType.NOUN, "Doctor", "Doktor", jobsCollection);

        //Delete word.
        this.wordService.deleteWord(WordType.NOUN, "Doctor", jobsCollection);
        Optional<Word> wordOptional = this.wordRepository.findByTypeAndEnglishMeaningAndCollection(WordType.NOUN, "Doctor", jobsCollection);
        assertThat(wordOptional.isPresent()).isEqualTo(false);
    }

    @Test
    void shouldNotDeleteWordThatDoesNotExist() {
        //Create user.
        User userJohn = new User(null, "John", this.passwordEncoder.encode(""), UserRole.USER, null);
        userJohn = this.userRepository.save(userJohn);

        //Create collection owned by the users.
        this.collectionService.createCollection("jobs", userJohn);
        Collection jobsCollection = this.collectionService.getCollection("Jobs", userJohn).get();

        //Delete word.
        boolean isDeleted = this.wordService.deleteWord(WordType.NOUN, "Doctor", jobsCollection);
        assertThat(isDeleted).isEqualTo(false);
    }
}
