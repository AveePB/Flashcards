package com.aveepb.flashcardapp.web.service;

import com.aveepb.flashcardapp.db.model.User;
import com.aveepb.flashcardapp.db.repo.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = this.repository.findByUsername(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException("Username not found!");

        return user.get();
    }
}
