package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.Reservation;
import com.localbandb.localbandb.data.repositories.ReservationRepository;
import com.localbandb.localbandb.services.models.ReservationServiceModel;
import com.localbandb.localbandb.services.services.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
  private final ReservationRepository reservationRepository;
  private final ModelMapper mapper;

  public ReservationServiceImpl(ReservationRepository reservationRepository, ModelMapper mapper) {
    this.reservationRepository = reservationRepository;
    this.mapper = mapper;
  }

  @Override
  public boolean create(ReservationServiceModel model) {
    try {
      Reservation reservation = mapper.map(model, Reservation.class);
      reservationRepository.save(reservation);
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }

  @Override
  public  List<LocalDate> datesBetween(LocalDate start, LocalDate end) {
    List<LocalDate> ret = new ArrayList<>();
    for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
      ret.add(date);
    }
    return ret;
  }
}
