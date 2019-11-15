package com.localbandb.localbandb.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PropertyViewModel {
  private String name;
  private String description;
  private BigDecimal price;
  private List<String> pictures;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
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

  public List<String> getPicture() {
    return pictures;
  }

  public void setPicture(List<String> picture) {
    this.pictures = picture;
  }
}
