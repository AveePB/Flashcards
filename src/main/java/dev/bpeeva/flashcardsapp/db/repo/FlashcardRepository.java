package dev.bpeeva.flashcardsapp.db.repo;

import dev.bpeeva.flashcardsapp.db.constant.WordType;
import dev.bpeeva.flashcardsapp.db.model.Flashcard;
import dev.bpeeva.flashcardsapp.db.model.FlashcardCollection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {

    Optional<Flashcard> findByWordTypeAndEnglishWordAndFlashcardCollection(WordType wordType, String englishWord, FlashcardCollection flashcardCollection);

    List<Flashcard> findAllByFlashcardCollection(FlashcardCollection flashcardCollection);

    void deleteByWordTypeAndEnglishWordAndFlashcardCollection(WordType wordType, String englishWord, FlashcardCollection flashcardCollection);
}