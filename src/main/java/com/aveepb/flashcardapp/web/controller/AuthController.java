package com.aveepb.flashcardapp.web.controller;

import com.aveepb.flashcardapp.web.service.AuthService;
import com.aveepb.flashcardapp.web.conn.auth.request.AuthRequest;
import com.aveepb.flashcardapp.web.conn.auth.response.AuthResponse;
import com.aveepb.flashcardapp.web.ex.auth.IncorrectUsernameOrPassword;
import com.aveepb.flashcardapp.web.ex.auth.UsernameAlreadyTaken;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> logIn(@RequestBody AuthRequest request) throws IncorrectUsernameOrPassword {

        String token = this.authService.logIn(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@RequestBody AuthRequest request) throws UsernameAlreadyTaken {

        String token = this.authService.signUp(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
