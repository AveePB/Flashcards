package dev.bpeeva.app.db.repo;

import dev.bpeeva.app.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //custom methods...
}
