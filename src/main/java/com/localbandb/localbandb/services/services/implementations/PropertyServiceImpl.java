package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.Address;
import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.data.models.Property;
import com.localbandb.localbandb.data.repositories.PropertyRepository;
import com.localbandb.localbandb.services.models.PropertyServiceModel;
import com.localbandb.localbandb.services.services.AddressService;
import com.localbandb.localbandb.services.services.CityService;
import com.localbandb.localbandb.services.services.PropertyService;
import com.localbandb.localbandb.web.models.PropertyViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {
  private final PropertyRepository propertyRepository;
  private final AddressService addressService;
  private final CityService cityService;
  private final ModelMapper modelMapper;

  public PropertyServiceImpl(PropertyRepository propertyRepository, AddressService addressService, CityService cityService, ModelMapper modelMapper) {
    this.propertyRepository = propertyRepository;
    this.addressService = addressService;
    this.cityService = cityService;
    this.modelMapper = modelMapper;
  }

  @Override
  public boolean save(PropertyServiceModel propertyServiceModel) {
    City city = cityService.getCityByName(propertyServiceModel.getAddressServiceModel().getCity());
    Address address = modelMapper.map(propertyServiceModel.getAddressServiceModel(), Address.class);
    address.setCity(city);
    Address savedAddress = addressService.save(address);

    Property property = modelMapper.map(propertyServiceModel, Property.class);
    if(property.getPictures() == null) {
      property.setPictures(new ArrayList<>());
    }
    property.getPictures().add(propertyServiceModel.getPicture());
    property.setAddress(savedAddress);
    propertyRepository.save(property);
    return true;
  }

  @Override
  public List<PropertyViewModel> getAll() {
    List<Property> properties = propertyRepository.findAll();
    return getPropertyViewModelsFromProperty(properties);
  }


  @Override
  public List<PropertyViewModel> getAllByCity(String city) {
    List<Property> properties = propertyRepository.getAllByAddress_City_Name(city);
    return getPropertyViewModelsFromProperty(properties);
  }

  private List<PropertyViewModel> getPropertyViewModelsFromProperty(List<Property> properties) {
    return properties.stream()
        .map(p -> modelMapper.map(p, PropertyViewModel.class))
        .collect(Collectors.toList());
  }
}
