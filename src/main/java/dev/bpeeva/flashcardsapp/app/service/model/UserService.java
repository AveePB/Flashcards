package dev.bpeeva.flashcardsapp.app.service.model;

import dev.bpeeva.flashcardsapp.db.constant.UserRole;
import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.db.repo.UserRepository;
import dev.bpeeva.flashcardsapp.util.dto.UserDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    /**
     * Checks if the username is already taken.
     * @param username the username.
     * @return the boolean value.
     */
    public boolean isUsernameTaken(String username) {

        return this.userRepository.findByUsername(username).isPresent();
    }

    /**
     * Fetches the user based on a dto.
     * @param userDTO the user data transfer object.
     * @return the optional object.
     */
    public Optional<User> getUser(UserDTO userDTO) {

        return getUser(userDTO.username());
    }

    /**
     * Fetches the user based on a username
     * @param username the username.
     * @return the optional object.
     */
    public Optional<User> getUser(String username) {

        return this.userRepository.findByUsername(username);
    }

    /**
     * Creates a new user in the database.
     * @param userDTO th user data transfer object.
     * @return the optional object.
     */
    public Optional<User> createUser(UserDTO userDTO) {
        //Check if not null and username not taken.
        if (userDTO.isNotNull() && !this.isUsernameTaken(userDTO.username())) {
            //Create user object.
            User user = User.builder()
                    .userRole(UserRole.USER)
                    .username(userDTO.username())
                    .password(this.passwordEncoder.encode(userDTO.password()))
                    .build();

            //Save user.
            return Optional.of(this.userRepository.save(user));
        }

        return Optional.empty();
    }

    /**
     * Deletes the user based on a dto.
     * @param userDTO
     */
    public void deleteUser(UserDTO userDTO) {
        //Fetch user.
        Optional<User> user = this.getUser(userDTO);

        //Check if present.
        if (user.isPresent()) {
            //Delete user.
            this.userRepository.delete(user.get());
        }
    }
}
