package com.localbandb.localbandb.web.view.models;

import java.math.BigDecimal;

public class PaymentCreateModel {
    private BigDecimal amount;

    public PaymentCreateModel() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
