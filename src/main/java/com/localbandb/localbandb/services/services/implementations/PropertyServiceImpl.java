package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.config.authentication.AuthenticationFacade;
import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.data.models.Property;
import com.localbandb.localbandb.data.models.Reservation;
import com.localbandb.localbandb.data.models.User;
import com.localbandb.localbandb.data.repositories.PropertyRepository;
import com.localbandb.localbandb.services.models.PropertyServiceModel;
import com.localbandb.localbandb.services.services.CityService;
import com.localbandb.localbandb.services.services.DateService;
import com.localbandb.localbandb.services.services.PropertyService;
import com.localbandb.localbandb.services.services.UserService;
import com.localbandb.localbandb.web.view.models.PropertyViewModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {
  private final PropertyRepository propertyRepository;
  private final CityService cityService;
  private final UserService userService;
  private final DateService dateService;
  private final ModelMapper modelMapper;
  private final AuthenticationFacade authenticationFacade;

  @Autowired
  public PropertyServiceImpl(PropertyRepository propertyRepository, CityService cityService, UserService userService, DateService dateService, ModelMapper modelMapper, AuthenticationFacade authenticationFacade) {
    this.propertyRepository = propertyRepository;
    this.cityService = cityService;
    this.userService = userService;
    this.dateService = dateService;
    this.modelMapper = modelMapper;
    this.authenticationFacade = authenticationFacade;
  }

  @Override
  @Secured("ROLE_HOST")
  public boolean save(PropertyServiceModel propertyServiceModel) throws NotFoundException {
    City city = cityService.findCityByName(propertyServiceModel.getCity());

    Property property = modelMapper.map(propertyServiceModel, Property.class);
    User user = userService.findByUsername(authenticationFacade.getAuthentication().getName());
    property.setHost(user);
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
  public PropertyViewModel findById(String id) throws NotFoundException {
    Optional<Property> byId = propertyRepository.findById(id);
    if(byId.isEmpty()) {
      throw new NotFoundException("Property not found");
    }
    Property property = byId.get();
    return modelMapper.map(property, PropertyViewModel.class);
  }

  @Override
  public List<PropertyViewModel> getAllByCityAndFilterBusyDatesAndOccupancy(String city, String startDate, String endDate, Integer occupancy) {
    List<LocalDate> busyDates = this.getDatesBetweenStartAndEndFromString(startDate, endDate);

    List<PropertyViewModel> properties = propertyRepository.findByCity_Name(city).stream()
        .map(property -> modelMapper.map(property, PropertyViewModel.class))
        .filter(p -> !p.getBusyDates().containsAll(busyDates))
        .filter(prop -> Integer.parseInt(prop.getMaxOccupancy()) >=  occupancy)
        .collect(Collectors.toList());
    return properties;
  }

  @Override
  public void addPropertyToReservation(String propertyId, Reservation model) throws NotFoundException {
    Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("Property not found"));
    List<LocalDate> dates = this.getDatesBetweenStartAndEnd(model.getStartDate(), model.getEndDate());
    property.getBusyDates().addAll(dates);
    Property savedProperty = propertyRepository.saveAndFlush(property);
    model.setProperty(savedProperty);
  }


  @Override
  public List<LocalDate> getDatesBetweenStartAndEndFromString(String start, String end) {
    LocalDate startDate = dateService.getDateFromString(start);
    LocalDate endDate = dateService.getDateFromString(end);
    return getDatesBetweenStartAndEnd(startDate, endDate);
  }

  @Override
  public List<LocalDate> getDatesBetweenStartAndEnd(LocalDate start, LocalDate end) {
    List<LocalDate> ret = new ArrayList<>();
    for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
      ret.add(date);
    }
    return ret;
  }

  @Override
  public List<String> getListDatesBetweenStartAndEndFromString(String startDate, String endDate) {
    List<String> collect = this.getDatesBetweenStartAndEndFromString(startDate, endDate).stream()
            .map(dateService::getStringFromLocalDate).collect(Collectors.toList());
    return collect;
  }

  @Override
  public String getJointlyDates(String propertyId, String startDate, String endDate) {
    List<String> requestedDates = getListDatesBetweenStartAndEndFromString(startDate, endDate);
    List<String> busyDates = propertyRepository.findById(propertyId).get().getBusyDates()
            .stream().map(dateService::getStringFromLocalDate).collect(Collectors.toList());
    System.out.printf("");
    busyDates.retainAll(requestedDates);
    if(busyDates.size() == 0) {
      return "";
    }
    List<String> collect = busyDates.stream().sorted(String::compareTo).collect(Collectors.toList());
    return String.join(", ", collect);
  }


  private List<PropertyViewModel> getPropertyViewModelsFromProperty(List<Property> properties) {
    return properties.stream()
        .map(p -> modelMapper.map(p, PropertyViewModel.class))
        .collect(Collectors.toList());
  }
}
