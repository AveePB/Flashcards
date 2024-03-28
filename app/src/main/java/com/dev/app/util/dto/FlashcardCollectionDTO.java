package com.dev.app.util.dto;

import lombok.Builder;

@Builder
public record FlashcardCollectionDTO(String collectionName, String ownerNickname) {

    public boolean isAllSet() {
        if (collectionName == null) return false;

        if (ownerNickname == null) return false;

        return true;
    }
}
