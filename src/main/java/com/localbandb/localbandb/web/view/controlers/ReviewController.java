package com.localbandb.localbandb.web.view.controlers;

import com.localbandb.localbandb.services.services.ReviewService;
import com.localbandb.localbandb.web.view.models.ReviewCreateModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/review")
public class ReviewController extends BaseController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/create/{id}")
    public ModelAndView save(@PathVariable String id, @ModelAttribute ReviewCreateModel model, ModelAndView modelAndView) {

        if(reviewService.save(model, id)) {
            modelAndView.addObject("result", "Review was added successfully to the reservation!");
        } else  {
            modelAndView.addObject("result", "Something went wrong! Review was not added successfully to the reservation!");
        }


        return this.view("reservation/reservation-reservations", modelAndView);

    }


    @PostMapping("/create/{id}")
    public ModelAndView saveConfirm(@PathVariable String id, @ModelAttribute ReviewCreateModel model, ModelAndView modelAndView) {

        if(reviewService.save(model, id)) {
            modelAndView.addObject("result", "Review was added successfully to the reservation!");
        } else  {
            modelAndView.addObject("result", "Something went wrong! Review was not added successfully to the reservation!");
        }


        return this.view("reservation/reservation-reservations", modelAndView);

    }
}
