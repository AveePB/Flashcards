package com.aveepb.flashcardapp.app.auth;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    /**
     * @param request the client's request.
     * @return the json web token.
     */
    public String logIn(AuthRequest request) {

        return null;
    }

    /**
     * @param request the client's request.
     * @return the json web token.
     */
    public String signUp(AuthRequest request) {

        return null;
    }

}
