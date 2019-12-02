package com.localbandb.localbandb.utils;

import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.data.models.Country;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public class ImportData {
/*
public static String[] countries = new String[]{
     "Austria","Italy",
     "Belgium","Latvia",
     "Bulgaria",	"Lithuania",
     "Croatia"	,"Luxembourg",
     "Cyprus"	,"Malta",
     "Czechia",	"Netherlands",
     "Denmark",	"Poland",
     "Estonia",	"Portugal",
     "Finland",	"Romania",
     "France"	,"Slovakia",
     "Germany",	"Slovenia",
     "Greece",	"Spain",
     "Hungary",	"Sweden",
     "Ireland",	"United Kingdom"
 };
*/

 /*for (String country : ImportData.countries) {
    Country current = new Country();
    current.setName(country);
    countryRepository.save(current);
  }*/

 /* public static String[] cities = new String[] {
      "Assenovgrad", "Plovdiv", "Sofia", "Pleven","Burgas","Varna","Sunny Beach", "Albena", "Arapya","Vidin"
  };*/
/* for (String city : ImportData.cities) {
    Country country = countryRepository.findByName("Bulgaria");
    City cityNew = new City();
    cityNew.setName(city);
    cityNew.setCountry(country);

    cityRepository.save(cityNew);
  }*/
}
