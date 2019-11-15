package com.localbandb.localbandb.data.repositories;

import com.localbandb.localbandb.data.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
}
