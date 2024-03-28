package com.dev.app.util.dto;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserDTOTests {

    @Test
    void shouldReturnTrueBecauseEveryParameterIsSet() {
        //Arrange
        String username = "Adam", password = "SH(WY$#GFDBD";
        UserDTO userDTO = new UserDTO(username, password);

        //Act
        boolean isAllSet = userDTO.isAllSet();

        //Assert
        assertThat(isAllSet).isTrue();
    }

    @Test
    void shouldReturnFalseBecauseOfNullArgs() {
        //Arrange
        String username = "Kuba", password = "FHDS(*WGEGR";
        UserDTO userDTO = new UserDTO(username, null);
        UserDTO userDTO2 = new UserDTO(null, password);
        UserDTO userDTO3 = new UserDTO(null, null);

        //Act
        boolean isAllSet = userDTO.isAllSet();
        boolean isAllSet2 = userDTO2.isAllSet();
        boolean isAllSet3 = userDTO3.isAllSet();

        //Assert
        assertThat(isAllSet).isFalse();
        assertThat(isAllSet2).isFalse();
        assertThat(isAllSet3).isFalse();
    }
}
