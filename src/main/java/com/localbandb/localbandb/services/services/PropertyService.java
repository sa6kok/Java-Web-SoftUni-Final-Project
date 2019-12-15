package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.data.models.Reservation;
import com.localbandb.localbandb.services.models.PropertyServiceModel;
import com.localbandb.localbandb.web.view.models.PropertyViewModel;
import com.localbandb.localbandb.web.view.models.ReservationCreateModel;
import javassist.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface PropertyService {

    boolean save(PropertyServiceModel propertyServiceModel) throws NotFoundException;

    List<PropertyViewModel> getAll();

    List<PropertyViewModel> getAllByCity(String city);

    PropertyViewModel findById(String id) throws NotFoundException;

    void addPropertyToReservation(String propertyId, Reservation model) throws NotFoundException;

    List<LocalDate> getDatesBetweenStartAndEndFromString(String start, String end);

    List<PropertyViewModel> getAllByCityAndFilterBusyDatesAndOccupancy(String city, String startDate, String endDate, Integer occupancy);

    List<LocalDate> getDatesBetweenStartAndEnd(LocalDate start, LocalDate end);

    List<String> getListDatesBetweenStartAndEndFromString(String startDate, String endDate);

  String getJointlyDates(String propertyId, String startDate, String endDate);
}

