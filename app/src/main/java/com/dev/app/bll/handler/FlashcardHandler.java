package com.dev.app.bll.handler;

import com.dev.app.bll.manager.FlashcardCollectionManager;
import com.dev.app.bll.manager.FlashcardManager;
import com.dev.app.bll.manager.UserManager;
import com.dev.app.db.model.Flashcard;
import com.dev.app.db.model.FlashcardCollection;
import com.dev.app.db.model.User;
import com.dev.app.util.dto.DTOMapper;
import com.dev.app.util.dto.FlashcardCollectionDTO;
import com.dev.app.util.dto.FlashcardDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlashcardHandler {

    @Autowired
    private final UserManager userManager;
    @Autowired
    private final FlashcardCollectionManager collManager;
    @Autowired
    private final FlashcardManager flashcardManager;

    public URI createNewFlashcard(FlashcardDTO flashcardDTO) throws Exception {
        if (flashcardDTO == null) throw new Exception("Flashcard Data Transfer Object is NULL...");

        UserDetails collectionOwner = userManager.loadUserByUsername(flashcardDTO.ownerNickname());
        Optional<FlashcardCollection> flashcardCollection = collManager.getAllFlashcardCollections((User) collectionOwner).stream()
                .findAny()
                .filter(coll -> coll.getName().equals(flashcardDTO.collectionName()));

        if (flashcardCollection.isEmpty()) throw new Exception("Flashcard Collection doesn't exit...");

        Optional<Flashcard> createdFlashcard = flashcardManager.createNewFlashcard(flashcardDTO.motherLang(), flashcardDTO.foreignLang(), flashcardCollection.get());
        if (createdFlashcard.isEmpty()) throw new Exception("Flashcard wasn't created...");

        return UriComponentsBuilder.newInstance()
                .path("flashcards/" + flashcardDTO.ownerNickname() + "/" + flashcardDTO.collectionName() + "/{id}")
                .buildAndExpand(createdFlashcard.get().getId())
                .toUri();
    }

    public void deleteFlashcard(FlashcardDTO flashcardDTO) throws Exception {
        if (flashcardDTO == null) throw new Exception("Flashcard Data Transfer Object is NULL...");

        UserDetails collectionOwner = userManager.loadUserByUsername(flashcardDTO.ownerNickname());
        Optional<FlashcardCollection> flashcardCollection = collManager.getAllFlashcardCollections((User) collectionOwner).stream()
                .findAny()
                .filter(coll -> coll.getName().equals(flashcardDTO.collectionName()));

        if (flashcardCollection.isEmpty()) throw new Exception("Flashcard Collection doesn't exit...");

        Optional<Flashcard> flashcardToDelete = flashcardManager.getAllFlashcards(flashcardCollection.get()).stream()
                .findFirst()
                .filter(f -> f.getMotherLang().equals(flashcardDTO.motherLang())
                        && f.getForeignLang().equals(flashcardDTO.foreignLang())
                );

        flashcardToDelete.ifPresent(
                f -> flashcardManager.deleteFlashcard(f.getId())
        );
    }

    public Optional<FlashcardDTO> getRandomFlashcard(FlashcardCollectionDTO flashcardCollectionDTO) throws Exception {
        if (flashcardCollectionDTO == null) throw new Exception("Flashcard Data Transfer Object is NULL...");

        UserDetails collectionOwner = userManager.loadUserByUsername(flashcardCollectionDTO.ownerNickname());
        Optional<FlashcardCollection> flashcardCollection = collManager.getAllFlashcardCollections((User) collectionOwner).stream()
                .findAny()
                .filter(coll -> coll.getName().equals(flashcardCollectionDTO.collectionName()));

        if (flashcardCollection.isEmpty()) throw new Exception("Flashcard Collection doesn't exit...");

        Optional<Flashcard> randomFlashcard = flashcardManager.getRandomFlashcard(flashcardCollection.get());
        if (randomFlashcard.isEmpty()) return Optional.empty();

        return DTOMapper.mapFlashcard(randomFlashcard.get().getMotherLang(), randomFlashcard.get().getForeignLang(),
                flashcardCollectionDTO.collectionName(), flashcardCollectionDTO.ownerNickname());
    }
}
