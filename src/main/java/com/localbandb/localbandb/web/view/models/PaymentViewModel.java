package com.localbandb.localbandb.web.view.models;

import com.localbandb.localbandb.data.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentViewModel {

    private BigDecimal amount;

    private LocalDate paymentDate;

    private Reservation reservation;

    public PaymentViewModel() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
