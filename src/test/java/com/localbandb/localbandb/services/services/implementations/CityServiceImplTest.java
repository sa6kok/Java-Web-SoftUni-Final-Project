package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.data.repositories.CityRepository;
import com.localbandb.localbandb.services.services.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class CityServiceImplTest {
   City testCity;
   CityRepository mockedCityRepository;


  @BeforeEach
  public void init() {
    testCity = new City();
    testCity.setName("Hoho");
    this.mockedCityRepository = Mockito.mock(CityRepository.class);
  }
  @Test
  void save() {

  }

  @Test
  void getCityByName() {
    Mockito.when(this.mockedCityRepository.findByName("Plovdiv")).thenReturn(this.testCity);

    CityService cityService = new CityServiceImpl(this.mockedCityRepository);

    City expected = this.testCity;

    City actual = cityService.findCityByName("Plovdiv");

   assertEquals(expected.getName(), actual.getName());

  }
}