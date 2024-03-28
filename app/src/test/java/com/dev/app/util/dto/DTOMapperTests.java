package com.dev.app.util.dto;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DTOMapperTests {

    @Test
    void shouldSuccessfullyMapToUserDTO() {
        //Arrange
        String username = "Adam", password = "HRWUO&*$GR$";

        //Act
        Optional<UserDTO> userDTO = DTOMapper.mapUser(username, password);

        //Assert
        assertThat(userDTO.isPresent()).isTrue();
    }

    @Test
    void shouldFailTMapToUserDTOBecauseOfNullArgs() {
        //Arrange
        String username = "Jacob", password = "JF(W*$YYTF";

        //Act
        Optional<UserDTO> userDTO = DTOMapper.mapUser(null, password);
        Optional<UserDTO> userDTO2 = DTOMapper.mapUser(username, null);
        Optional<UserDTO> userDTO3 = DTOMapper.mapUser(null, null);

        //Assert
        assertThat(userDTO.isPresent()).isFalse();
        assertThat(userDTO2.isPresent()).isFalse();
        assertThat(userDTO3.isPresent()).isFalse();
    }

    @Test
    void shouldSuccessfullyMapToFlashcardCollectionDTO() {
        //Arrange
        String collectionName = "Footballers", ownerNickname = "Lionel Messi";

        //Act
        Optional<FlashcardCollectionDTO> flashcardCollectionDTO = DTOMapper.mapFlashcardCollection(collectionName, ownerNickname);

        //Assert
        assertThat(flashcardCollectionDTO.isPresent()).isTrue();
    }

    @Test
    void shouldFailToMapToFlashcardCollectionDTOBecauseOfNullArgs() {
        //Arrange
        String collectionName = "Songs", ownerNickname = "Travis Scott";

        //Act
        Optional<FlashcardCollectionDTO> flashcardCollectionDTO = DTOMapper.mapFlashcardCollection(null, ownerNickname);
        Optional<FlashcardCollectionDTO> flashcardCollectionDTO2 = DTOMapper.mapFlashcardCollection(collectionName, null);
        Optional<FlashcardCollectionDTO> flashcardCollectionDTO3 = DTOMapper.mapFlashcardCollection(null, null);

        //Assert
        assertThat(flashcardCollectionDTO.isPresent()).isFalse();
        assertThat(flashcardCollectionDTO2.isPresent()).isFalse();
        assertThat(flashcardCollectionDTO3.isPresent()).isFalse();
    }

    @Test
    void shouldSuccessfullyMapToFlashcardDTO() {
        //Arrange
        String motherLang = "Niebieskie światło", foreignLang = "A blue light", collectionName = "Spells", ownerNickname = "Fellow Traveler";

        //Act
        Optional<FlashcardDTO> flashcardDTO = DTOMapper.mapFlashcard(motherLang, foreignLang, collectionName, ownerNickname);

        //Assert
        assertThat(flashcardDTO.isPresent()).isTrue();
    }

    @Test
    void shouldFailToMapToFlashcardDTOBecauseOfNullArgs() {
        //Arrange
        String motherLang = "Łopata", foreignLang = "A shovel", collectionName = "Tools", ownerNickname = "Builder Jack";

        //Act
        Optional<FlashcardDTO> flashcardDTO = DTOMapper.mapFlashcard(null, foreignLang, collectionName, ownerNickname);
        Optional<FlashcardDTO> flashcardDTO2 = DTOMapper.mapFlashcard(motherLang, null, collectionName, ownerNickname);
        Optional<FlashcardDTO> flashcardDTO3 = DTOMapper.mapFlashcard(motherLang, foreignLang, null, ownerNickname);
        Optional<FlashcardDTO> flashcardDTO4 = DTOMapper.mapFlashcard(motherLang, foreignLang, collectionName, null);
        Optional<FlashcardDTO> flashcardDTO5 = DTOMapper.mapFlashcard(null, null, null, null);

        //Assert
        assertThat(flashcardDTO.isPresent()).isFalse();
        assertThat(flashcardDTO2.isPresent()).isFalse();
        assertThat(flashcardDTO3.isPresent()).isFalse();
        assertThat(flashcardDTO4.isPresent()).isFalse();
        assertThat(flashcardDTO5.isPresent()).isFalse();
    }
}
