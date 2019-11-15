package com.localbandb.localbandb.data.repositories;

import com.localbandb.localbandb.data.models.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository extends JpaRepository<Host, String> {
  Host findByUsernameAndPassword(String username, String password);
}
