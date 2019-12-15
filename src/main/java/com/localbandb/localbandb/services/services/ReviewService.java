package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.web.view.models.ReviewCreateModel;

public interface ReviewService {

    boolean save(ReviewCreateModel model, String reservationId);
}
