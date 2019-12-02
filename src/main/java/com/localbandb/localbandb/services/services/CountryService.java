package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.services.models.CountryServiceModel;

import java.util.List;

public interface CountryService {

  List<String> getAllCountryNames();

  CountryServiceModel findByName(String name);

  void addCityToCountry(String country, String name);

  List<String> getOrderedCitiesForCountry(String name);
}
