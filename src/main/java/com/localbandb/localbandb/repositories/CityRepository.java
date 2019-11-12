package com.localbandb.localbandb.repositories;

import com.localbandb.localbandb.data.models.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, String> {
  City findByName(String name);
}
