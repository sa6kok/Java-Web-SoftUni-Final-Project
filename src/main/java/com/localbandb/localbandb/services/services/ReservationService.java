package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.data.models.Reservation;
import com.localbandb.localbandb.web.view.models.ReservationCreateModel;
import com.localbandb.localbandb.web.view.models.ReservationViewModel;
import javassist.NotFoundException;

import java.util.List;

public interface ReservationService {

    boolean create(String propertyId, ReservationCreateModel model);

    Reservation save(Reservation reservation);

    ReservationCreateModel fillUpModel(String id, String start, String end, String pax) throws NotFoundException;

    ReservationCreateModel fillUpModel(String id) throws NotFoundException;

    void addPaymentToReservation(Reservation savedReservation, String totalPrice) throws NotFoundException;

    List<ReservationViewModel> findReservationsForUserWithFilter(String filter);

    boolean payReservation(String id);

    boolean cancelReservation(String id);

    Reservation findById(String reservationId) throws NotFoundException;

    void findReservationsToCancelAndCancelAutomaticScheduled();
}
