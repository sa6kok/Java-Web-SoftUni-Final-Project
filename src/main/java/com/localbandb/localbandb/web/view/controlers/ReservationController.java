package com.localbandb.localbandb.web.view.controlers;

import com.localbandb.localbandb.services.models.ReservationServiceModel;
import com.localbandb.localbandb.services.services.CountryService;
import com.localbandb.localbandb.services.services.PropertyService;
import com.localbandb.localbandb.services.services.ReservationService;
import com.localbandb.localbandb.web.view.models.PropertyViewModel;
import com.localbandb.localbandb.web.view.models.ReservationCreateModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
                                               @Valid @ModelAttribute ReservationCreateModel model,
                                               ModelAndView modelAndView, BindingResult bindingResult){
    if(bindingResult.hasErrors()) {
      return this.view("reservation/{id}", modelAndView);
    }
    reservationService.create(id, model);

    System.out.println();
    return this.redirect("reservation/mine", modelAndView);
  }
}
