package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.services.models.PropertyServiceModel;
import com.localbandb.localbandb.web.view.models.PropertyViewModel;

import java.util.List;

public interface PropertyService {

  boolean save(PropertyServiceModel propertyServiceModel);

  List<PropertyViewModel> getAll();

  List<PropertyViewModel> getAllByCity(String city);
}
