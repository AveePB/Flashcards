package com.dev.app.bll.manager;

import com.dev.app.db.model.Flashcard;
import com.dev.app.db.model.FlashcardCollection;
import com.dev.app.db.repo.FlashcardRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FlashcardManager {

    private final FlashcardRepository flashcardRepository;

    public Optional<Flashcard> createNewFlashcard(String motherLang, String foreignLang, FlashcardCollection collection) {
        if (motherLang == null || foreignLang == null || collection == null) return Optional.empty();

        Flashcard newFlashcard = Flashcard.builder()
                .motherLang(motherLang)
                .foreignLang(foreignLang)
                .collection(collection)
                .build();

        try {
            return Optional.of(flashcardRepository.save(newFlashcard));
        }
        catch (InvalidDataAccessApiUsageException ex) {
            return Optional.empty();
        }
    }

    public List<Flashcard> getAllFlashcards(FlashcardCollection collection) {
        if (collection == null) return new ArrayList<>();

        try {
            return flashcardRepository.findAllByCollection(collection);
        }
        catch (InvalidDataAccessApiUsageException ex) {
            return new ArrayList<>();
        }
    }

    public Optional<Flashcard> getRandomFlashcard(FlashcardCollection collection) {
        if (collection == null) return Optional.empty();

        try {
            List<Flashcard> flashcards = flashcardRepository.findAllByCollection(collection);
            if (flashcards.isEmpty()) return Optional.empty();

            int randomIdx = new Random().nextInt(0, flashcards.size());
            return Optional.of(flashcards.get(randomIdx));
        }
        catch (InvalidDataAccessApiUsageException ex) {
            return Optional.empty();
        }
    }

    public boolean deleteFlashcard(Integer id) {
        if (id == null) return false;

        flashcardRepository.deleteById(id);

        return true;
    }
}
