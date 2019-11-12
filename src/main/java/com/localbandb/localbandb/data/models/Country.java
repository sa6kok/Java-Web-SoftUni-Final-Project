package com.localbandb.localbandb.data.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity {

  @Column(name = "name")
  private String name;

  @OneToMany(targetEntity = City.class, mappedBy = "country", cascade = CascadeType.ALL)
  private List<City> cities;

  public Country() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<City> getCities() {
    return cities;
  }

  public void setCities(List<City> cities) {
    this.cities = cities;
  }
}
