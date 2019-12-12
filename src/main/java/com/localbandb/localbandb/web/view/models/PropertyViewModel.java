package com.localbandb.localbandb.web.view.models;

import com.localbandb.localbandb.data.models.City;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PropertyViewModel {
  private String id;
  private String name;
  private String description;
  private BigDecimal price;
  private String maxOccupancy;
  private String street;
  private Integer streetNumber;
  private String streetNumberAddition;
  private Integer floor;
  private Integer apartment;
  private City city;
  private List<String> pictures;
  private List<LocalDate> busyDates;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public String getShortDescription() {
    return description.length() > 75 ?  description.substring(0, 75) + "..." : description;
  }

  public String getFullStreet() {
    StringBuilder sb = new StringBuilder(this.street);
   sb.append(" ").append(this.streetNumber).append(this.streetNumberAddition == null ? "" : this.streetNumberAddition )
           .append(", Floor: ").append(this.floor == null ? "" : this.floor).append(", Apartment: ")
           .append(this.apartment == null ? "" : this.apartment);

   return sb.toString();
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getMaxOccupancy() {
    return maxOccupancy;
  }

  public void setMaxOccupancy(String maxOccupancy) {
    this.maxOccupancy = maxOccupancy;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Integer getStreetNumber() {
    return streetNumber;
  }

  public void setStreetNumber(Integer streetNumber) {
    this.streetNumber = streetNumber;
  }

  public String getStreetNumberAddition() {
    return streetNumberAddition;
  }

  public void setStreetNumberAddition(String streetNumberAddition) {
    this.streetNumberAddition = streetNumberAddition;
  }

  public Integer getFloor() {
    return floor;
  }

  public void setFloor(Integer floor) {
    this.floor = floor;
  }

  public Integer getApartment() {
    return apartment;
  }

  public void setApartment(Integer apartment) {
    this.apartment = apartment;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public List<String> getPictures() {
    return pictures;
  }

  public void setPictures(List<String> pictures) {
    this.pictures = pictures;
  }

  public List<String> getPicture() {
    return pictures;
  }

  public void setPicture(List<String> picture) {
    this.pictures = picture;
  }

  public List<LocalDate> getBusyDates() {
    return busyDates;
  }

  public void setBusyDates(List<LocalDate> busyDates) {
    this.busyDates = busyDates;
  }
}
