package com.localbandb.localbandb.web.controlers;

import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.data.models.Country;
import com.localbandb.localbandb.services.services.CityService;
import com.localbandb.localbandb.services.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchLocationController {
  private final CountryService countryService;
  private final CityService cityService;

  @Autowired
  public SearchLocationController(CountryService countryService, CityService cityService) {
    this.countryService = countryService;
    this.cityService = cityService;
  }

  @GetMapping("/country")
  public ModelAndView searchCountry() {
    List<String> countries = countryService.getAllCountryNames();
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("countries", countries);
    modelAndView.setViewName("search-country");
    return modelAndView;
  }

  @GetMapping("/cities")
  @ResponseBody
  public ModelAndView searchCity(@RequestParam(name = "country", required = false) String country) {
    ModelAndView modelAndView = new ModelAndView();


    return modelAndView;
  }


}
