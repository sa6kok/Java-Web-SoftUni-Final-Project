package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.data.models.Property;
import com.localbandb.localbandb.data.repositories.PropertyRepository;
import com.localbandb.localbandb.services.models.PropertyServiceModel;
import com.localbandb.localbandb.services.services.CityService;
import com.localbandb.localbandb.services.services.PropertyService;
import com.localbandb.localbandb.services.services.ReservationService;
import com.localbandb.localbandb.web.view.models.PropertyViewModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {
  private final PropertyRepository propertyRepository;
  private final CityService cityService;
  private final ModelMapper modelMapper;

  @Autowired
  public PropertyServiceImpl(PropertyRepository propertyRepository, CityService cityService, ModelMapper modelMapper) {
    this.propertyRepository = propertyRepository;
    this.cityService = cityService;
    this.modelMapper = modelMapper;
  }

  @Override
  public boolean save(PropertyServiceModel propertyServiceModel) {
    City city = cityService.findCityByName(propertyServiceModel.getCity());

    Property property = modelMapper.map(propertyServiceModel, Property.class);
    property.setCity(city);
    if(property.getPictures() == null) {
      property.setPictures(new ArrayList<>());
    }
    if(property.getBusyDates() == null) {
      property.setBusyDates(new ArrayList<>());
    }
    if(property.getReservations() == null) {
      property.setReservations(new ArrayList<>());
    }
    if(property.getReservationWithoutUsers() == null) {
      property.setReservationWithoutUsers(new ArrayList<>());
    }
    property.getPictures().add(propertyServiceModel.getPictureUrl());
    try {
      propertyRepository.save(property);
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }

  @Override
  public List<PropertyViewModel> getAll() {
    List<Property> properties = propertyRepository.findAll();
    return getPropertyViewModelsFromProperty(properties);
  }


  @Override
  public List<PropertyViewModel> getAllByCity(String city) {
    List<Property> properties = propertyRepository.findByCity_Name(city);
    return getPropertyViewModelsFromProperty(properties);
  }

  @Override
  public PropertyServiceModel findById(String id) throws NotFoundException {
    Optional<Property> byId = propertyRepository.findById(id);
    if(byId.isEmpty()) {
      throw new NotFoundException("Property not found");
    }
    Property property = byId.get();
    return modelMapper.map(property, PropertyServiceModel.class);
  }

  @Override
  public List<PropertyViewModel> getAllByCityAndFilterBusyDatesAndOccupancy(String city, String startDate, String endDate, Integer occupancy) {
    List<LocalDate> busyDates = this.datesBetween(startDate, endDate);

    List<PropertyViewModel> properties = propertyRepository.findByCity_Name(city).stream()
        .map(property -> modelMapper.map(property, PropertyViewModel.class))
        .filter(p -> !p.getBusyDates().containsAll(busyDates))
        .filter(prop -> Integer.parseInt(prop.getMaxOccupancy()) >=  occupancy)
        .collect(Collectors.toList());
    return properties;
  }


  @Override
  public List<LocalDate> datesBetween(String start, String end) {
    LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern("d.MM.yyyy"));
    LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ofPattern("d.MM.yyyy"));
    List<LocalDate> ret = new ArrayList<>();
    for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
      ret.add(date);
    }
    return ret;
  }


  private List<PropertyViewModel> getPropertyViewModelsFromProperty(List<Property> properties) {
    return properties.stream()
        .map(p -> modelMapper.map(p, PropertyViewModel.class))
        .collect(Collectors.toList());
  }
}
