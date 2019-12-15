/*
package com.localbandb.localbandb.data.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservations_without_user")
public class ReservationWithoutUser extends BaseEntity {

  @Size(min = 15, max = 35)
  @Column(name = "guest_full_name", nullable = false)
  private String guestFullName;

  @Email
  @Column(name = "guest_email", nullable = false, unique = true)
  private String guestEmail;


  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @ManyToOne(targetEntity = Property.class)
  @JoinColumn(name = "property_id", referencedColumnName = "id")
  private Property property;

  @Min(0)
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

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
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
*/
