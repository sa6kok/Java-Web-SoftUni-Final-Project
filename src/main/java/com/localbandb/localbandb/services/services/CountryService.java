package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.data.models.Country;

import java.util.List;

public interface CountryService {
  List<String> getAllCountryNames();
  Country findByName(String name);
  void saveCities(Country country);
}
