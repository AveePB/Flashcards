package com.dev.app.util.dto;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FlashcardCollectionDTOTests {

    @Test
    void shouldReturnTrueBecauseEveryParameterIsSet() {
        //Arrange
        String collectionName = "Animals", ownerNickname = "Phd. Greenwooder";
        FlashcardCollectionDTO flashcardCollectionDTO = new FlashcardCollectionDTO(collectionName, ownerNickname);

        //Act
        boolean isAllSet = flashcardCollectionDTO.isAllSet();

        //Assert
        assertThat(isAllSet).isTrue();
    }

    @Test
    void shouldReturnFalseBecauseOfNullArgs() {
        //Arrange
        String collectionName = "Tools", ownerNickname = "Super Builder";
        FlashcardCollectionDTO flashcardCollectionDTO = new FlashcardCollectionDTO(collectionName, null);
        FlashcardCollectionDTO flashcardCollectionDTO2 = new FlashcardCollectionDTO(null, ownerNickname);
        FlashcardCollectionDTO flashcardCollectionDTO3 = new FlashcardCollectionDTO(null, null);

        //Act
        boolean isAllSet = flashcardCollectionDTO.isAllSet();
        boolean isAllSet2 = flashcardCollectionDTO2.isAllSet();
        boolean isAllSet3 = flashcardCollectionDTO3.isAllSet();

        //Assert
        assertThat(isAllSet).isFalse();
        assertThat(isAllSet2).isFalse();
        assertThat(isAllSet3).isFalse();
    }
}
