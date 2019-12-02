package com.localbandb.localbandb.web.view.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PropertyCreateModel {

  @Size(min = 3, max = 15, message = "should be between 3 and 15 symbols!")
  private String name;

  @Size(min = 15, message = "should be at least 15 symbols!")
  private String description;

  @Min(value = 1, message = " should be at least one!")
  private Integer maxOccupancy;

  @Min(value = 0, message = " should be a positive number!")
  private BigDecimal price;

  @NotEmpty(message = " should not be empty!")
  private String city;

  @Size(min = 3, max = 25, message = "should be between 3 and 15 symbols!")
  private String street;

  @Min(value = 0, message = " should be a positive number!")
  private int streetNumber;


  private String streetNumberAddition;

  @Min(value = 0, message = " should be a positive number!")
  private int floor;

  @Min(value = 0, message = " should be a positive number!")
  private int apartment;

  private String pictureUrl;
}
