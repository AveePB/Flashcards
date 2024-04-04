package com.dev.app.api.controller;

import com.dev.app.bll.handler.AuthHandler;
import com.dev.app.bll.handler.HandlerException;
import com.dev.app.util.dto.UserDTO;
import com.dev.app.util.token.JsonWebToken;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthHandler authHandler;

    @PostMapping("/log-in")
    private ResponseEntity<JsonWebToken> logIn(@RequestBody UserDTO userDTO) {
        try {
            JsonWebToken jwt = authHandler.logIn(userDTO);
            return ResponseEntity.ok(jwt);
        }
        catch (HandlerException ex) {
            return ResponseEntity.status(403).build();
        }
    }

    @PostMapping("/sign-up")
    private ResponseEntity<JsonWebToken> signUp(@RequestBody UserDTO userDTO) {
        try {
            JsonWebToken jwt = authHandler.signUp(userDTO);
            return ResponseEntity.ok(jwt);
        }
        catch (HandlerException ex) {
            return ResponseEntity.status(403).build();
        }
    }
}
