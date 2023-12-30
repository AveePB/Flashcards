package com.aveepb.flashcardapp.db.repo;

import com.aveepb.flashcardapp.db.constant.WordType;
import com.aveepb.flashcardapp.db.model.Collection;
import com.aveepb.flashcardapp.db.model.Word;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {

    //READ:
    List<Word> findAllByCollection(Collection collection);
    Optional<Word> findByTypeAndEnglishMeaningAndCollection(WordType type, String englishMeaning, Collection collection);
}
