package dev.bpeeva.app.bll.manager;

import dev.bpeeva.app.db.model.User;
import dev.bpeeva.app.db.repo.UserRepository;
import dev.bpeeva.app.util.dto.UserDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    //private final PasswordEncoder passwordEncoder; <- ADD THIS LATER ON...
    private final UserRepository userRepository;

    public Optional<User> getUser(String username) {

        return this.userRepository.findByUsername(username);
    }

    public Optional<User> createNewUser(UserDTO userDTO) {
        boolean isUsernameTaken = getUser(userDTO.username()).isPresent();

        if (isUsernameTaken)
            return Optional.empty();

        User user = User.builder()
                .username(userDTO.username())
                .password(userDTO.password())
                .build();

        return Optional.of(this.userRepository.save(user));
    }
}
