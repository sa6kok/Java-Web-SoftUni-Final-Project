package com.localbandb.localbandb.web.view.models;

import javax.validation.constraints.Size;

public class ReservationCreateModel {
  private String country;
  private String city;
  private Integer occupancy;
  @Size(min = 10, max = 10)
  private String startDate;
  @Size(min = 10, max = 10)
  private String endDate;
  private PropertyViewModel propertyViewModel;
  private String totalPrice;

  public ReservationCreateModel() {
  }

  public ReservationCreateModel(Integer occupancy, String startDate, String endDate, PropertyViewModel propertyViewModel) {
    this.occupancy = occupancy;
    this.startDate = startDate;
    this.endDate = endDate;
    this.propertyViewModel = propertyViewModel;
  }

  public ReservationCreateModel(PropertyViewModel propertyViewModel) {
    this.propertyViewModel = propertyViewModel;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Integer getOccupancy() {
    return occupancy;
  }

  public void setOccupancy(Integer occupancy) {
    this.occupancy = occupancy;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public PropertyViewModel getPropertyViewModel() {
    return propertyViewModel;
  }

  public void setPropertyViewModel(PropertyViewModel propertyViewModel) {
    this.propertyViewModel = propertyViewModel;
  }

  public String getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(String totalPrice) {


    this.totalPrice = totalPrice;
  }
}
