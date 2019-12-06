package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.data.models.Country;
import com.localbandb.localbandb.data.repositories.CountryRepository;
import com.localbandb.localbandb.services.base.ServiceTestBase;
import com.localbandb.localbandb.services.models.CityServiceModel;
import com.localbandb.localbandb.services.models.CountryServiceModel;
import com.localbandb.localbandb.services.services.CityService;
import com.localbandb.localbandb.services.services.CountryService;
import com.localbandb.localbandb.services.services.implementations.CityServiceImpl;
import com.localbandb.localbandb.services.services.implementations.CountryServiceImpl;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mapping.PropertyReferenceException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CountryServiceTest extends ServiceTestBase {
  Country testCountry;

  @MockBean
  CountryRepository mockedCountryRepository;

  @MockBean
  CityService mockedCityService;

  @Autowired
  CountryService countryService;


  @Override
  protected void beforeEach() {
    this.testCountry = new Country();
    this.testCountry.setName("Bulgaria");
  }



  @Test
  void getAllCountryNames_whenThreeUnorderedCountries_shouldReturnThemOrdered() {
    List<Country> countries = new ArrayList<>();
    countries.add(new Country("Bulgaria"));
    countries.add(new Country("Angola"));
    countries.add(new Country("Qatar"));
    Mockito.when(mockedCountryRepository.findAllByOrderByName())
        .thenReturn(countries.stream().sorted(Comparator.comparing(Country::getName)).collect(Collectors.toList()));


    List<String> expectedCountyNames = countryService.getAllCountryNames();

    assertEquals(expectedCountyNames.size(), countries.size());
    assertEquals(expectedCountyNames.get(0), countries.get(1).getName());

  }

  @Test
  void findByName_whenCountryExist_shouldReturnCountry() throws NotFoundException {
   Mockito.when(mockedCountryRepository.findByName("Bulgaria")).thenReturn(testCountry);

    CountryServiceModel expected = countryService.findByName("Bulgaria");


    assertEquals(expected.getName(), testCountry.getName());
  }

  @Test
  void findByName_whenCountryDoesNotExist_shouldThrow() {
    Mockito.when(mockedCountryRepository.findByName("Bulgaria")).thenReturn(testCountry);

    assertThrows(NotFoundException.class , () -> countryService.findByName("Bulg"));
  }

}