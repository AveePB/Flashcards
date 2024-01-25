package dev.bpeeva.flashcardsapp.dto;

import dev.bpeeva.flashcardsapp.db.constant.UserRole;
import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.util.dto.UserDTO;
import dev.bpeeva.flashcardsapp.util.mapper.UserDTOMapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserDTOTests {

    @Test
    void shouldCreateAUserDTO() {
        //Create objects.
        User user = new User(null, UserRole.USER, "Hanks", "", null);
        Optional<UserDTO> userDTO = new UserDTOMapper().apply(user);

        //Check if not null.
        assertThat(userDTO.isPresent()).isEqualTo(true);

        //Check USER ROLE.
        assertThat(userDTO.get().userRole()).isEqualTo(user.getUserRole());

        //Check USERNAME.
        assertThat(userDTO.get().username()).isEqualTo(user.getUsername());

        //Check PASSWORD.
        assertThat(userDTO.get().password()).isEqualTo(user.getPassword());
    }

    @Test
    void shouldNotCreateAUserDTO() {
        //Create objects.
        User user = new User(null, null, null, null, null);
        Optional<UserDTO> userDTO = new UserDTOMapper().apply(user);

        //Check if not null.
        assertThat(userDTO.isEmpty()).isEqualTo(true);
    }
}
