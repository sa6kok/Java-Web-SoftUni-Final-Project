package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.base.TestBase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateServiceImplTest extends TestBase {
    private LocalDate localDate;
    private String date;

    @Autowired
    DateServiceImpl dateService;

    @Before
    public void init() {
        this.localDate = LocalDate.of(2017, 11, 6);
        this.date = "6.11.2017";
    }

    @Test
    public void getDateFromString_IfDateFormatIsCorrect_ShouldReturnCorrectDate() {
        LocalDate expectedDate = dateService.getDateFromString(this.date);
        assertEquals(expectedDate, this.localDate);
    }
    @Test(expected = DateTimeParseException.class)
    public void getDateFromString_IfDateFormatIsNotCorrect_ShouldThrow() {
        LocalDate expectedDate = dateService.getDateFromString("06-1-2019");
        assertEquals(expectedDate, this.localDate);
    }

    @Test
    public void getStringFromLocalDate() {
        String expectedDate = dateService.getStringFromLocalDate(this.localDate);
        assertEquals(expectedDate, this.date);
    }
    @Test(expected = NullPointerException.class)
    public void getStringFromLocalDate_WithNull_ShouldThrow() {
        String expectedDate = dateService.getStringFromLocalDate(null);
        assertEquals(expectedDate, this.date);
    }
}