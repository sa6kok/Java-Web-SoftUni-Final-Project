package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.services.models.CountryServiceModel;
import javassist.NotFoundException;

import java.util.List;

public interface CountryService {

  List<String> getAllCountryNames();

  CountryServiceModel findByName(String name) throws NotFoundException;

  void addCityToCountry(String country, String name) throws NotFoundException;

  List<String> getOrderedCitiesForCountry(String name) throws NotFoundException;
}
