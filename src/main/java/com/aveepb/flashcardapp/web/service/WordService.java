package com.aveepb.flashcardapp.web.service;

import com.aveepb.flashcardapp.db.constant.WordType;
import com.aveepb.flashcardapp.db.model.Collection;
import com.aveepb.flashcardapp.db.model.Word;
import com.aveepb.flashcardapp.db.repo.WordRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WordService {

    private final WordRepository wordRepository;

    /**
     * Fetches a random word from a given collection.
     * @param collection the collection.
     * @return the optional of word.
     */
    public Optional<Word> getRandomWord(Collection collection) {
        //Fetch all words in this collection and initializes random.
        List<Word> wordList = this.wordRepository.findAllByCollection(collection);
        Random rand = new Random();

        //Check if collection is empty.
        if (wordList.isEmpty())
            return Optional.empty();

        return Optional.of(wordList.get(rand.nextInt(0, wordList.size())));
    }

    /**
     * Inserts a new word into the database.
     * @param wordType the word type.
     * @param englishMeaning the english meaning.
     * @param polishMeaning the polish meaning.
     * @param collection the collection.
     * @return true if the word was created otherwise false.
     */
    public boolean createWord(WordType wordType, String englishMeaning, String polishMeaning, Collection collection) {
        //Check if such word exists in the collection.
        if (this.wordRepository.findByTypeAndEnglishMeaningAndCollection(wordType, englishMeaning, collection).isPresent())
            return false;

        Word newWord = Word.builder()
                .type(wordType)
                .englishMeaning(englishMeaning)
                .polishMeaning(polishMeaning)
                .collection(collection)
                .build();

        this.wordRepository.save(newWord);
        return true;
    }

    /**
     * Removes the object from the database.
     * @param wordType the word type.
     * @param englishMeaning the english meaning.
     * @param collection the collection.
     * @return true if the word was removed otherwise false.
     */
    public boolean deleteWord(WordType wordType, String englishMeaning, Collection collection) {

        Optional<Word> wordOptional = this.wordRepository.findByTypeAndEnglishMeaningAndCollection(wordType, englishMeaning, collection);

        //Check if the word exists.
        if (wordOptional.isEmpty())
            return false;

        this.wordRepository.delete(wordOptional.get());
        return true;
    }
}
