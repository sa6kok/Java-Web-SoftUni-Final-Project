package com.localbandb.localbandb.web.view.controlers;

import com.localbandb.localbandb.services.services.ReviewService;
import com.localbandb.localbandb.web.view.models.ReviewCreateModel;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static com.localbandb.localbandb.web.view.constants.Constants.*;

@Controller
@RequestMapping("/review")
public class ReviewController extends BaseController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/create/{id}")
    public ModelAndView saveConfirm(@PathVariable String id, @ModelAttribute ReviewCreateModel model, ModelAndView modelAndView, BindingResult bindingResult) {
        String result = REVIEW_SUCCESSFULLY_ADDED;

        if (!reviewService.save(model, id)) {
            result = REVIEW_NOT_ADDED;
        }

        modelAndView.addObject("result", result);
        return this.view("reservation/reservation-reservations", modelAndView);

    }
}
