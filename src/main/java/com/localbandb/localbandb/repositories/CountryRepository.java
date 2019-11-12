package com.localbandb.localbandb.repositories;

import com.localbandb.localbandb.data.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, String> {
  Country findByName(String name);
}
