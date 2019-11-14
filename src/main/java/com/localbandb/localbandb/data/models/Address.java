package com.localbandb.localbandb.data.models;


import javax.persistence.*;


@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {
  @Column(name = "street", nullable = false)
  private String street;

  @Column(name = "streetNumber", nullable = false)
  private String streetNumber;

  @Column(name = "floor", nullable = false)
  private Integer floor;

  @Column(name = "apartment", nullable = false)
  private Integer apartment;

  @ManyToOne(targetEntity = City.class)
  @JoinColumn(name = "city_id", referencedColumnName = "id")
  private City city;


  @OneToOne(targetEntity = Property.class)
  private Property property;

  public Address() {
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String  getStreetNumber() {
    return streetNumber;
  }

  public void setStreetNumber(String  streetNumber) {
    this.streetNumber = streetNumber;
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

  public Property getProperty() {
    return property;
  }

  public void setProperty(Property property) {
    this.property = property;
  }
}
