package com.aveepb.flashcardapp.web.service;

import com.aveepb.flashcardapp.web.conn.auth.AuthRequest;
import com.aveepb.flashcardapp.web.ex.auth.IncorrectUsernameOrPassword;
import com.aveepb.flashcardapp.web.ex.auth.UsernameAlreadyTaken;
import com.aveepb.flashcardapp.db.model.User;
import com.aveepb.flashcardapp.db.repo.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     * @param request the client's request.
     * @return the json web token.
     */
    public String logIn(AuthRequest request) throws IncorrectUsernameOrPassword {
        //Prepare crucial variables.
        String encodedPassword = this.passwordEncoder.encode(request.getPassword());
        Optional<User> user = this.userRepository.findByUsernameAndPassword(request.getUsername(), encodedPassword);

        //Check if user doesn't exist.
        if (user.isEmpty())
            throw new IncorrectUsernameOrPassword();

        return "TOKEN";
    }

    /**
     * @param request the client's request.
     * @return the json web token.
     */
    public String signUp(AuthRequest request) throws UsernameAlreadyTaken {
        //Prepare crucial variables.
        String encodedPassword = this.passwordEncoder.encode(request.getPassword());
        Optional<User> user = this.userRepository.findByUsername(request.getUsername());

        //Check if user exists.
        if (user.isPresent())
            throw new UsernameAlreadyTaken();

        User newUser = User.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .build();

        this.userRepository.save(newUser);
        return "TOKEN";
    }
}
