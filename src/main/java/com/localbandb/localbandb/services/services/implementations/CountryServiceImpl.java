package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.Country;
import com.localbandb.localbandb.repositories.CountryRepository;
import com.localbandb.localbandb.services.services.CountryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {
  private final CountryRepository countryRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper) {
    this.countryRepository = countryRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<String> getAllCountryNames() {
    return countryRepository.findAll().stream().map(Country::getName).collect(Collectors.toList());
  }

  @Override
  public Country findByName(String name) {
    return countryRepository.findByName(name);
  }

  @Override
  public void saveCities(Country country) {
    countryRepository.saveAndFlush(country);
  }
}
