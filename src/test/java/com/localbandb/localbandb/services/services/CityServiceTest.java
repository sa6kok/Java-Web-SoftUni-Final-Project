package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.data.repositories.CityRepository;
import com.localbandb.localbandb.services.base.ServiceTestBase;
import com.localbandb.localbandb.services.services.CityService;
import com.localbandb.localbandb.services.services.implementations.CityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

class CityServiceTest extends ServiceTestBase {
   City testCity;

   @MockBean
   CityRepository mockedCityRepository;

   @Autowired
    CityService cityService;


  @Override
  protected void beforeEach() {
    testCity = new City();
    testCity.setName("Hoho");
  }

  @Test
  void save() {
   // mockedCityRepository.save(this.testCity);
    cityService.save(this.testCity);
    ArgumentCaptor<City> argument = ArgumentCaptor.forClass(City.class);
    Mockito.verify(mockedCityRepository).save(argument.capture());
    City theCity = argument.getValue();
    assertEquals("Hoho", theCity.getName());

  }

  @Test
  void getCityByName() {
    Mockito.when(this.mockedCityRepository.findByName("Plovdiv")).thenReturn(this.testCity);

    City actual = cityService.findCityByName("Plovdiv");

   assertEquals(testCity.getName(), actual.getName());

  }
}