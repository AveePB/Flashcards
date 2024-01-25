package dev.bpeeva.flashcardsapp.app.service;

import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.db.repo.UserRepository;
import dev.bpeeva.flashcardsapp.util.dto.UserDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

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

        return this.userRepository.findByUsernameAndPassword(userDTO.username(), userDTO.password());
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
            User user = new User(null, userDTO.userRole(), userDTO.username(), userDTO.password(), null);

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Fetch user.
        Optional<User> user = this.userRepository.findByUsername(username);

        //Check if empty.
        if (user.isEmpty())
            throw new UsernameNotFoundException("Username not found!!!");

        return user.get();
    }
}
