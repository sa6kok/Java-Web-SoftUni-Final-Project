package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.repositories.CityRepository;
import com.localbandb.localbandb.services.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {
  private final CityRepository cityRepository;

  @Autowired
  public CityServiceImpl(CityRepository cityRepository) {
    this.cityRepository = cityRepository;
  }

  public CityRepository getCityRepository() {
    return cityRepository;
  }

  @Override
  public void save(City city) {
    cityRepository.save(city);
  }

  @Override
  public City getCityByName(String name) {
    return cityRepository.findByName(name);
  }
}
