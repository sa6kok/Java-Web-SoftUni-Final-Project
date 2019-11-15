package com.localbandb.localbandb.data.repositories;

import com.localbandb.localbandb.data.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, String> {
  List<Property> getAllByAddress_City_Name(String city);
}
