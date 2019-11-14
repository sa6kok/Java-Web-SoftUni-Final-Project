package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.data.models.City;

import java.util.List;

public interface CityService {
  void save(City city);
  City getCityByName(String name);
}
