package com.localbandb.localbandb.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PropertyCreateModel {
  private String name;
  private String description;
  private BigDecimal price;
  private String city;
  private String street;
  private int streetNumber;
  private String streetNumberLetter;
  private int floor;
  private int apartment;
  private String picture;
}
