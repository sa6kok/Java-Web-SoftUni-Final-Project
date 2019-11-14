package com.localbandb.localbandb.web.controlers;

import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.data.models.Country;
import com.localbandb.localbandb.data.repositories.CityRepository;
import com.localbandb.localbandb.data.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController extends BaseController {
  private final CountryRepository countryRepository;
  private final CityRepository cityRepository;

  @Autowired
  public IndexController(CountryRepository countryRepository, CityRepository cityRepository) {
    this.countryRepository = countryRepository;
    this.cityRepository = cityRepository;
  }

  @GetMapping("/")
  public ModelAndView showIndex()  {

    return this.view("home/index");
  }
}
