package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.data.models.Property;
import com.localbandb.localbandb.services.models.ReservationServiceModel;
import com.localbandb.localbandb.web.view.models.ReservationCreateModel;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

  boolean create(ReservationServiceModel model);

  ReservationCreateModel fillUpModel( String id,  String start, String end,  String pax) throws NotFoundException;

}