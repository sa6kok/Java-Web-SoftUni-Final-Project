package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.base.TestBase;
import com.localbandb.localbandb.config.authentication.AuthenticationFacade;
import com.localbandb.localbandb.data.models.City;
import com.localbandb.localbandb.data.models.Property;
import com.localbandb.localbandb.data.models.User;
import com.localbandb.localbandb.data.repositories.PropertyRepository;
import com.localbandb.localbandb.services.models.PropertyServiceModel;
import com.localbandb.localbandb.services.services.CityService;
import com.localbandb.localbandb.services.services.DateService;
import com.localbandb.localbandb.services.services.PropertyService;
import com.localbandb.localbandb.services.services.UserService;
import com.localbandb.localbandb.web.view.models.PropertyCreateModel;
import com.localbandb.localbandb.web.view.models.PropertyViewModel;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class PropertyServiceImplTest extends TestBase {

    private Property property;
    private PropertyServiceModel propertyServiceModel;
    private PropertyCreateModel propertyCreateModel;


    @MockBean
    private PropertyRepository propertyRepository;

    @MockBean
    private CityService cityService;

    @MockBean
    private UserService userService;

    @MockBean
    private DateService dateService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private AuthenticationFacade authenticationFacade;

    @MockBean
    PropertyServiceImpl mockedPropertyService;

    @Autowired
    private PropertyServiceImpl propertyService;

   @Before
    public void beforeEach() {
        this.property = new Property();
       this.propertyServiceModel = new PropertyServiceModel();
       this.propertyServiceModel.setPictureUrl("url");
       this.propertyCreateModel = new PropertyCreateModel();

    }


    @Test
    public void save_withoutCity_returnsFalse() throws NotFoundException {
        Mockito.when(cityService.findCityByName("Pesho")).thenReturn(null);

        assertFalse(propertyService.save(this.propertyCreateModel));
    }

    @Test
    public void save_withoutUser_returnsFalse() throws NotFoundException {
        Mockito.when(userService.findByUsername("Pesho")).thenReturn(null);


        assertFalse(propertyService.save(this.propertyCreateModel));
    }

    @Test
    public void save_withInValidPropertyServiceModelData_returnsTrue() throws NotFoundException {
        Mockito.when(cityService.findCityByName("Pesho")).thenReturn(new City());
        Mockito.when(userService.findByUsername("Pesho")).thenReturn(new User());
        Mockito.when(modelMapper.map(this.propertyServiceModel, Property.class)).thenReturn(null);

        assertFalse(propertyService.save(this.propertyCreateModel));
    }



    @Test
    public void getAllByCity_withCorrectCity_returnsArray() {
        Mockito.when(propertyRepository.findByCity_Name("city")).thenReturn(new ArrayList<>());

        assertEquals(propertyService.getAllByCity("city"), new ArrayList<>());
    }

    @Test
    public void getAllByCity_withWrongCity_ReturnsEmptyArray() {
        Mockito.when(propertyRepository.findByCity_Name("cit")).thenReturn(null);

      assertEquals(0, propertyService.getAllByCity("cit").size());
    }

    @Test
    public void findById_withWrongId_throws() throws NotFoundException {
       Optional<Property> property  = Optional.empty();
        Mockito.when(propertyRepository.findById("id")).thenReturn(property);
       assertEquals(null, propertyService.findById("id"));
   }


    @Test
    public void getAllByCityAndFilterBusyDatesAndOccupancy_withSameDate_shouldReturnArrayWithoutTheProperty() {
       LocalDate start = LocalDate.of(2019, 12, 27);
       LocalDate end = LocalDate.of(2019, 12, 28);
       List<LocalDate> askedDates = new ArrayList<>();
       askedDates.add(start);
       askedDates.add(end);

       LocalDate busy = LocalDate.of(2019, 12, 27);
       Property property = new Property();
       property.setBusyDates(new ArrayList<>());
       property.getBusyDates().add(busy);
       property.setMaxOccupancy(2);
       List<Property> properties = new ArrayList<>();
       properties.add(property);
        Mockito.when(propertyRepository.findByCity_Name("city")).thenReturn(properties);
       Mockito.when(mockedPropertyService.getDatesBetweenStartAndEndFromString("2019-12-27", "2019-12-29")).thenReturn(askedDates);
       assertEquals(0, propertyService.getAllPropertyServiceModelsByCityAndFilterBusyDatesAndOccupancy("city", "2019-12-27","2019-12-29", 1 ).size());
    }

    @Test
    public void getAllByCityAndFilterBusyDatesAndOccupancy_withBiggerOccupancy_shouldReturnEmptyArray() {
        LocalDate start = LocalDate.of(2019, 12, 27);
        LocalDate end = LocalDate.of(2019, 12, 28);
        List<LocalDate> askedDates = new ArrayList<>();
        askedDates.add(start);
        askedDates.add(end);

        LocalDate busy = LocalDate.of(2019, 12, 31);
        Property property = new Property();
        property.setBusyDates(new ArrayList<>());
        property.getBusyDates().add(busy);
        property.setMaxOccupancy(2);
        List<Property> properties = new ArrayList<>();
        properties.add(property);
        Mockito.when(propertyRepository.findByCity_Name("city")).thenReturn(properties);
        List<PropertyViewModel> propertyList = propertyService.getPropertyViewModelsFromProperty(properties);
        assertEquals(0, propertyList.size());
    }

}