package com.dev.app.bll.manager;

import com.dev.app.db.model.User;
import com.dev.app.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserManager implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    public Optional<User> createNewUser(String username, String password) {
        if (username == null || password == null) return Optional.empty();

        User newUser = User.builder()
                .username(username)
                .password(password) //.password(encoder.encode(password))
                .build();

        try {
            return Optional.of(userRepository.save(newUser));
        }
        catch (DataIntegrityViolationException ex) {
            return Optional.empty();
        }
    }

    public boolean deleteUser(String username) {
        if (username == null) return false;

        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresent(u -> userRepository.deleteById(u.getId()));

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException("Username not found...");

        return user.get();
    }
}
