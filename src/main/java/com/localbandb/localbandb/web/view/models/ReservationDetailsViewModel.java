package com.localbandb.localbandb.web.view.models;

import java.math.BigDecimal;
import java.util.List;

public class ReservationDetailsViewModel {

    private String startDate;

    private String endDate;

    private Integer occupancy;

    private PropertyViewModel property;

    private ReviewViewModel review;

    private BigDecimal totalPrice;

    private List<PaymentViewModel> payments;

    private boolean payed;

    private boolean canceled;

    public ReservationDetailsViewModel() {
    }


}
