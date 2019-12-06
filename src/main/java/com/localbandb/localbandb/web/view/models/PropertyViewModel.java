package com.localbandb.localbandb.web.view.models;

import com.localbandb.localbandb.data.models.City;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
