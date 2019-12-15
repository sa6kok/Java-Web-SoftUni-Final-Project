package com.localbandb.localbandb.web.view.controlers;

import com.localbandb.localbandb.services.services.CountryService;
import com.localbandb.localbandb.services.services.PropertyService;
import com.localbandb.localbandb.services.services.ReservationService;
import com.localbandb.localbandb.web.view.models.PropertyViewModel;
import com.localbandb.localbandb.web.view.models.ReservationCreateModel;
import com.localbandb.localbandb.web.view.models.ReservationViewModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
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

@Controller
@RequestMapping("/reservation")
public class ReservationController extends BaseController {
  private final ReservationService reservationService;
  private final CountryService countryService;
  private final PropertyService propertyService;
  private final ModelMapper mapper;

  @Autowired
  public ReservationController(ReservationService reservationService, CountryService countryService, PropertyService propertyService, ModelMapper mapper) {
    this.reservationService = reservationService;
    this.countryService = countryService;
    this.propertyService = propertyService;
    this.mapper = mapper;
  }

  @GetMapping("/create")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView create(ModelAndView modelAndView) {
    List<String> countries = countryService.getAllCountryNames();
    modelAndView.addObject("countries", countries);
    return this.view("reservation/create-reservation", modelAndView);
  }

  @PostMapping("/create")
  public ModelAndView createConfirm(@ModelAttribute ReservationCreateModel model, ModelAndView modelAndView, RedirectAttributes attributes) {

    List<PropertyViewModel> properties = propertyService.getAllByCityAndFilterBusyDatesAndOccupancy(model.getCity(), model.getStartDate(), model.getEndDate(), model.getOccupancy());

    attributes.addFlashAttribute("city", model.getCity());
    attributes.addFlashAttribute("model", model);
    attributes.addFlashAttribute("cityProperties", properties);
    System.out.println();
    return this.redirect("property/show", modelAndView);
  }


  @GetMapping("/details/{id}/{start}/{end}/{pax}")
  public ModelAndView showPropertyDetailsWithDates(@PathVariable String id, @PathVariable String start,
                                                   @PathVariable String end, @PathVariable String pax,
                                                   ModelAndView modelAndView) throws NotFoundException {

    ReservationCreateModel reservationCreateModel = reservationService.fillUpModel(id, start, end, pax);
    modelAndView.addObject("model", reservationCreateModel);
    System.out.println();
    return this.view("reservation/reservation-details", modelAndView);
  }


  @GetMapping("/details/{id}")
  public ModelAndView showPropertyDetails(@PathVariable String id,
                                          ModelAndView modelAndView) throws NotFoundException {

    ReservationCreateModel reservationCreateModel = reservationService.fillUpModel(id);
    System.out.println();
    modelAndView.addObject("model", reservationCreateModel);


    return this.view("reservation/reservation-details", modelAndView);
  }

  @PostMapping("/details/{id}")
  public ModelAndView createReservationConfirm(@PathVariable String id,
                                               @Valid @ModelAttribute ReservationCreateModel model, RedirectAttributes attributes, BindingResult bindingResult, ModelAndView modelAndView) {
    String busyDates = propertyService.getJointlyDates(id, model.getStartDate(), model.getEndDate());
    if(bindingResult.hasErrors() || !busyDates.equals("")) {
      attributes.addFlashAttribute("model", model);
      attributes.addFlashAttribute("busyDates", busyDates);
      return this.redirect("reservation/details/" + id);
    }
    reservationService.create(id, model);
    modelAndView.addObject("result", "Reservation was successfully created!");
    return this.view("reservation/reservation-reservations", modelAndView);
  }

  @GetMapping("/reservations/{filter}")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView showMyReservations(@PathVariable String filter,  ModelAndView modelAndView) {
    List<ReservationViewModel> reservationViewModels = reservationService.findReservationsForUserWithFilter(filter);
    modelAndView.addObject("reservations", reservationViewModels);
    return this.view("reservation/reservation-my-reservations", modelAndView);
  }


  @GetMapping("/pay/{id}")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView payReservation(@PathVariable String id,  ModelAndView modelAndView) {
    System.out.printf("");
   if(reservationService.payReservation(id)) {
     modelAndView.addObject("result", "Payment successful!");
   } else {
     modelAndView.addObject("result", "Payment was not successful! Please try again!");
   }
    return this.view("reservation/reservation-reservations", modelAndView);
  }

  @GetMapping("/cancel/{id}")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView cancelReservation(@PathVariable String id,  ModelAndView modelAndView) {

    if(reservationService.cancelReservation(id)) {
      modelAndView.addObject("result", "You just canceled the reservation successful!");
    } else {
      modelAndView.addObject("result", "Your try to cancel was not successful! Please try again!");
    }
    return this.view("reservation/reservation-reservations", modelAndView);
  }

  @GetMapping("/guest/home")
  @Secured("ROLE_GUEST")
  public ModelAndView guestHome(ModelAndView modelAndView) {

    return this.view("reservation/reservation-reservations", modelAndView);
  }
}
