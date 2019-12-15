package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.config.authentication.AuthenticationFacade;
import com.localbandb.localbandb.data.models.Payment;
import com.localbandb.localbandb.data.models.Reservation;
import com.localbandb.localbandb.data.models.User;
import com.localbandb.localbandb.data.repositories.PaymentRepository;
import com.localbandb.localbandb.services.services.PaymentService;
import com.localbandb.localbandb.services.services.UserService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(AuthenticationFacade authenticationFacade, UserService userService, PaymentRepository paymentRepository) {
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public boolean save(Payment payment) {
       try {
           paymentRepository.save(payment);
           return true;
       } catch (Exception ex) {
           return false;
       }
    }

    @Override
    public void createPayment(Reservation reservation) throws NotFoundException {
        Payment payment = new Payment();

        User host = userService.findById(reservation.getProperty().getHost().getId());
        User guest = userService.findByUsername(authenticationFacade.getAuthentication().getName());
        payment.setGuest(guest);
        payment.setHost(host);
        payment.setAmount(reservation.getTotalPrice());
        payment.setPaymentDate(LocalDate.now());
        payment.setReservation(reservation);

        try {
            Payment savedPayment = paymentRepository.saveAndFlush(payment);
            reservation.getPayment().add(savedPayment);

        } catch (Exception ex) {

        }

    }
}
