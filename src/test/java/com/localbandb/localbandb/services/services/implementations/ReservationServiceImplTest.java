package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.base.TestBase;
import com.localbandb.localbandb.config.authentication.AuthenticationFacade;
import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.data.models.Reservation;
import com.localbandb.localbandb.data.repositories.ReservationRepository;
import com.localbandb.localbandb.services.services.DateService;
import com.localbandb.localbandb.services.services.PaymentService;
import com.localbandb.localbandb.services.services.PropertyService;
import com.localbandb.localbandb.services.services.UserService;
import com.localbandb.localbandb.web.view.models.ReservationCreateModel;
import com.localbandb.localbandb.web.view.models.ReviewCreateModel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceImplTest extends TestBase {
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

    @Autowired
    ReservationServiceImpl reservationService;


    @Test
    void fillUpModel() {
    }

    @Test
    void testFillUpModel() {
    }

    @Test
    void addPaymentToReservation() {
    }

    @Test
    void findReservationsForUserWithFilter() {
    }

    @Test
    void payReservation() {
    }

    @Test
    void cancelReservation() {
    }

    @Test
    void findById() {
    }
}