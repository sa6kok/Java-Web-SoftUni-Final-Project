package com.localbandb.localbandb.data.models;


import javax.persistence.*;
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

  @OneToMany(targetEntity = Review.class, mappedBy = "id")
  private List<Review> reviews;

  @ElementCollection(targetClass = Date.class)
  @Column(name = "busy_dates")
  private List<Date> busyDates;

  @ManyToMany(targetEntity = User.class, mappedBy = "visitedProperties")
  private List<User> visitors;

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

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  public List<Date> getBusyDates() {
    return busyDates;
  }

  public void setBusyDates(List<Date> busyDates) {
    this.busyDates = busyDates;
  }

  public List<User> getVisitors() {
    return visitors;
  }

  public void setVisitors(List<User> visitors) {
    this.visitors = visitors;
  }
}
