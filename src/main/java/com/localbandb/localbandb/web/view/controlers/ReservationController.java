package com.localbandb.localbandb.web.view.controlers;

import com.localbandb.localbandb.services.models.ReservationServiceModel;
import com.localbandb.localbandb.services.services.CountryService;
import com.localbandb.localbandb.services.services.PropertyService;
import com.localbandb.localbandb.services.services.ReservationService;
import com.localbandb.localbandb.web.view.models.PropertyViewModel;
import com.localbandb.localbandb.web.view.models.ReservationCreateModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    LocalDate start = LocalDate.parse(model.getStartDate(), DateTimeFormatter.ofPattern("d.MM.yyyy"));
    LocalDate end = LocalDate.parse(model.getEndDate(), DateTimeFormatter.ofPattern("d.MM.yyyy"));
    List<LocalDate> busyDates = reservationService.datesBetween(start, end);

    List<PropertyViewModel> properties = propertyService.getAllByCity(model.getCity()).stream()
        .filter(p -> !p.getBusyDates().containsAll(busyDates))
        .filter(prop -> Integer.parseInt(prop.getMaxOccupancy()) >=  model.getOccupancy())
        .collect(Collectors.toList());
    attributes.addFlashAttribute("cityProperties", properties);
    System.out.println();
    return this.redirect("property/show", modelAndView);
  }
}
