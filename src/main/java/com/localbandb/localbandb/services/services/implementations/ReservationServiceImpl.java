package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.config.authentication.AuthenticationFacade;
import com.localbandb.localbandb.data.models.Payment;
import com.localbandb.localbandb.data.models.Reservation;
import com.localbandb.localbandb.data.repositories.ReservationRepository;
import com.localbandb.localbandb.services.services.*;
import com.localbandb.localbandb.web.view.models.PropertyViewModel;
import com.localbandb.localbandb.web.view.models.ReservationCreateModel;
import com.localbandb.localbandb.web.view.models.ReservationViewModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final PropertyService propertyService;
    private final DateService dateService;
    private final UserService userService;
    private final PaymentService paymentService;
    private final ReservationRepository reservationRepository;
    private final AuthenticationFacade authenticationFacade;
    private final ModelMapper mapper;

    @Autowired
    public ReservationServiceImpl(PropertyService propertyService, DateService dateService, UserService userService, PaymentService paymentService, ReservationRepository reservationRepository, AuthenticationFacade authenticationFacade, ModelMapper mapper) {
        this.propertyService = propertyService;
        this.dateService = dateService;
        this.userService = userService;
        this.paymentService = paymentService;
        this.reservationRepository = reservationRepository;
        this.authenticationFacade = authenticationFacade;
        this.mapper = mapper;
    }


    @Override
    public boolean create(String propertyId, ReservationCreateModel model) {

        try {
            Reservation reservation = mapper.map(model, Reservation.class);
            reservation.setStartDate(dateService.getDateFromString(model.getStartDate()));
            reservation.setEndDate(dateService.getDateFromString(model.getEndDate()));
            propertyService.addPropertyToReservation(propertyId, reservation);
            userService.addUserToReservation(reservation);
            reservation.setPayment(new ArrayList<>());
            Reservation savedReservation = reservationRepository.save(reservation);

            if (model.isCheckPayment()) {
                this.addPaymentToReservation(savedReservation, model.getTotalPrice());
                Reservation reservationToCheck = reservationRepository.findById(savedReservation.getId()).get();
                if (this.checkIfItIsPayed(reservationToCheck)) {
                    reservationToCheck.setPayed(true);
                    reservationRepository.saveAndFlush(reservationToCheck);
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void save(Reservation reservation) {
        reservationRepository.saveAndFlush(reservation);
    }

    @Override
    @Secured("ROLE_GUEST")
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
    @Secured("ROLE_GUEST")
    public ReservationCreateModel fillUpModel(String id) throws NotFoundException {
        PropertyViewModel propertyServiceById = propertyService.findById(id);
        ReservationCreateModel reservationCreateModel = new ReservationCreateModel();
        reservationCreateModel.setPropertyViewModel(propertyServiceById);
        return reservationCreateModel;
    }

    @Override
    public void addPaymentToReservation(Reservation savedReservation, String totalPrice) throws NotFoundException {
        paymentService.createPayment(savedReservation);
    }

    @Override
    public List<ReservationViewModel> findReservationsForUserWithFilter(String filter) {
        List<Reservation> reservation = new ArrayList<>();
        String username = authenticationFacade.getAuthentication().getName();

        switch (filter) {
            case "payed":
                reservation = reservationRepository.findAllByGuest_Username_AndPayedOrderByStartDateAsc(username, true);
                break;
            case "notPayed":
                reservation = reservationRepository.findAllByGuest_Username_AndPayedOrderByStartDateAsc(username, false);
                break;
            case "all":
                reservation = reservationRepository.findAllByGuest_UsernameOrderByStartDateAsc(username);
                break;
            case "future":
                reservation = reservationRepository.findAllByGuest_Username_AndPastOrderByEndDateAsc(username, false);
                break;
            case "past":
                reservation = reservationRepository.findAllByGuest_Username_AndPastOrderByEndDateAsc(username, true);
                break;
            case "canceled":
                reservation = reservationRepository.findAllByGuest_Username_AndCanceledOrderByStartDateAsc(username, true);
                break;
        }
        return getReservationViewModelsFromReservation(reservation);
    }

    private List<ReservationViewModel> getReservationViewModelsFromReservation(List<Reservation> reservationRepositoryAllByGuestUsername) {
        return reservationRepositoryAllByGuestUsername .stream()
                .map(r ->{
                    PropertyViewModel pvm = mapper.map(r.getProperty(), PropertyViewModel.class);
                    ReservationViewModel rvm = mapper.map(r, ReservationViewModel.class);
                    rvm.setPropertyViewModel(pvm);
                    return rvm;
                } ).collect(Collectors.toList());
    }

    @Override
    public boolean payReservation(String id)  {
        Reservation reservation = reservationRepository.getOne(id);

        try {
            this.addPaymentToReservation(reservation, reservation.getTotalPrice().toString());
            Reservation savedReservation = reservationRepository.getOne(id);
          if(this.checkIfItIsPayed(savedReservation)) {
              savedReservation.setPayed(true);
              reservationRepository.saveAndFlush(savedReservation);
          }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean cancelReservation(String id) {
        try {
            Reservation reservation = reservationRepository.getOne(id);
            reservation.setCanceled(true);
            reservationRepository.saveAndFlush(reservation);
            return true;
        } catch (Exception ex) {
            return false;
        }


    }

    @Override
    public Reservation findById(String reservationId) throws NotFoundException {
       return reservationRepository.findById(reservationId).orElseThrow(() -> new NotFoundException("Not Found"));
    }

    private boolean checkIfItIsPayed(Reservation savedReservation) {
        BigDecimal sum = savedReservation.getPayment().stream().map(Payment::getAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return sum.compareTo(savedReservation.getTotalPrice()) >= 0;
    }

}
