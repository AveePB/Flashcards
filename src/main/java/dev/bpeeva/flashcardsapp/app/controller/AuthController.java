package dev.bpeeva.flashcardsapp.app.controller;

import dev.bpeeva.flashcardsapp.app.service.model.UserService;
import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.security.jwt.Token;
import dev.bpeeva.flashcardsapp.util.dto.UserDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Token> signUp(@RequestBody UserDTO userDTO) {
        //Create new user.
        Optional<User> user = this.userService.createUser(userDTO);

        //If operation successful.
        if (user.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new Token("NO BEARER TOKEN"));

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Token> logIn(@RequestBody UserDTO userDTO) {
        //Check credentials.
        Optional<User> user = this.userService.getUser(userDTO);

        //If operation successful.
        if (user.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new Token("NO BEARER TOKEN"));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    //PUT VALUE INTO JWT TOKEN CONSTRUCTOR!!!!!
}
