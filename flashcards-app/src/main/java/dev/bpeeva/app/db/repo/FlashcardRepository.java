package dev.bpeeva.app.db.repo;

import dev.bpeeva.app.db.model.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashcardRepository extends JpaRepository<Integer, Flashcard> {
    //custom methods...
}
