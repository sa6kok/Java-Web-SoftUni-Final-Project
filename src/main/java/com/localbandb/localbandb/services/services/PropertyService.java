package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.services.models.PropertyServiceModel;
import com.localbandb.localbandb.web.view.models.PropertyViewModel;
import javassist.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface PropertyService {

  boolean save(PropertyServiceModel propertyServiceModel);

  List<PropertyViewModel> getAll();

  List<PropertyViewModel> getAllByCity(String city);

  PropertyServiceModel findById(String id) throws NotFoundException;

  List<LocalDate> datesBetween(String start, String end);

  List<PropertyViewModel> getAllByCityAndFilterBusyDatesAndOccupancy(String city, String startDate, String endDate, Integer occupancy);
}
