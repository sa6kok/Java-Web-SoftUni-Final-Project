package com.localbandb.localbandb.web.view.controlers;

import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.services.models.CityServiceModel;
import com.localbandb.localbandb.services.models.PropertyServiceModel;
import com.localbandb.localbandb.web.view.models.CountryViewModel;
import com.localbandb.localbandb.web.view.models.PropertyViewModel;
import com.localbandb.localbandb.services.services.CountryService;
import com.localbandb.localbandb.services.services.PropertyService;
import com.localbandb.localbandb.web.view.models.PropertyCreateModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/property")
public class PropertyController extends BaseController {
  private final CountryService countryService;
  private final PropertyService propertyService;
  private final ModelMapper modelMapper;

  @Autowired
  public PropertyController(CountryService countryService, PropertyService propertyService, ModelMapper modelMapper) {
    this.countryService = countryService;
    this.propertyService = propertyService;
    this.modelMapper = modelMapper;
  }


  @GetMapping("/create")
  public ModelAndView getCreateProperty(@ModelAttribute("propertyCreateModel") PropertyCreateModel propertyCreateModel,  ModelAndView modelAndView) {
    List<String> countries = countryService.getAllCountryNames();

    modelAndView.addObject("countries", countries);
    return this.view("property/property-create", modelAndView);
  }


  @GetMapping("/create/{country}")
  public ModelAndView getCreateProperty(@PathVariable("country") String country,@ModelAttribute PropertyCreateModel model, ModelAndView modelAndView) {

    List<CityServiceModel> cities = countryService.findByName(country).getCities().stream()
        .sorted(Comparator.comparing(CityServiceModel::getName)).collect(Collectors.toList());
    if (cities.size() == 0) {
     return this.redirect("property/create/city/{country}");
    }
    modelAndView.addObject("cities", cities);
    modelAndView.addObject("country", country);
    return this.view("property/property-create", modelAndView);
  }

  @PostMapping("/create/{country}")
  public ModelAndView postCreateProperty(@Valid @ModelAttribute PropertyCreateModel model,
                                         BindingResult bindingResult,
                                         @PathVariable("country") String country,
                                         ModelAndView modelAndView) {
    if(bindingResult.hasErrors()) {
      List<CityServiceModel> cities = countryService.findByName(country).getCities().stream()
          .sorted(Comparator.comparing(CityServiceModel::getName)).collect(Collectors.toList());
      modelAndView.addObject("cities", cities);
      return this.view("property/property-create", modelAndView);
    }
    PropertyServiceModel propertyServiceModel = modelMapper.map(model, PropertyServiceModel.class);
    propertyService.save(propertyServiceModel);
    return this.redirect("property/show/all");
  }

  @GetMapping("/create/city/{country}")
  public ModelAndView createCity(@PathVariable("country") String country, ModelAndView modelAndView, @ModelAttribute CountryViewModel countryViewModel) {


    modelAndView.addObject("country", country);

    return this.view("property/property-create-city", modelAndView);
  }


  @PostMapping("/create/city/{country}")
  public ModelAndView createCityConfirm(@PathVariable("country") String country, ModelAndView modelAndView, @ModelAttribute CountryViewModel countryViewModel) {

    countryService.addCityToCountry(country, countryViewModel.getName());

    return this.redirect("property/create/" + country, modelAndView);
  }


  @GetMapping("/show")
  public ModelAndView showProperties(@ModelAttribute("cityProperties") List<PropertyViewModel> propertyViewModels,
                                     ModelAndView modelAndView) {
    modelAndView.addObject("properties", propertyViewModels);
    return this.view("property/property-show", modelAndView);
  }

  @GetMapping("/show/all")
  public ModelAndView showProperties(ModelAndView modelAndView) {
    List<PropertyViewModel> properties = propertyService.getAll();
    modelAndView.addObject("properties", properties);
    return this.view("property/property-show", modelAndView);
  }

}
