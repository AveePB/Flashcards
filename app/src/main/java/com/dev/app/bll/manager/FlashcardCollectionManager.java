package com.dev.app.bll.manager;

import com.dev.app.db.model.FlashcardCollection;
import com.dev.app.db.model.User;
import com.dev.app.db.repo.FlashcardCollectionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlashcardCollectionManager {

    @Autowired
    private final FlashcardCollectionRepository flashcardCollectionRepository;

    public Optional<FlashcardCollection> createNewFlashcardCollection(String collectionName, User owner) {
        if (collectionName == null || owner == null) return Optional.empty();

        int collectionNameCount = flashcardCollectionRepository.findAllByNameAndOwner(collectionName, owner).size();
        if (collectionNameCount > 0) return Optional.empty();

        FlashcardCollection flashcardCollection = FlashcardCollection.builder()
                .name(collectionName)
                .owner(owner)
                .build();

        return Optional.of(flashcardCollectionRepository.save(flashcardCollection));
    }

    public List<FlashcardCollection> getAllFlashcardCollections(User owner) {
        if (owner == null)
            return new ArrayList<>();

        return flashcardCollectionRepository.findAllByOwner(owner);
    }

    public Optional<FlashcardCollection> updateFlashcardCollectionName(Integer id, String collectionName) {
        if (id == null || collectionName == null) return Optional.empty();

        Optional<FlashcardCollection> flashcardCollection = this.flashcardCollectionRepository.findById(id);
        if (flashcardCollection.isEmpty()) return Optional.empty();

        flashcardCollection.get().setName(collectionName);
        return Optional.of(flashcardCollectionRepository.save(flashcardCollection.get()));
    }

    public boolean deleteFlashcardCollection(Integer id) {
        if (id == null) return false;

        flashcardCollectionRepository.deleteById(id);

        return true;
    }
}
