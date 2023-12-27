package com.aveepb.flashcardapp.db.repo;

import com.aveepb.flashcardapp.db.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
