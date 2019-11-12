package com.localbandb.localbandb.data.models;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "properties")
public class Property extends BaseEntity {

  @Column(name = "name")
  private String name;

  @ManyToOne(targetEntity = Host.class)
  private Host host;

  @OneToOne(targetEntity = Address.class)
  @JoinColumn(name = "address_id", referencedColumnName = "id")
  private Address address;

  @Column(name = "description")
  private String description;


  @ElementCollection(targetClass = Date.class)
  @Column(name = "busy_dates")
  private List<Date> busyDates;

  @OneToMany(targetEntity = Reservation.class, mappedBy = "property")
  private List<Reservation> reservations;

  @OneToMany(targetEntity = ReservationWithoutUser.class, mappedBy = "property")
  private List<ReservationWithoutUser> reservationWithoutUsers;

  @ElementCollection(targetClass = String.class)
  private List<String> pictures;

  @Column(name = "price")
  private BigDecimal price;

  public Property() {
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Host getHost() {
    return host;
  }

  public void setHost(Host host) {
    this.host = host;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Date> getBusyDates() {
    return busyDates;
  }

  public void setBusyDates(List<Date> busyDates) {
    this.busyDates = busyDates;
  }

  public List<Reservation> getReservations() {
    return reservations;
  }

  public void setReservations(List<Reservation> reservations) {
    this.reservations = reservations;
  }

  public List<ReservationWithoutUser> getReservationWithoutUsers() {
    return reservationWithoutUsers;
  }

  public void setReservationWithoutUsers(List<ReservationWithoutUser> reservationWithoutUsers) {
    this.reservationWithoutUsers = reservationWithoutUsers;
  }

  public List<String> getPictures() {
    return pictures;
  }

  public void setPictures(List<String> pictures) {
    this.pictures = pictures;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
