package com.localbandb.localbandb.services.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PropertyServiceModel {
  private String name;
  private String description;
  private BigDecimal price;
  private String picture;
  private AddressServiceModel addressServiceModel;

  public AddressServiceModel getAddressServiceModel() {
    return addressServiceModel;
  }

  public void setAddressServiceModel(AddressServiceModel addressServiceModel) {
    this.addressServiceModel = addressServiceModel;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }
}
