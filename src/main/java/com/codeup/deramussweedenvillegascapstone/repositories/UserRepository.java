package com.codeup.deramussweedenvillegascapstone.repositories;

import com.codeup.deramussweedenvillegascapstone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
    User findUserById(long id);

    User findByUsername(String username);
    User findByEmail(String email);


}
