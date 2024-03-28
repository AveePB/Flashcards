package com.dev.app.util.dto;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FlashcardDTOTests {

    @Test
    void shouldReturnTrueBecauseAllParametersAreSet() {
        //Arrange
        String motherLang = "Pies", foreignLang = "A dog", collectionName = "Animals", ownerNickname = "Mr. Pearson";
        FlashcardDTO flashcardDTO = new FlashcardDTO(motherLang, foreignLang, collectionName, ownerNickname);

        //Act
        boolean isAllSet = flashcardDTO.isAllSet();

        //Assert
        assertThat(isAllSet).isTrue();

    }

    @Test
    void shouldReturnFalseBecauseOfNullArgs() {
        //Arrange
        String motherLang = "Łóżko", foreignLang = "A bed", collectionName = "Furniture", ownerNickname = "Ms. Watson";
        FlashcardDTO flashcardDTO = new FlashcardDTO(null, foreignLang, collectionName, ownerNickname);
        FlashcardDTO flashcardDTO2 = new FlashcardDTO(motherLang, null, collectionName, ownerNickname);
        FlashcardDTO flashcardDTO3 = new FlashcardDTO(motherLang, foreignLang, null, ownerNickname);
        FlashcardDTO flashcardDTO4 = new FlashcardDTO(motherLang, foreignLang, collectionName, null);
        FlashcardDTO flashcardDTO5 = new FlashcardDTO(null, null, null, null);

        //Act
        boolean isAllSet = flashcardDTO.isAllSet();
        boolean isAllSet2 = flashcardDTO2.isAllSet();
        boolean isAllSet3 = flashcardDTO3.isAllSet();
        boolean isAllSet4 = flashcardDTO4.isAllSet();
        boolean isAllSet5 = flashcardDTO5.isAllSet();

        //Assert
        assertThat(isAllSet).isFalse();
        assertThat(isAllSet2).isFalse();
        assertThat(isAllSet3).isFalse();
        assertThat(isAllSet4).isFalse();
        assertThat(isAllSet5).isFalse();
    }
}
