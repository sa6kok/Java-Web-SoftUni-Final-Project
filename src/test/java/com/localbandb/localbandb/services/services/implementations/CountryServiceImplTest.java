package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.Country;
import com.localbandb.localbandb.data.repositories.CountryRepository;
import com.localbandb.localbandb.services.models.CityServiceModel;
import com.localbandb.localbandb.services.models.CountryServiceModel;
import com.localbandb.localbandb.services.services.CityService;
import com.localbandb.localbandb.services.services.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.data.mapping.PropertyReferenceException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CountryServiceImplTest {
  Country testCountry;
  CountryRepository mockedCountryRepository;
  CityService mockedCityService;
  ModelMapper mockedModelMapper;

  @BeforeEach
  void init() {
    this.testCountry = new Country();
    this.testCountry.setName("Bulgaria");
    this.mockedCountryRepository = Mockito.mock(CountryRepository.class);
    this.mockedCityService = Mockito.mock(CityServiceImpl.class);
    this.mockedModelMapper = new ModelMapper();

  }

  @Test
  void getAllCountryNames_whenThreeUnorderedCountries_shouldReturnThemOrdered() {
    List<Country> countries = new ArrayList<>();
    countries.add(new Country("Bulgaria"));
    countries.add(new Country("Angola"));
    countries.add(new Country("Qatar"));
    Mockito.when(mockedCountryRepository.findAllByOrderByName())
        .thenReturn(countries.stream().sorted(Comparator.comparing(Country::getName)).collect(Collectors.toList()));

    CountryService countryService = new CountryServiceImpl(mockedCountryRepository, mockedCityService, mockedModelMapper);

    List<String> expectedCountyNames = countryService.getAllCountryNames();

    assertEquals(expectedCountyNames.size(), countries.size());
    assertEquals(expectedCountyNames.get(0), countries.get(1).getName());

  }

  @Test
  void findByName_whenCountryExist_shouldReturnCountry() {
   Mockito.when(mockedCountryRepository.findByName("Bulgaria")).thenReturn(testCountry);

    CountryService countryService = new CountryServiceImpl(mockedCountryRepository, mockedCityService, mockedModelMapper);
    CountryServiceModel expected = countryService.findByName("Bulgaria");


    assertEquals(expected.getName(), testCountry.getName());
  }

  @Test
  void findByName_whenCountryDoesNotExist_shouldThrow() {
    Mockito.when(mockedCountryRepository.findByName("Bulgaria")).thenReturn(testCountry);

    CountryService countryService = new CountryServiceImpl(mockedCountryRepository, mockedCityService, mockedModelMapper);


    assertThrows(IllegalArgumentException.class , () -> countryService.findByName("Bulg"));
  }

  @Test
  void addCityToCountry() {
  }
}