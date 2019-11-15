package com.localbandb.localbandb.services.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressServiceModel {
  private String city;
  private String street;
  private int streetNumber;
  private String streetNumberLetter;
  private int floor;
  private int apartment;

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }
}
