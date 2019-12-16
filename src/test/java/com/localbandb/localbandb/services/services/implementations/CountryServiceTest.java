package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.base.TestBase;
import com.localbandb.localbandb.data.models.Country;
import com.localbandb.localbandb.data.repositories.CountryRepository;
import com.localbandb.localbandb.services.models.CountryServiceModel;
import com.localbandb.localbandb.services.services.CityService;
import com.localbandb.localbandb.services.services.CountryService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CountryServiceTest extends TestBase {
  Country testCountry;

  @MockBean
  CountryRepository mockedCountryRepository;

  @MockBean
  CityService mockedCityService;

  @Autowired
  CountryService countryService;


  @Override
  public void beforeEach() {
    this.testCountry = new Country();
    this.testCountry.setName("Bulgaria");
  }



  @Test
  @WithMockUser(username="spring")
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
  @WithMockUser(username="spring")
  void findByName_whenCountryExist_shouldReturnCountry() throws NotFoundException {
   Mockito.when(mockedCountryRepository.findByName("Bulgaria")).thenReturn(testCountry);

    CountryServiceModel expected = countryService.findByName("Bulgaria");


    assertEquals(expected.getName(), testCountry.getName());
  }

  @Test
  @WithMockUser(username="spring")
  void findByName_whenCountryDoesNotExist_shouldThrow() {
    Mockito.when(mockedCountryRepository.findByName("Bulg")).thenReturn(null);

    assertThrows(NotFoundException.class , () -> countryService.findByName("Bulg"));
  }

}