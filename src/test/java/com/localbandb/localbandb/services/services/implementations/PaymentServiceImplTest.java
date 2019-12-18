package com.localbandb.localbandb.services.services.implementations;


import com.localbandb.localbandb.base.TestBase;
import com.localbandb.localbandb.config.authentication.AuthenticationFacade;
import com.localbandb.localbandb.data.models.Payment;
import com.localbandb.localbandb.data.models.Reservation;
import com.localbandb.localbandb.data.models.User;
import com.localbandb.localbandb.data.repositories.PaymentRepository;
import com.localbandb.localbandb.services.services.UserService;
import javassist.NotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class PaymentServiceImplTest extends TestBase {

    private static Payment payment;

    @MockBean
    PaymentRepository mockedPaymentRepository;

    @MockBean
    UserService userService;

    @MockBean
    AuthenticationFacade mockedAuthenticationFacade;

    @Autowired
    public PaymentServiceImpl paymentService;



    @BeforeClass
    public static void init() {
        payment = new Payment();
        payment.setId("id");
        payment.setAmount(BigDecimal.TEN);
    }

    @Test
    public void save_withEmptyEntity_returnsFalse() {
        Mockito.when(mockedPaymentRepository.save(payment)).thenThrow(PersistenceException.class);
        assertFalse(paymentService.save(payment));
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
        Mockito.when(mockedPaymentRepository.save(payment)).thenReturn(new Payment());
        assertTrue(paymentService.save(payment));
    }


    @Test
    public void createPayment_whenEverythingIsOk_doesNotThrow() throws NotFoundException {
        Reservation reservation = new Reservation();
        User user = new User();
        Mockito.when(userService.findById("id")).thenReturn(user);
        Mockito.when(userService.findByUsername("username")).thenReturn(user);
        when(mockedPaymentRepository.saveAndFlush(payment)).thenReturn(payment);
        paymentService.createPayment(reservation);

    }

    @Test
    public void createPayment_withoutHost_doesNotThrow() throws NotFoundException {
        Payment testPayment = new Payment();
        Reservation reservation = new Reservation();
        reservation.setTotalPrice(BigDecimal.valueOf(10));
        try {
            Mockito.when(userService.findByUsername("Pesho")).thenReturn(new User());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Mockito.when(userService.findById("Pesho")).thenThrow(NotFoundException.class);
        Mockito.when(mockedPaymentRepository.saveAndFlush(testPayment)).thenThrow(PersistenceException.class);

      paymentService.createPayment(reservation);
    }



}