package com.dev.app.bll.handler;

import com.dev.app.bll.manager.FlashcardCollectionManager;
import com.dev.app.bll.manager.UserManager;
import com.dev.app.db.model.FlashcardCollection;
import com.dev.app.db.model.User;
import com.dev.app.util.dto.FlashcardCollectionDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlashcardCollectionHandler {

    @Autowired
    private final UserManager userManager;
    @Autowired
    private final FlashcardCollectionManager collectionManager;

    public URI createNewFlashcardCollection(FlashcardCollectionDTO flashcardCollectionDTO) throws HandlerException {
        if (flashcardCollectionDTO == null) throw new HandlerException("Flashcard Collection Data Transfer Object is NULL...");

        UserDetails collectionOwner = userManager.loadUserByUsername(flashcardCollectionDTO.ownerNickname());

        Optional<FlashcardCollection> createdCollection = collectionManager.createNewFlashcardCollection(flashcardCollectionDTO.collectionName(), (User) collectionOwner);
        if (createdCollection.isEmpty()) throw new HandlerException("Flashcard Collection wasn't created...");

        return UriComponentsBuilder.newInstance()
                .path("flashcards/" + flashcardCollectionDTO.ownerNickname() + "/{id}")
                .buildAndExpand(createdCollection.get().getId())
                .toUri();
    }

    public void deleteFlashcardCollection(FlashcardCollectionDTO flashcardCollectionDTO) throws HandlerException {
        if (flashcardCollectionDTO == null) throw new HandlerException("Flashcard Collection Data Transfer Object is NULL...");

        UserDetails collectionOwner = userManager.loadUserByUsername(flashcardCollectionDTO.ownerNickname());
        Optional<FlashcardCollection> flashcardCollection = collectionManager.getAllFlashcardCollections((User) collectionOwner).stream()
                .findAny()
                .filter(coll -> coll.getName().equals(flashcardCollectionDTO.collectionName()));

        flashcardCollection.ifPresent(
                coll -> collectionManager.deleteFlashcardCollection(coll.getId())
        );
    }
}
