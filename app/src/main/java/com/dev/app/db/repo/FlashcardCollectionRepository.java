package com.dev.app.db.repo;

import com.dev.app.db.model.FlashcardCollection;

import com.dev.app.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardCollectionRepository extends JpaRepository<FlashcardCollection, Integer> {

    List<FlashcardCollection> findAllByOwner(User owner);
}
