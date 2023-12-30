package com.aveepb.flashcardapp.web.service;

import com.aveepb.flashcardapp.db.model.Collection;
import com.aveepb.flashcardapp.db.model.User;
import com.aveepb.flashcardapp.db.repo.CollectionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;

    /**
     * Fetches all collections owned by the user.
     * @param user the user details.
     * @return the list of collections.
     */
    public List<Collection> getAllCollections(User user) {

        return this.collectionRepository.findAllByUser(user);
    }

    /**
     * Fetches the collection owned by the user.
     * @param collectionName the collection name.
     * @param user the user details.
     * @return the optional of collection.
     */
    public Optional<Collection> getCollection(String collectionName, User user) {

        return this.collectionRepository.findByNameAndUser(collectionName, user);
    }

    /**
     * Inserts a new collection into the database.
     * @param collectionName the collection name.
     * @param user the user details.
     * @return true if the collection was created otherwise false.
     */
    public boolean createCollection(String collectionName, User user) {
        //Check if the collection name is taken.
        if (this.collectionRepository.findByNameAndUser(collectionName, user).isPresent())
            return false;

        Collection newCollection = Collection.builder()
                .name(collectionName)
                .user(user)
                .build();

        this.collectionRepository.save(newCollection);
        return true;
    }

    /**
     * Removes the object from the database.
     * @param collectionName the collection name.
     * @param user the user details.
     * @return true if the collection was deleted otherwise false.
     */
    public boolean deleteCollection(String collectionName, User user) {

        Optional<Collection> collectionOptional = getCollection(collectionName, user);

        //Check if the collection exists.
        if (collectionOptional.isEmpty())
            return false;

        this.collectionRepository.delete(collectionOptional.get());
        return true;
    }
}
