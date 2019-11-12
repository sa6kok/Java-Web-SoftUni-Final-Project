package com.localbandb.localbandb.data.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "reservations_without_user")
public class ReservationWithoutUser extends BaseEntity {

  @Column(name = "guest_full_name", nullable = false)
  private String guestFullName;

  @Column(name = "guest_email", nullable = false)
  private String guestEmail;

  @Column(name = "start_date")
  private Date startDate;

  @Column(name = "end_date")
  private Date endDate;

  @ManyToOne(targetEntity = Property.class)
  @JoinColumn(name = "property_id", referencedColumnName = "id")
  private Property property;

  @Column(name = "total_price")
  private BigDecimal totalPrice;

  public ReservationWithoutUser() {
  }

  public String getGuestFullName() {
    return guestFullName;
  }

  public void setGuestFullName(String guestFullName) {
    this.guestFullName = guestFullName;
  }

  public String getGuestEmail() {
    return guestEmail;
  }

  public void setGuestEmail(String guestEmail) {
    this.guestEmail = guestEmail;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Property getProperty() {
    return property;
  }

  public void setProperty(Property property) {
    this.property = property;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }
}
