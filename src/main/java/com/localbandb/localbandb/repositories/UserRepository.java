package com.localbandb.localbandb.repositories;

import com.localbandb.localbandb.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
