package com.localbandb.localbandb.web.view.controlers;

import com.localbandb.localbandb.services.services.CountryService;
import com.localbandb.localbandb.services.services.PropertyService;
import com.localbandb.localbandb.web.view.models.PropertyViewModel;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchLocationController extends BaseController {
  private final CountryService countryService;
  private final PropertyService propertyService;

  @Autowired
  public SearchLocationController(CountryService countryService, PropertyService propertyService) {
    this.countryService = countryService;
    this.propertyService = propertyService;
  }

  @GetMapping("/country")
  public ModelAndView searchCountry(ModelAndView modelAndView) {
    List<String> countries = countryService.getAllCountryNames();
    modelAndView.addObject("countries", countries);

    return this.view("location/search-location", modelAndView);
  }

  @GetMapping("/country/{country}")
  public ModelAndView searchCountry(@PathVariable String country, ModelAndView modelAndView) throws NotFoundException {
    List<String> cities = countryService.getOrderedCitiesForCountry(country);
    modelAndView.addObject("cities", cities);
    modelAndView.addObject("currentCountry", country);
    return this.view("location/search-location", modelAndView);
  }

  @GetMapping("/country/{country}/{city}")
  public ModelAndView searchCountry(@PathVariable String country, @PathVariable String city, ModelAndView modelAndView, RedirectAttributes attributes) throws NotFoundException {
    List<String> cities = countryService.getOrderedCitiesForCountry(country);
    modelAndView.addObject("cities", cities);
    List<PropertyViewModel> cityProperties = propertyService.getAllByCity(city);
    modelAndView.addObject("properties", cityProperties);
    modelAndView.addObject("message", "Available locations in " + city );
    return this.view("property/property-show", modelAndView);
  }

}
