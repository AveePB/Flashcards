package com.aveepb.flashcardapp.db.repo;

import com.aveepb.flashcardapp.db.model.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Integer> {

}
