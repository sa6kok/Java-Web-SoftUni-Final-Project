package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.data.repositories.CityRepository;
import com.localbandb.localbandb.services.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CityServiceImpl implements CityService {
  private final CityRepository cityRepository;

  @Autowired
  public CityServiceImpl(CityRepository cityRepository) {
    this.cityRepository = cityRepository;
  }

  @Override
  public void save(City city) {
    if (city.getProperties() == null) {
      city.setProperties(new ArrayList<>());
    }

    cityRepository.save(city);
  }

  @Override
  public City findCityByName(String name) {
    return cityRepository.findByName(name);
  }
}
