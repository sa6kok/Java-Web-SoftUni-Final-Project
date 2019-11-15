package com.localbandb.localbandb.web.controlers;

import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.services.models.AddressServiceModel;
import com.localbandb.localbandb.services.models.PropertyServiceModel;
import com.localbandb.localbandb.web.models.PropertyViewModel;
import com.localbandb.localbandb.services.services.CountryService;
import com.localbandb.localbandb.services.services.PropertyService;
import com.localbandb.localbandb.web.models.PropertyCreateModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
  public ModelAndView getCreateProperty(ModelAndView modelAndView) {
    List<City> cities = countryService.findByName("Bulgaria").getCities();
    modelAndView.addObject("cities", cities);
    return this.view("property/property-create", modelAndView);
  }

  @PostMapping("/create")
  public ModelAndView postCreateProperty(@ModelAttribute PropertyCreateModel model, ModelAndView modelAndView) {
    AddressServiceModel addressServiceModel = modelMapper.map(model, AddressServiceModel.class);
    PropertyServiceModel propertyServiceModel = modelMapper.map(model, PropertyServiceModel.class);
    propertyServiceModel.setAddressServiceModel(addressServiceModel);

    propertyService.save(propertyServiceModel);

    return this.redirect("property/show/all", modelAndView);
  }


  @GetMapping("/show")
  public ModelAndView showProperties(@ModelAttribute("cityProperties")List<PropertyViewModel> propertyViewModels,
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
