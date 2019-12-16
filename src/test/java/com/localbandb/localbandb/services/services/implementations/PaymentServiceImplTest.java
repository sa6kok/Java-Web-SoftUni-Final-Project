package com.localbandb.localbandb.services.services.implementations;


import com.localbandb.localbandb.base.TestBase;
import com.localbandb.localbandb.config.authentication.AuthenticationFacade;
import com.localbandb.localbandb.data.models.Payment;
import com.localbandb.localbandb.data.models.Reservation;
import com.localbandb.localbandb.data.models.User;
import com.localbandb.localbandb.data.repositories.PaymentRepository;
import com.localbandb.localbandb.services.services.UserService;
import javassist.NotFoundException;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentServiceImplTest extends TestBase {

    private Payment payment;

    @MockBean
    PaymentRepository mockedPaymentRepository;

    @MockBean
    AuthenticationFacade mockedAuthenticationFacade;

    @MockBean
    UserService mockedUserService;

    @Autowired
    public PaymentServiceImpl paymentService;

    @Override
    public void beforeEach() {
        this.payment = new Payment();
    }

    @Test
    public void save_withEmptyEntity_returnsFalse() {
        Mockito.when(mockedPaymentRepository.save(this.payment)).thenThrow(PersistenceException.class);
        assertFalse(paymentService.save(this.payment));
    }


    @Test
    public void save_withNegativeAmount_returnsFalse() {
        Payment testPayment = new Payment();
        testPayment.setAmount(BigDecimal.valueOf(-1));
        Mockito.when(mockedPaymentRepository.save(testPayment)).thenThrow(PersistenceException.class);
        assertFalse(paymentService.save(testPayment));
    }


    @Test
    public void save_withValidEntity_returnsTrue() {
        Mockito.when(mockedPaymentRepository.save(this.payment)).thenReturn(new Payment());
        assertTrue(paymentService.save(this.payment));
    }


    @Test
    public void createPayment() {

    }

    @Test
    public void createPayment_withoutHost_doesNotThrow() throws NotFoundException {
        Payment testPayment = new Payment();
        Reservation reservation = new Reservation();
        reservation.setTotalPrice(BigDecimal.valueOf(10));
//        Mockito.when(mockedAuthenticationFacade.getAuthentication().getName()).thenReturn("Pesho");
        try {
            Mockito.when(mockedUserService.findByUsername("Pesho")).thenReturn(new User());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Mockito.when(mockedUserService.findById("Pesho")).thenThrow(NotFoundException.class);
        Mockito.when(mockedPaymentRepository.saveAndFlush(testPayment)).thenThrow(PersistenceException.class);

      paymentService.createPayment(reservation);
    }
    @Test
    public void createPayment_withoutUser_doesNotThrow() throws NotFoundException {
        Payment testPayment = new Payment();
        Reservation reservation = new Reservation();
        reservation.setTotalPrice(BigDecimal.valueOf(10));
//        Mockito.when(mockedAuthenticationFacade.getAuthentication().getName()).thenReturn("Pesho");
        try {
            Mockito.when(mockedUserService.findByUsername("Pesho")).thenThrow( NotFoundException.class);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Mockito.when(mockedUserService.findById("Pesho")).thenReturn(new User());
        Mockito.when(mockedPaymentRepository.saveAndFlush(testPayment)).thenThrow(PersistenceException.class);

        paymentService.createPayment(reservation);
    }

    @Test
    public void createPayment_withoutGuest_doesNotThrow() {
        Payment testPayment = new Payment();
        Reservation reservation = new Reservation();
        Mockito.when(mockedPaymentRepository.saveAndFlush(testPayment)).thenThrow(PersistenceException.class);
        paymentService.createPayment(reservation);
    }


    @Test
    public void createPayment_withoutReservation_throws() {
        Payment testPayment = new Payment();
        Reservation reservation = new Reservation();
        Mockito.when(mockedPaymentRepository.save(testPayment)).thenThrow(NullPointerException.class);
        paymentService.createPayment(reservation);
    }
}