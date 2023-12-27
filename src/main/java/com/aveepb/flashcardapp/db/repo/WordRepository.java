package com.aveepb.flashcardapp.db.repo;

import com.aveepb.flashcardapp.db.model.Word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {

}
