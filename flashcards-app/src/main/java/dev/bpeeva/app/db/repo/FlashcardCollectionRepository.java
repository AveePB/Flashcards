package dev.bpeeva.app.db.repo;

import dev.bpeeva.app.db.model.FlashcardCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashcardCollectionRepository extends JpaRepository<FlashcardCollection, Integer> {
    //custom methods...
}
