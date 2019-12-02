package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.data.models.Country;
import com.localbandb.localbandb.data.repositories.CountryRepository;
import com.localbandb.localbandb.services.models.CountryServiceModel;
import com.localbandb.localbandb.services.services.CityService;
import com.localbandb.localbandb.services.services.CountryService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {
  private final CountryRepository countryRepository;
  private final CityService cityService;
  private final ModelMapper modelMapper;

  @Autowired
  public CountryServiceImpl(CountryRepository countryRepository, CityService cityService, ModelMapper modelMapper) {
    this.countryRepository = countryRepository;
    this.cityService = cityService;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<String> getAllCountryNames() {
    return countryRepository.findAllByOrderByName().stream().map(Country::getName).collect(Collectors.toList());
  }

  @Override
  public CountryServiceModel findByName(String name) throws PropertyReferenceException {
    Country country = countryRepository.findByName(name);
    return modelMapper.map(country, CountryServiceModel.class);
  }


  @Override
  public void addCityToCountry(String country, String name) {
    Country countryFromDB = countryRepository.findByName(country);
    City city = new City();
    city.setCountry(countryFromDB);
    city.setName(name);
    cityService.save(city);

  }

  @Override
  public List<String> getOrderedCitiesForCountry(String name) {
    return countryRepository.findByName(name).getCities().stream().sorted(Comparator.comparing(City::getName))
        .map(City::getName).collect(Collectors.toList());
  }
}
