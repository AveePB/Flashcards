package com.aveepb.flashcardapp.app.auth;

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
    public ResponseEntity<AuthResponse> logIn(@RequestBody AuthRequest request) {

        String token = this.authService.logIn(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@RequestBody AuthRequest request) {

        String token = this.authService.signUp(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
