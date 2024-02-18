package dev.bpeeva.flashcardsapp.app.service.dummy;

import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DummyUserService implements UserDetailsService {

    private final UserRepository userRepository;

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
