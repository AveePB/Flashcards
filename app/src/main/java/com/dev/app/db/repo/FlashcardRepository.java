package com.dev.app.db.repo;

import com.dev.app.db.model.Flashcard;

import com.dev.app.db.model.FlashcardCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {

    Optional<Flashcard> findById(Integer id);
    List<Flashcard> findAllByCollection(FlashcardCollection collection);
}
