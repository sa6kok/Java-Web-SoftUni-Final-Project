package com.localbandb.localbandb.web.view.models;

public class ReservationCreateModel {
  private String country;
  private String city;
  private Integer occupancy;
  private String startDate;
  private String endDate;
  private PropertyViewModel propertyViewModel;
  private String totalPrice;

  public ReservationCreateModel() {
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
