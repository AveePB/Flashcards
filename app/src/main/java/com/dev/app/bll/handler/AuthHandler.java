package com.dev.app.bll.handler;

import com.dev.app.api.config.SecurityConfig;
import com.dev.app.bll.manager.JWTManager;
import com.dev.app.bll.manager.UserManager;
import com.dev.app.db.model.User;
import com.dev.app.util.dto.UserDTO;
import com.dev.app.util.token.JsonWebToken;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthHandler {

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserManager userManager;
    @Autowired
    private final JWTManager jwtManager;

    public JsonWebToken logIn(UserDTO userDTO) throws Exception {
        if (userDTO == null) throw new Exception("User Data Transfer Object is NULL...");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.username(), userDTO.password()));

        Optional<JsonWebToken> jwt = jwtManager.generateJWT(userDTO.username(), SecurityConfig.SECRET_KEY_256_BIT);
        if (jwt.isEmpty()) throw new Exception("JWT hasn't been properly generated...");

        return jwt.get();
    }

    public JsonWebToken signUp(UserDTO userDTO) throws Exception {
        if (userDTO == null) throw new Exception("User Data Transfer Object is NULL...");

        Optional<User> createdUser = userManager.createNewUser(userDTO.username(), passwordEncoder.encode(userDTO.password()));
        if (createdUser.isEmpty()) throw new Exception("Cannot create a user...");

        Optional<JsonWebToken> jwt = jwtManager.generateJWT(userDTO.username(), SecurityConfig.SECRET_KEY_256_BIT);
        if (jwt.isEmpty()) throw new Exception("JWT hasn't been properly generated...");

        return jwt.get();
    }
}
