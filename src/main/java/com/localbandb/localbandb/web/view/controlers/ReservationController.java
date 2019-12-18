package com.localbandb.localbandb.web.view.controlers;

import com.localbandb.localbandb.services.services.CountryService;
import com.localbandb.localbandb.services.services.PropertyService;
import com.localbandb.localbandb.services.services.ReservationService;
import com.localbandb.localbandb.web.annotations.PageTitle;
import com.localbandb.localbandb.web.view.models.PropertyViewModel;
import com.localbandb.localbandb.web.view.models.ReservationCreateModel;
import com.localbandb.localbandb.web.view.models.ReservationViewModel;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

import static com.localbandb.localbandb.web.view.constants.Constants.*;

@Controller
@RequestMapping("/reservation")
public class ReservationController extends BaseController {
    private final ReservationService reservationService;
    private final CountryService countryService;
    private final PropertyService propertyService;

    @Autowired
    public ReservationController(ReservationService reservationService, CountryService countryService, PropertyService propertyService) {
        this.reservationService = reservationService;
        this.countryService = countryService;
        this.propertyService = propertyService;
    }

    @GetMapping("/guest/home")
    @Secured("ROLE_GUEST")
    public ModelAndView guestHome(ModelAndView modelAndView) {

        return this.view("reservation/reservation-reservations", modelAndView);
    }

    @GetMapping("/create")
    @Secured("ROLE_GUEST")
    public ModelAndView create(ModelAndView modelAndView) {
        List<String> countries = countryService.getAllCountryNames();
        modelAndView.addObject("countries", countries);
        return this.view("reservation/create-reservation", modelAndView);
    }

    @PostMapping("/create")
    public ModelAndView createConfirm(@ModelAttribute ReservationCreateModel model, ModelAndView modelAndView, RedirectAttributes attributes) {

        List<PropertyViewModel> properties = propertyService.getAllPropertyServiceModelsByCityAndFilterBusyDatesAndOccupancy(model.getCity(), model.getStartDate(), model.getEndDate(), model.getOccupancy());

        modelAndView.addObject("city", model.getCity());
        modelAndView.addObject("model", model);
        modelAndView.addObject("properties", properties);
        return this.view("property/property-show", modelAndView);
    }


    @GetMapping("/details/{id}/{start}/{end}/{pax}")
    public ModelAndView showPropertyDetailsWithDates(@PathVariable String id, @PathVariable String start, @PathVariable String end, @PathVariable String pax, ModelAndView modelAndView) throws NotFoundException {

        ReservationCreateModel reservationCreateModel = reservationService.fillUpModel(id, start, end, pax);

        modelAndView.addObject("model", reservationCreateModel);
        return this.view("reservation/reservation-details", modelAndView);
    }


    @GetMapping("/details/{id}")
    public ModelAndView showPropertyDetails(@PathVariable String id,
                                            ModelAndView modelAndView) throws NotFoundException {

        ReservationCreateModel reservationCreateModel = reservationService.fillUpModel(id);

        modelAndView.addObject("model", reservationCreateModel);
        return this.view("reservation/reservation-details", modelAndView);
    }

    @PostMapping("/details/{id}")
    public ModelAndView createReservationConfirm(@PathVariable String id, @Valid @ModelAttribute ReservationCreateModel model, RedirectAttributes attributes, BindingResult bindingResult, ModelAndView modelAndView) {

        String busyDates = propertyService.getJointlyDates(id, model.getStartDate(), model.getEndDate());

        if (bindingResult.hasErrors() || !busyDates.equals("") || model.getStartDate().equals(model.getEndDate())) {
            attributes.addFlashAttribute("model", model);
            attributes.addFlashAttribute("busyDates", busyDates);
            return this.redirect("reservation/details/" + id);
        }
        reservationService.create(id, model);
        modelAndView.addObject("result", RESERVATION_SUCCESSFULLY_CREATED);
        return this.view("reservation/reservation-reservations", modelAndView);
    }

    @GetMapping("/reservations/{filter}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle(PAGE_TITLE)
    public ModelAndView showMyReservations(@PathVariable String filter, ModelAndView modelAndView) {

        List<ReservationViewModel> reservationViewModels = reservationService.findReservationsForUserWithFilter(filter);

        modelAndView.addObject("reservations", reservationViewModels);
        return this.view("reservation/reservation-my-reservations", modelAndView);
    }


    @GetMapping("/pay/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView payReservation(@PathVariable String id, ModelAndView modelAndView) {

        if (reservationService.payReservation(id)) {
            modelAndView.addObject("result", PAYMENT_SUCCESSFUL);
        } else {
            modelAndView.addObject("result", PAYMENT_WAS_NOT_SUCCESSFUL_TRY_AGAIN);
        }

        return this.view("reservation/reservation-reservations", modelAndView);
    }

    @GetMapping("/cancel/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView cancelReservation(@PathVariable String id, ModelAndView modelAndView) {

        if (reservationService.cancelReservation(id)) {
            modelAndView.addObject("result", RESERVATION_CANCELED_SUCCESSFUL);
        } else {
            modelAndView.addObject("result", RESERVATION_NOT_CANCELED_TRY_AGAIN);
        }

        return this.view("reservation/reservation-reservations", modelAndView);
    }

}
