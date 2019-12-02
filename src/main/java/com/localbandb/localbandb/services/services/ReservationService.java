package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.services.models.ReservationServiceModel;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {

  List<LocalDate> datesBetween(LocalDate start, LocalDate end);


  boolean create(ReservationServiceModel model);
}
