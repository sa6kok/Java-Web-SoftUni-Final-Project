package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.data.models.Country;
import com.localbandb.localbandb.data.repositories.CountryRepository;
import com.localbandb.localbandb.services.models.CityServiceModel;
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
  public CountryServiceModel findByName(String name) throws NotFoundException {

      Country country = countryRepository.findByName(name);
      if(country == null) {
        throw new NotFoundException("Country not Found!");
      }
     return modelMapper.map(country, CountryServiceModel.class);


  }


  @Override
  public void addCityToCountry(String country, String name) throws NotFoundException {
    Country countryFromDB = countryRepository.findByName(country);
    if(country == null) {
      throw new NotFoundException("Country not Found!");
    }
    City city = new City();
    city.setCountry(countryFromDB);
    city.setName(name);
    countryFromDB.getCities().add(city);
    countryRepository.saveAndFlush(countryFromDB);

  }

  @Override
  public List<String> getOrderedCitiesForCountry(String name) throws NotFoundException {
    return this.findByName(name).getCities().stream().sorted(Comparator.comparing(CityServiceModel::getName))
        .map(CityServiceModel::getName).collect(Collectors.toList());
  }
}
