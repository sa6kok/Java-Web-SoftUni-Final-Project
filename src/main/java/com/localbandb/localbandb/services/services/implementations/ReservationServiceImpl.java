package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.Reservation;
import com.localbandb.localbandb.data.repositories.ReservationRepository;
import com.localbandb.localbandb.services.services.DateService;
import com.localbandb.localbandb.services.services.PropertyService;
import com.localbandb.localbandb.services.services.ReservationService;
import com.localbandb.localbandb.services.services.UserService;
import com.localbandb.localbandb.web.view.models.PropertyViewModel;
import com.localbandb.localbandb.web.view.models.ReservationCreateModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {
  private final PropertyService propertyService;
  private final DateService dateService;
  private final UserService userService;
  private final ReservationRepository reservationRepository;
  private final ModelMapper mapper;

  @Autowired
  public ReservationServiceImpl(PropertyService propertyService, DateService dateService, UserService userService, ReservationRepository reservationRepository, ModelMapper mapper) {
    this.propertyService = propertyService;
    this.dateService = dateService;
    this.userService = userService;
    this.reservationRepository = reservationRepository;
    this.mapper = mapper;
  }


  @Override
  public boolean create(String propertyId, ReservationCreateModel model) {
    System.out.printf("");
    try {
      Reservation reservation = mapper.map(model, Reservation.class);
      reservation.setStartDate(dateService.getDateFromString(model.getStartDate()));
      reservation.setEndDate(dateService.getDateFromString(model.getEndDate()));
      propertyService.addPropertyToReservation(propertyId, reservation);
      userService.addUserToReservation(reservation);
      reservationRepository.save(reservation);
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }

  @Override
  @PreAuthorize("isAuthenticated()")
  public ReservationCreateModel fillUpModel(String id, String start, String end, String pax) throws NotFoundException {
    PropertyViewModel propertyServiceById = propertyService.findById(id);
    ReservationCreateModel reservationCreateModel = new ReservationCreateModel();
    reservationCreateModel.setStartDate(start);
    reservationCreateModel.setEndDate(end);
    reservationCreateModel.setOccupancy(Integer.parseInt(pax));
    reservationCreateModel.setPropertyViewModel(propertyServiceById);
    return reservationCreateModel;
  }

  @Override
  @PreAuthorize("isAuthenticated()")
  public ReservationCreateModel fillUpModel(String id) throws NotFoundException {
    PropertyViewModel propertyServiceById = propertyService.findById(id);
    ReservationCreateModel reservationCreateModel = new ReservationCreateModel();
    reservationCreateModel.setPropertyViewModel(propertyServiceById);
    return reservationCreateModel;
  }

}
