package com.localbandb.localbandb.data.repositories;

import com.localbandb.localbandb.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  User findByUsernameAndPassword(String username, String password);

  User findByUsername(String username);

  User findByEmail(String email);
}
