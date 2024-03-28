package com.dev.app.util.dto;

import lombok.Builder;

@Builder
public record FlashcardDTO(String motherLang, String foreignLang, String collectionName, String ownerNickname) {

    public boolean isAllSet() {
        if (motherLang == null) return false;

        if (foreignLang == null) return false;

        if (collectionName == null) return false;

        if (ownerNickname == null) return false;

        return true;
    }
}
