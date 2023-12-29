package com.aveepb.flashcardapp.db.repo;

import com.aveepb.flashcardapp.db.model.Collection;

import com.aveepb.flashcardapp.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CollectionRepository extends JpaRepository<Collection, Integer> {

    //READ:
    List<Collection> findAllByUser(User user);
    Optional<Collection> findByNameAndUser(String name, User user);

    //DELETE:
    void deleteByNameAndUser(String name, User user);
}
