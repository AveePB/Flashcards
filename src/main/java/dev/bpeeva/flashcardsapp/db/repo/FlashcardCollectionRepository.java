package dev.bpeeva.flashcardsapp.db.repo;

import dev.bpeeva.flashcardsapp.db.model.FlashcardCollection;
import dev.bpeeva.flashcardsapp.db.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlashcardCollectionRepository extends JpaRepository<FlashcardCollection, Integer> {

    Optional<FlashcardCollection> findByNameAndUser(String name, User user);

    List<FlashcardCollection> findAllByUser(User user);

    void deleteByNameAndUser(String name, User user);
}
