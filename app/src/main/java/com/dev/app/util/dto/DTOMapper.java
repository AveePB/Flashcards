package com.dev.app.util.dto;

import java.util.Optional;

public class DTOMapper {

    public static Optional<UserDTO> mapUser(String username, String password) {
        if (username == null || password == null) return Optional.empty();

        return Optional.of(UserDTO.builder()
                .username(username)
                .password(password)
                .build()
        );
    }

    public static Optional<FlashcardCollectionDTO> mapFlashcardCollection(String collectionName, String ownerNickname) {
        if (collectionName == null || ownerNickname == null) return Optional.empty();

        return Optional.of(FlashcardCollectionDTO.builder()
                .collectionName(collectionName)
                .ownerNickname(ownerNickname)
                .build()
        );
    }

    public static Optional<FlashcardDTO> mapFlashcard(String motherLang, String foreignLang, String collectionName, String ownerNickname) {
        if (motherLang == null || foreignLang == null || collectionName == null || ownerNickname == null) return Optional.empty();

        return Optional.of(FlashcardDTO.builder()
                .motherLang(motherLang)
                .foreignLang(foreignLang)
                .collectionName(collectionName)
                .ownerNickname(ownerNickname)
                .build()
        );
    }
}
