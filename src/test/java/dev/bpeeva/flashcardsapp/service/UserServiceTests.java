package dev.bpeeva.flashcardsapp.service;

import dev.bpeeva.flashcardsapp.app.service.UserService;
import dev.bpeeva.flashcardsapp.db.constant.UserRole;
import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.db.repo.UserRepository;
import dev.bpeeva.flashcardsapp.util.dto.UserDTO;
import dev.bpeeva.flashcardsapp.util.mapper.UserDTOMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceTests {

    @Autowired
    public UserRepository userRepository = null;
    @Autowired
    public UserService userService = null;

    public User user;

    @BeforeEach
    void setUp() {
        //Create required user.
        this.user = new User(null, UserRole.USER, "J4c0b", "", null);

        //Save to repo.
        this.userRepository.save(this.user);
    }

    @Test
    void shouldReturnTrueWhenAskedAboutTheTakenUsername() {
        //Fetch boolean value.
        boolean isUsernameTaken = this.userService.isUsernameTaken(this.user.getUsername());

        //Check if username taken.
        assertThat(isUsernameTaken).isEqualTo(true);
    }

    @Test
    void shouldReturnFalseWhenAskedAboutTheNonTakenUsername() {
        //Fetch boolean value.
        boolean isUsernameTaken = this.userService.isUsernameTaken("R4nd0m Username");

        //Check if username taken.
        assertThat(isUsernameTaken).isEqualTo(false);
    }

    @Test
    void shouldReturnAUser() {
        //Create user DTO.
        Optional<UserDTO> userDTO = new UserDTOMapper().apply(this.user);

        //Fetch user.
        Optional<User> user = this.userService.getUser(userDTO.get());

        //Check if present.
        assertThat(user.isPresent()).isEqualTo(true);

        //Check USER ROLE.
        assertThat(user.get().getUserRole()).isEqualTo(this.user.getUserRole());

        //Check USERNAME.
        assertThat(user.get().getUsername()).isEqualTo(this.user.getUsername());

        //Check PASSWORD.
        assertThat(user.get().getPassword()).isEqualTo(this.user.getPassword());
    }

    @Test
    void shouldNotReturnAUser() {
        //Create user DTO.
        UserDTO userDTO = new UserDTO(null, null, null);

        //Fetch user.
        Optional<User> user = this.userService.getUser(userDTO);

        //Check if empty.
        assertThat(user.isEmpty()).isEqualTo(true);
    }

    @Test
    void shouldCreateANewUser() {
        //Create user DTO.
        UserDTO userDTO = new UserDTO(UserRole.USER, "Jack", "");

        //Create user.
        Optional<User> user = this.userService.createUser(userDTO);

        //Check if present.
        assertThat(user.isPresent()).isEqualTo(true);

        //Check ID.
        assertThat(user.get().getId()).isNotNull();

        //Check USER ROLE.
        assertThat(user.get().getUserRole()).isEqualTo(userDTO.userRole());

        //Check USERNAME.
        assertThat(user.get().getUsername()).isEqualTo(userDTO.username());

        //Check PASSWORD.
        assertThat(user.get().getPassword()).isEqualTo(userDTO.password());
    }

    @Test
    void shouldNotCreateANewUserWithLackingParameters() {
        //Create user DTO.
        UserDTO userDTO = new UserDTO(null, null, null);

        //Create user.
        Optional<User> user = this.userService.createUser(userDTO);

        //Check if empty.
        assertThat(user.isEmpty()).isEqualTo(true);
    }

    @Test
    void shouldDeleteAUser() {
        //Create user DTO.
        Optional<UserDTO> userDTO = new UserDTOMapper().apply(this.user);

        //Delete user.
        this.userService.deleteUser(userDTO.get());

        //Check if user exists.
        boolean isUsernameTaken = this.userService.isUsernameTaken(this.user.getUsername());
        assertThat(isUsernameTaken).isEqualTo(false);
    }

    @Test
    void shouldNotDeleteAUserWithLackingParameters() {
        //Create user DTO.
        UserDTO userDTO = new UserDTO(null, null, null);

        //Delete user.
        this.userService.deleteUser(userDTO);

        //Check if user exists.
        boolean isUsernameTaken = this.userService.isUsernameTaken(this.user.getUsername());
        assertThat(isUsernameTaken).isEqualTo(true);
    }
}
