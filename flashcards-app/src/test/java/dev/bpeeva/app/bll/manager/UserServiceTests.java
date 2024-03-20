package dev.bpeeva.app.bll.manager;

import dev.bpeeva.app.db.model.User;
import dev.bpeeva.app.db.repo.UserRepository;
import dev.bpeeva.app.util.dto.UserDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private final UserRepository userRepository = null;
    @Autowired
    private final UserService userService = null;

    @Test
    void shouldSuccessfullyReturnAUser() {
        this.userRepository.save(new User(null, "Jacek", ",sdfjsd", null));

        Optional<User> user = this.userService.getUser("Jacek");
        assertThat(user.isPresent()).isTrue();

        assertThat(user.get().getUsername()).isEqualTo("Jacek");
        assertThat(user.get().getPassword()).isEqualTo(",sdfjsd");

        this.userRepository.delete(user.get());
    }

    @Test
    void shouldReturnBeEmptyInsteadOfAUser() {
        Optional<User> user = this.userService.getUser("Andrzej");
        assertThat(user.isPresent()).isFalse();
    }

    @Test
    void shouldSuccessfullyCreateANewUser() {
        Optional<User> user = this.userService.createNewUser(new UserDTO("Xavier", "xjhi24"));
        assertThat(user.isPresent()).isTrue();

        Optional<User> fetchedUser = this.userService.getUser("Xavier");
        assertThat(fetchedUser.isPresent()).isTrue();

        assertThat(user.get().getId()).isEqualTo(fetchedUser.get().getId());
        assertThat(user.get().getUsername()).isEqualTo(fetchedUser.get().getUsername());
        assertThat(user.get().getPassword()).isEqualTo(fetchedUser.get().getPassword());

        this.userRepository.delete(user.get());
    }

    @Test
    void shouldNotCreateAUserBecauseOfATakenUsername() {
        User savedUser = this.userRepository.save(new User(null, "Eugeniusz", "ij4sfd", null));

        Optional<User> user = this.userService.createNewUser(new UserDTO("Eugeniusz", "f24x"));
        assertThat(user.isPresent()).isFalse();

        this.userRepository.delete(savedUser);
    }
}
