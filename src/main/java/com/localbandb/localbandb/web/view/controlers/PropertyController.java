package com.localbandb.localbandb.web.view.controlers;

import com.localbandb.localbandb.services.models.CityServiceModel;
import com.localbandb.localbandb.services.services.CountryService;
import com.localbandb.localbandb.services.services.PropertyService;
import com.localbandb.localbandb.web.view.models.*;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.localbandb.localbandb.web.view.constants.Constants.*;

@Controller
@RequestMapping("/property")
public class PropertyController extends BaseController {
    private final CountryService countryService;
    private final PropertyService propertyService;

    @Autowired
    public PropertyController(CountryService countryService, PropertyService propertyService, ModelMapper modelMapper) {
        this.countryService = countryService;
        this.propertyService = propertyService;
    }


    @GetMapping("/create")
    @Secured("ROLE_HOST")
    public ModelAndView getCreateProperty(@ModelAttribute("propertyCreateModel") PropertyCreateModel propertyCreateModel, ModelAndView modelAndView) {
        List<String> countries = countryService.getAllCountryNames();

        modelAndView.addObject("countries", countries);
        return this.view("property/property-create", modelAndView);
    }


    @GetMapping("/create/{country}")
    public ModelAndView getCreateProperty(@PathVariable("country") String country, @ModelAttribute PropertyCreateModel model, ModelAndView modelAndView) throws NotFoundException {
        List<CityServiceModel> cities = countryService.findOrderedCitiesByCountry(country);
        if (cities.size() == 0) {
            return this.redirect("property/create/city/{country}");
        }
        modelAndView.addObject("cities", cities);
        modelAndView.addObject("country", country);
        return this.view("property/property-create", modelAndView);
    }

    @PostMapping("/create/{country}")
    @Secured("ROLE_HOST")
    public ModelAndView postCreateProperty(@Valid @ModelAttribute PropertyCreateModel model,
                                           BindingResult bindingResult,
                                           @PathVariable("country") String country,
                                           ModelAndView modelAndView) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            List<CityServiceModel> cities = countryService.findOrderedCitiesByCountry(country);
            modelAndView.addObject("cities", cities);
            return this.view("property/property-create", modelAndView);
        }
        propertyService.save(model);
        return this.redirect("property/show/all");
    }

    @GetMapping("/create/city/{country}")
    public ModelAndView createCity(@PathVariable("country") String country, ModelAndView modelAndView, @ModelAttribute CountryViewModel countryViewModel) {
        modelAndView.addObject("country", country);
        return this.view("property/property-create-city", modelAndView);
    }


    @PostMapping("/create/city/{country}")
    public ModelAndView createCityConfirm(@PathVariable("country") String country, ModelAndView modelAndView, @ModelAttribute CountryViewModel countryViewModel) throws NotFoundException {
        countryService.addCityToCountry(country, countryViewModel.getName());
        return this.redirect("property/create/" + country, modelAndView);
    }


    @GetMapping("/show")
    public ModelAndView showProperties(@ModelAttribute("cityProperties") List<PropertyViewModel> propertyViewModels,
                                       @ModelAttribute("model") ReservationCreateModel reservationCreateModel,
                                       ModelAndView modelAndView) {
        if (reservationCreateModel.getStartDate() == null) {
            reservationCreateModel = null;
        }
        modelAndView.addObject("properties", propertyViewModels);
        modelAndView.addObject("model", reservationCreateModel);
        return this.view("property/property-show", modelAndView);
    }


    @GetMapping("/show/all")
    public ModelAndView showProperties(ModelAndView modelAndView) {
        List<PropertyViewModel> properties = propertyService.getAllOrderedByAverageReviews();
        modelAndView.addObject("properties", properties);
        modelAndView.addObject("message", AVAILABLE_LOCATIONS_ALL_OVER_EUROPE);
        return this.view("property/property-show", modelAndView);
    }

    @GetMapping("/show/my")
    public ModelAndView showMyProperties(ModelAndView modelAndView) {
        List<PropertyViewModel> properties = propertyService.getMyProperties();
        modelAndView.addObject("properties", properties);
        modelAndView.addObject("message", MY_PROPERTIES);
        return this.view("property/property-show", modelAndView);
    }

    @GetMapping("/details/{id}")
    public ModelAndView showMyProperties(@PathVariable String id, ModelAndView modelAndView, Principal principal) throws NotFoundException {
        PropertyViewModel property = propertyService.findById(id);
        boolean isOwner = propertyService.checkIfUserIsPropertyOwner(id);
        modelAndView.addObject("property", property);
        modelAndView.addObject("isOwner", isOwner);
        return this.view("property/property-details", modelAndView);
    }

    @GetMapping("/add/picture/{id}")
    public ModelAndView addPictureToProperty(@PathVariable String id, @ModelAttribute PictureAddModel pictureAddModel, ModelAndView modelAndView, RedirectAttributes attributes) {
        if (!propertyService.addPictureToProperty(id, pictureAddModel)) {
            attributes.addFlashAttribute("message", PICTURE_WAS_NOT_ADDED);
        }
        return this.redirect("property/details/" + id, modelAndView);
    }

}
