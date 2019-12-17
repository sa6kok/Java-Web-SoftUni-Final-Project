package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.base.TestBase;
import com.localbandb.localbandb.config.authentication.AuthenticationFacade;
import com.localbandb.localbandb.data.models.Reservation;
import com.localbandb.localbandb.data.models.Role;
import com.localbandb.localbandb.data.models.User;
import com.localbandb.localbandb.data.repositories.ReservationRepository;
import com.localbandb.localbandb.services.services.DateService;
import com.localbandb.localbandb.services.services.PaymentService;
import com.localbandb.localbandb.services.services.PropertyService;
import com.localbandb.localbandb.services.services.UserService;
import javassist.NotFoundException;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.PersistenceException;
import java.net.Authenticator;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceImplTest extends TestBase {
    @MockBean
    PropertyService propertyService;

    @MockBean
    DateService dateService;

    @MockBean
    UserService userService;

    @MockBean
    PaymentService paymentService;

    @MockBean
    AuthenticationFacade authenticationFacade;

    @MockBean
    ModelMapper mapper;

    @MockBean
    ReservationRepository reservationRepository;

    @MockBean
    ReservationServiceImpl mockedReservationService;

    @Autowired
    ReservationServiceImpl reservationService;


    @Test
    public void fillUpModel() {
    }


    @Test
    public void addPaymentToReservation_WithCorrectReservation_ShouldNotThrow() throws NotFoundException {
        Reservation reservation = new Reservation();
        doNothing().when(paymentService).createPayment(reservation);
        reservationService.addPaymentToReservation(reservation, "101");
    }

    @Test(expected = Exception.class)
    public void addPaymentToReservation_WithNullReservation_ShouldThrow() throws NotFoundException {
        Reservation reservation = new Reservation();
        doThrow().when(paymentService).createPayment(null);
        reservationService.addPaymentToReservation(reservation, "101");
    }

    @Test(expected = Exception.class)
    public void payReservationWithInvalidUser_shouldThrow() {
        doThrow().when(reservationRepository).findById("id");
        reservationService.payReservation("id");
    }

    @Test(expected = Exception.class)
    public void payReservation_whenNotPossibleToAdd_shouldThrow() throws NotFoundException {
        Reservation reservation = new Reservation();
        doThrow().when(mockedReservationService).addPaymentToReservation(reservation, "id");
        reservationService.payReservation("id");
    }

    @Test
    public void payReservation_withWrongData_returnsFalse() throws NotFoundException {
        Reservation reservation = new Reservation();
        doNothing().when(mockedReservationService).addPaymentToReservation(reservation, "id");
        assertFalse(reservationService.payReservation("ip"));
    }

    @Test
    public void payReservation_withoutData_returnsFalse() throws NotFoundException {
        Reservation reservation = new Reservation();
        Mockito.when(reservationRepository.findById("id")).thenReturn(Optional.of(reservation));
        doNothing().when(mockedReservationService).addPaymentToReservation(reservation, "id");
        assertFalse(reservationService.payReservation("ip"));
    }

    @Test(expected = Exception.class)
    public void cancelReservation_withoutUser_throws() {
       Mockito.when(authenticationFacade.getAuthentication().getName()).thenReturn(null);
       reservationService.cancelReservation("id");
    }

    @Test(expected = Exception.class)
    public void cancelReservation_withoutCorrectId_throws() {
        Reservation reservation = new Reservation();
        Mockito.when(authenticationFacade.getAuthentication().getName()).thenReturn("Pesho");
      Mockito.when(reservationRepository.findById("id")).thenReturn(Optional.of(reservation));
        reservationService.cancelReservation("id");
    }

    @Test
    public void findById_withWrongUser_shouldNotThrow() throws NotFoundException {
        Reservation reservation = new Reservation();
        reservation.setId("id");
        Mockito.when(reservationRepository.findById("id")).thenReturn(Optional.of(reservation));
       reservationService.findById("id");
    }

}