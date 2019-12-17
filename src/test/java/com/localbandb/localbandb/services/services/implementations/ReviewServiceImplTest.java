package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.base.TestBase;
import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.data.models.Reservation;
import com.localbandb.localbandb.data.models.Review;
import com.localbandb.localbandb.data.repositories.ReviewRepository;
import com.localbandb.localbandb.services.services.ReservationService;
import com.localbandb.localbandb.web.view.models.ReviewCreateModel;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

class ReviewServiceImplTest extends TestBase {

    Review testReview;
    ReviewCreateModel testReviewCreateModel;

    @MockBean
    ReservationService reservationService;

    @MockBean
    ReviewRepository reviewRepository;

    @Autowired
    ReviewServiceImpl reviewService;

    @Override
    public void beforeEach() {
       this.testReview = new Review();
       this.testReviewCreateModel = new ReviewCreateModel();
      this.testReviewCreateModel.setDescription("ok");
      this.testReview.setDescription("ok");
    }

    @Test
    void save_withNoReservation_shouldThrow() throws NotFoundException {
        Mockito.when(reservationService.findById("id")).thenThrow();
        reviewService.save(this.testReviewCreateModel, "id");



    }
}